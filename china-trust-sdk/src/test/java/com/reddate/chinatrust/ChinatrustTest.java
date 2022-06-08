package com.reddate.chinatrust;

import com.reddate.chinatrust.dto.*;
import com.reddate.chinatrust.param.req.*;
import com.reddate.chinatrust.utils.Secp256K1Util;
import com.reddate.ddc.DDCSdkClient;
import com.reddate.ddc.config.ConfigCache;
import com.reddate.ddc.listener.Secp256K1SignEventListener;
import com.reddate.did.sdk.DidClient;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ChinatrustTest {

    ChinaTrustClient trustClient = new ChinaTrustClient();

    private DidClient didClient = null;
    private DDCSdkClient ddcClient = null;
    private String chainAccPrivateKey = "-----BEGIN PRIVATE KEY-----MIGEAgEAMBAGByqGSM49AgEGBSuBBAAKBG0wawIBAQQgatH5XaE3mR6Py/CeDPs0vFb7w1iJ2PYfH3e4dKMZbQehRANCAATlIb6BxFjwV3AE6pr5YuxDxP/HyhEa/47ITpCO6KNTIhuPj1rUMxvUK7DI9CL0OpgOMLvq6wN0wcMkADJgJpqB-----END PRIVATE KEY-----";
    private String chainAccPublicKey = "-----BEGIN PUBLIC KEY-----MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE5SG+gcRY8FdwBOqa+WLsQ8T/x8oRGv+OyE6QjuijUyIbj49a1DMb1CuwyPQi9DqYDjC76usDdMHDJAAyYCaagQ==-----END PUBLIC KEY-----";

    private String didPrivateKey = "15752570254173592501850381327417892021285124071063564823361665149524141208878";
    private String chinaAccAddress = "0xd4996afe3902c6e5387b88b46b5f1fd0407c146a";
    private String didPublicKey = "10480774481491669745149479170894939304498520647390124280550775543845711439325659395250137144854384271417179728447634641499724947349581414434142384794963583";
    private Boolean isDdcTestEnvironment = true;
    private String ddcToAddress = "0x6f069e84a194faa184068af0e74bd46529b5130e";

    private DidClient getDidClient() {

        if (didClient == null) {
            didClient = new DidClient();
        }
        return didClient;
    }

    private DDCSdkClient getDdcClient() {

        if (ddcClient == null) {
            if (isDdcTestEnvironment) {
                initDdcTestEnvContractAddress();
            }
            ddcClient = new DDCSdkClient("https://opbtest.bsngate.com:18602/api/Fiscorpc/rpc");
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


    @Test//签发
    public void issueDocumentTest() throws Exception {

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        DocumentIssuanceTx doc = demo();
                        System.out.println("-------------- ddcId = " + doc.getDocumentIssuance().getReference() +
                                ", id = " + doc.getDocumentIssuance().getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            list.add(thread);
        }
        for (Thread tmp : list){
            tmp.join();
        }

//        assertNotNull(demo());
    }


    @Test//验证
    public void VerificationDocumentTest() throws Exception {
        DocumentIssuance doc=new DocumentIssuance();
        DocumentValidationReq docValReq = new DocumentValidationReq();
        docValReq.setDidClient(getDidClient());
        docValReq.setDdcClient(getDdcClient());
        docValReq.setPrivateKey(didPrivateKey);
        docValReq.setPublicKey(didPublicKey);
        BeanUtils.copyProperties(demo(),doc);
        docValReq.setDocumentIssuance(doc);
        assertEquals(trustClient.getVerificationDocumentService().validateDocument(docValReq), true);
    }


    @Test//转移
    public void TransferableDocumentTest() throws Exception {
        DocumentTransferReq docTraReq = new DocumentTransferReq();
        docTraReq.setDdcClient(getDdcClient());
        docTraReq.setSenderAddress(Secp256K1Util.convertDidPubKey2Address(didPublicKey));
        docTraReq.setDdcFromAddress(chinaAccAddress);
        docTraReq.setDdcId(demo().getDocumentIssuance().getReference());
        docTraReq.setDdcToAddress(ddcToAddress);
        assertNotNull(trustClient.getVerificationDocumentService().transferDocument(docTraReq));
    }


    @Test//查询
    public void queryDocumentTest() throws Exception {
        DocOwnerQueryReq ownerQryReq = new DocOwnerQueryReq();
        ownerQryReq.setDdcClient(getDdcClient());
        ownerQryReq.setDdcId(demo().getDocumentIssuance().getReference());
        assertEquals(trustClient.getVerificationDocumentService().queryDocumentOwner(ownerQryReq), chinaAccAddress);
    }


    @Test//销毁
    public void setTransferableDocumentService() throws Exception {
        DocumentDestroyReq docDesReq = new DocumentDestroyReq();
        docDesReq.setDdcClient(getDdcClient());
        docDesReq.setChainAccAddress(chinaAccAddress);
        docDesReq.setDdcId(demo().getDocumentIssuance().getReference());
        assertNotNull(trustClient.getVerificationDocumentService().destroyDocument(docDesReq));
    }

    private DocumentIssuanceTx demo() throws Exception {

        DocumentIssuInput documentIssuInput=new DocumentIssuInput();
        documentIssuInput.setVersion("https://schema.reddatetech.com/1.0/schema.json");
        List<String> con = new ArrayList<>();
        con.add("https://www.w3.org/2018/credentials/v1");
        documentIssuInput.setContext(con);

        documentIssuInput.setName("Electronic bill of lading");
        Issuer is = new Issuer();
        is.setName("Harry Potter");
        is.setId("https://example.com");
        documentIssuInput.setIssuer(is);

        List<String> ty = new ArrayList<>();
        ty.add("VerifiableCredential");
        ty.add("EBLCredential");
        documentIssuInput.setType(ty);
        demo2(documentIssuInput);

        DocumentIssuanceReq docIssReq = new DocumentIssuanceReq();

        docIssReq.setDidClient(getDidClient());
        docIssReq.setDdcClient(getDdcClient());
        docIssReq.setPrivateKey(didPrivateKey);
        docIssReq.setDdcToAddress(chinaAccAddress);
        docIssReq.setSenderAddress(Secp256K1Util.convertDidPubKey2Address(didPublicKey));
        docIssReq.setDocumentIssuance((documentIssuInput));
        DocumentIssuanceTx documentIssuance = trustClient.getVerificationDocumentService().issueDocument(docIssReq);
        return documentIssuance;
    }

    private void demo2(DocumentIssuInput documentIssuInput) {
        Proof pr = new Proof();
        pr.setTargetHash("21e104bea288809f659f6144ebae1ce632256ddeb9c8ecded795573853a43c5d");
        pr.setMerkleRoot("21e104bea288809f659f6144ebae1ce632256ddeb9c8ecded795573853a43c5d");
        pr.setType("EcdsaSecp256k1Signature2019");
        pr.setProofPurpose("assertionMethod");
        Metadata me = new Metadata();
        ProofM pro = new ProofM();
        pro.setType("ProofMethod");
        pro.setMethod("BSN-DID");
        pro.setValue("did:bsn:2bNhngjvq9r1HwAK7kLMQ3tQcH3Q");
        me.setProof(pro);
        documentIssuInput.setMetadata(me);

        CredentialSubject cr = new CredentialSubject();
        cr.setId("https://ipfscc.bsngate.com:18602/ipfs/peer1/17e8134655ffacd004");
        Recipient r = new Recipient();
        r.setName("John Doe");
        cr.setRecipient(r);

        List<Map<String, Object>> documents = new ArrayList<Map<String,Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("1","z");
        documents.add(map);
        cr.setDocuments(documents);
        documentIssuInput.setCredentialSubject(cr);
    }
}
