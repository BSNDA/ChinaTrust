package com.reddate.chinatrust.service;


import com.reddate.chinatrust.ChinaTrustClient;
import com.reddate.chinatrust.dto.DocumentIssuance;
import com.reddate.chinatrust.dto.DocumentIssuanceTx;
import com.reddate.chinatrust.param.req.*;
import com.reddate.chinatrust.utils.Secp256K1Util;
import com.reddate.ddc.DDCSdkClient;
import com.reddate.ddc.config.ConfigCache;
import com.reddate.ddc.listener.Secp256K1SignEventListener;
import com.reddate.did.sdk.DidClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BusinessService{

    private static final Logger logger = LoggerFactory.getLogger(BusinessService.class);

    private static ChinaTrustClient trustClient = new ChinaTrustClient();

    private DidClient didClient = null;

    private DDCSdkClient ddcClient = null;

    @Value("${didPrivateKey}")
    private String didPrivateKey;

    @Value("${didPublicKey}")
    private String didPublicKey;

    @Value("${chainAccPrivateKey}")
    private String chainAccPrivateKey;

    @Value("${chainAccPublicKey}")
    private String chainAccPublicKey;

    @Value("${chainAccAddress}")
    private String chainAccAddress;

    @Value("${ddcUrl}")
    private String ddcUrl;

    @Value("${isDdcTestEnvironment}")
    private Boolean isDdcTestEnvironment;


    private DidClient getDidClient(){

        if(didClient == null){
            didClient = new DidClient();
        }
        return didClient;
    }

    private DDCSdkClient getDdcClient(){

        DDCSdkClient ddcClient = null;
        if(ddcClient == null){
            if(isDdcTestEnvironment) {
                initDdcTestEnvContractAddress();
            }
            ddcClient = new DDCSdkClient(ddcUrl);
            Secp256K1SignEventListener signEventListener = null;

            try {
                signEventListener = new Secp256K1SignEventListener(chainAccPrivateKey, chainAccPublicKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ddcClient.registerSignListener(signEventListener);
        }
        return ddcClient;
    }

    private void initDdcTestEnvContractAddress() {

        final String AUTHORITY_ADDRESS = "0x3a4E0796e910FCBD55193C1046D6e8Ee0FA97fCF";
        final String CHARGE_ADDRESS = "0xD2b9EF79d3fC992C767fb2E7Fb5E337DF27848D6";
        final String DDC_721_ADDRESS = "0x5a7ac17A046003E3048aB749E60EB71988393104";
        final String DDC_1155_ADDRESS = "0x727CdAD1C0324E8f236fa5A22F7f13174FF5F9C3";

        ConfigCache.get().setAuthorityLogicAddress(AUTHORITY_ADDRESS);
        ConfigCache.get().setChargeLogicAddress(CHARGE_ADDRESS);
        ConfigCache.get().setDdc721Address(DDC_721_ADDRESS);
        ConfigCache.get().setDdc1155Address(DDC_1155_ADDRESS);
    }

    public DocumentIssuanceTx issueDocument(@RequestBody DocumentIssuanceReq docIssReq) throws Exception{

        docIssReq.setDidClient(getDidClient());
        docIssReq.setDdcClient(getDdcClient());
        docIssReq.setPrivateKey(didPrivateKey);
        docIssReq.setDdcToAddress(chainAccAddress);
        docIssReq.setSenderAddress(Secp256K1Util.convertDidPubKey2Address(didPublicKey));
        return trustClient.getVerificationDocumentService().issueDocument(docIssReq);
    }

    public Boolean validateDocument(@RequestBody DocumentValidationReq docValReq) throws Exception{

        docValReq.setDidClient(getDidClient());
        docValReq.setDdcClient(getDdcClient());
        docValReq.setPrivateKey(didPrivateKey);
        docValReq.setPublicKey(didPublicKey);
        return trustClient.getVerificationDocumentService().validateDocument(docValReq);
    }

    public String transferDocument(@RequestBody DocumentTransferReq docTraReq) throws Exception{

        docTraReq.setDdcClient(getDdcClient());
        docTraReq.setSenderAddress(Secp256K1Util.convertDidPubKey2Address(didPublicKey));
        docTraReq.setDdcFromAddress(chainAccAddress);
        return trustClient.getVerificationDocumentService().transferDocument(docTraReq);
    }

    public String queryDocumentOwner(@RequestBody DocOwnerQueryReq ownerQryReq) throws Exception{

        ownerQryReq.setDdcClient(getDdcClient());
        return trustClient.getVerificationDocumentService().queryDocumentOwner(ownerQryReq);
    }

    public String destroyDocument(@RequestBody DocumentDestroyReq docDesReq) throws Exception{

        docDesReq.setDdcClient(getDdcClient());
        docDesReq.setChainAccAddress(chainAccAddress);
        return trustClient.getVerificationDocumentService().destroyDocument(docDesReq);
    }
}
