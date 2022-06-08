package com.reddate.chinatrust.service;

import cn.hutool.json.JSONUtil;
import com.reddate.chinatrust.constatnt.ConfigConst;
import com.reddate.chinatrust.dto.DocumentIssuInput;
import com.reddate.chinatrust.dto.DocumentIssuance;
import com.reddate.chinatrust.dto.DocumentIssuanceTx;
import com.reddate.chinatrust.dto.Proof;
import com.reddate.chinatrust.exception.ChinaTrustBaseException;
import com.reddate.chinatrust.param.req.*;
import com.reddate.chinatrust.utils.AesUtils;
import com.reddate.ddc.DDCSdkClient;
import com.reddate.ddc.dto.ddc.BaseEventBean;
import com.reddate.ddc.dto.ddc.DDC721TransferEventBean;
import com.reddate.ddc.dto.taianchain.TransactionRecepitBean;
import com.reddate.ddc.service.BlockEventService;
import com.reddate.did.sdk.DidClient;
import com.reddate.did.sdk.param.req.DidSign;
import com.reddate.did.sdk.util.ECDSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.math.BigInteger;
import java.util.ArrayList;


/**
 * Verification document service
 */
public class VerificationDocumentService {

	Logger log = LoggerFactory.getLogger(VerificationDocumentService.class);



	/**
	 * Document issuance
	 *
	 * <1> Check input parameters
	 * <2> Save the document issue date and validation date validfrom
	 * <3> Verify did
	 * <4> The signature value of the generated document content (excluding the reference and targethash storing ddcid) is saved to targethash
	 * <5> Generate ddcid and chain the signature value of < 4 > as ddcuri
	 *
	 * @param docIssReq DocumentIssuanceReq value
	 * @return DocumentIssuance information
	 * @throws Exception
	 */
	public DocumentIssuanceTx issueDocument(DocumentIssuanceReq docIssReq) throws Exception {

		docIssReq.check();
		DocumentIssuInput docInput = docIssReq.getDocumentIssuance();
		String didId = docInput.getMetadata().getProof().getValue();
		if(!validateDid(docIssReq.getDidClient(), didId, docIssReq.getPrivateKey())){
			throw new ChinaTrustBaseException("This did does not exist");
		}

		DocumentIssuance docOutput = new DocumentIssuance();
		BeanUtils.copyProperties(docInput, docOutput);
		docOutput.setId(AesUtils.generalKey());
		ArrayList<String> arr = callDdcMint(docIssReq, docOutput.getId());
		String ddcId = arr.get(0);
		String tx = arr.get(1);

		docOutput.setReference(ddcId);
		String js = JSONUtil.toJsonStr(docOutput);
		String signValue = ECDSAUtils.sign(js, docIssReq.getPrivateKey());
		docOutput.setProof(new Proof());
		docOutput.getProof().setType(ECDSAUtils.TYPE);
		docOutput.getProof().setTargetHash(signValue);

		DocumentIssuanceTx docTx = new DocumentIssuanceTx();
		docTx.setDocumentIssuance(docOutput);
		docTx.setTx(tx);
		return docTx;
	}

	/**
	 * Validate Did
	 *
	 * @param didId  Distributed digital identity
	 * @return Return the verify did identify result
	 * @throws Exception
	 */
	private static Boolean validateDid(DidClient didClient, String didId, String didPrivateKey) throws Exception {

		DidSign didSign = new DidSign();
		didSign.setDid(didId);
		String signValue = ECDSAUtils.sign(didId, didPrivateKey);
		didSign.setDidSign(signValue);

		return didClient.verifyDIdSign(didSign);
	}

	/**
	 * Document validation
	 *
	 * <1> Reference check(Reference=ddcId)
	 * <2> Verify document content signature
	 * <3> Verify did
	 * <4> Verify ddcid and the corresponding relationship between ddcid and signature
	 *
	 * @param docValReq DocumentValidationReq value
	 * @return True
	 * @throws Exception
	 */
	public Boolean validateDocument(DocumentValidationReq docValReq) throws Exception {

		docValReq.check();
		DocumentIssuance documentIssuance = docValReq.getDocumentIssuance();
		String id = documentIssuance.getId();
		String ddcId = documentIssuance.getReference();
		String targetHash = documentIssuance.getProof().getTargetHash();

		documentIssuance.setProof(null);
		String js=JSONUtil.toJsonStr(documentIssuance);
		boolean ret = ECDSAUtils.verify(js, docValReq.getPublicKey(), targetHash);
		if(!ret){
			throw new ChinaTrustBaseException("The document content fails to pass the signature verification");
		}

		String didId = documentIssuance.getMetadata().getProof().getValue();
		if(!validateDid(docValReq.getDidClient(), didId, docValReq.getPrivateKey())){
			throw new ChinaTrustBaseException("This did does not exist");
		}
		String ddcUri = callDdcUri(docValReq, ddcId);
		if(!id.equals(ddcUri)){
			throw new ChinaTrustBaseException("The value of documentIssuance.id is inconsistent with that on DDC chain");
		}
		return true;
	}

	/**
	 * Document transfer
	 *
	 * <1> Check input parameters
	 * <2> Transfer the ddcid of the current chain account to another chain account
	 *
	 * @param docTraReq DocumentTransferReq value
	 * @return Transaction hash
	 * @throws Exception
	 */
	public String transferDocument(DocumentTransferReq docTraReq) throws Exception {

		docTraReq.check();
		DDCSdkClient ddcSdkClient = docTraReq.getDdcClient();
		String tx =  ddcSdkClient.getDDC721Service().transferFrom(docTraReq.getSenderAddress(), docTraReq.getDdcFromAddress(),
				docTraReq.getDdcToAddress(), new BigInteger(docTraReq.getDdcId()));

		while (true) {
			TransactionRecepitBean transactionRecepitBean = ddcSdkClient.getDDC721Service().getTransactionRecepit(tx);
			if (transactionRecepitBean == null) {
				Thread.sleep(200 * 2);
				continue;
			}
			if(!ConfigConst.CHAIN_TX_STATUS.equals(transactionRecepitBean.getStatus())) {
				throw new ChinaTrustBaseException("Failed to transfer document");
			}
			break;
		}
		return tx;
	}

	/**
	 * Query document owner
	 *
	 * <1> Reference check
	 * <2> Query the chain account address according to ddcid
	 *
	 * @param ownerQryReq DocOwnerQueryReq value
	 * @return Chain account address
	 * @throws Exception
	 */
	public String queryDocumentOwner(DocOwnerQueryReq ownerQryReq) throws Exception {

		ownerQryReq.check();
		DDCSdkClient ddcSdkClient = ownerQryReq.getDdcClient();
		return ddcSdkClient.getDDC721Service().ownerOf(new BigInteger(ownerQryReq.getDdcId()));
	}

	/**
	 * Document destroy
	 *
	 * <1> Reference check
	 * <2> Destroy the corresponding document according to ddcid
	 *
	 * @param docDesReq DocumentDestroyReq value
	 * @return Transaction hash
	 * @throws Exception
	 */
	public String destroyDocument(DocumentDestroyReq docDesReq) throws Exception {

		docDesReq.check();
		DDCSdkClient ddcSdkClient = docDesReq.getDdcClient();
		String tx =  ddcSdkClient.getDDC721Service().burn(docDesReq.getChainAccAddress(), new BigInteger(docDesReq.getDdcId()));

		while (true) {
			TransactionRecepitBean transactionRecepitBean = ddcSdkClient.getDDC721Service().getTransactionRecepit(tx);
			if (transactionRecepitBean == null) {
				Thread.sleep(200 * 2);
				continue;
			}
			if(!ConfigConst.CHAIN_TX_STATUS.equals(transactionRecepitBean.getStatus())) {
				throw new ChinaTrustBaseException("Failed to destroy document");
			}
			break;
		}
		return tx;
	}

	private String callDdcUri(DocumentValidationReq docValReq, String ddcId) throws Exception {

		DDCSdkClient ddcSdkClient = docValReq.getDdcClient();
		return ddcSdkClient.getDDC721Service().ddcURI(new BigInteger(ddcId));
	}

	/**
	 * Generate ddcid
	 *
	 * @param docIssReq DocumentIssuanceReq value
	 * @return ddcId value
	 * @throws Exception
	 */
	private synchronized ArrayList<String> callDdcMint(DocumentIssuanceReq docIssReq, String id) throws Exception {

		ArrayList<String> arrayList = new ArrayList<String>();
		final String[] ddcId = {null};
		DDCSdkClient ddcSdkClient = docIssReq.getDdcClient();
		String tx = ddcSdkClient.getDDC721Service().mint(docIssReq.getSenderAddress(), docIssReq.getDdcToAddress(), id);
		log.info("--------------------------- callDdcMint:tx {}", tx);

		while (true) {
			TransactionRecepitBean transactionRecepitBean = ddcSdkClient.getDDC721Service().getTransactionRecepit(tx);
			if (transactionRecepitBean == null) {
				Thread.sleep(200 * 2);
				continue;
			}
			if(!ConfigConst.CHAIN_TX_STATUS.equals(transactionRecepitBean.getStatus())) {
				throw new ChinaTrustBaseException("Failed to issue document");
			}

			BlockEventService blockEventService = new BlockEventService();
			ArrayList<BaseEventBean> result = blockEventService.getBlockEvent(transactionRecepitBean.getBlockNumber());
			result.forEach(t -> {
				if (t instanceof DDC721TransferEventBean) {
					ddcId[0] = ((DDC721TransferEventBean) t).getDdcId().toString();
					log.info("--------------------------- {}:DDCID {}", t.getClass(), ddcId[0]);
					arrayList.add(ddcId[0]);
				}
			});
			break;
		}
		arrayList.add(tx);
		return arrayList;
	}

}
