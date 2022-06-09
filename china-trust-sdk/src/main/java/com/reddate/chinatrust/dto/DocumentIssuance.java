package com.reddate.chinatrust.dto;


import cn.hutool.core.collection.CollUtil;
import com.reddate.chinatrust.exception.ChinaTrustBaseException;
import lombok.Data;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.constraint.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Attribute of DocumentIssuance
 */
@Data
public class DocumentIssuance {

    private static final Logger logger = LoggerFactory.getLogger(DocumentIssuance.class);
    private static final Validator validator = new Validator();

    /**
     * DocumentIssuance version
     */
    private String version;

    /**
     * DocumentIssuance context
     */
    private List<String> context;

    /**
     * Store ddcID
     */
    @NotNull(message = "the value of documentIssuance.reference is null")
    private String reference;

    /**
     * DocumentIssuance name
     */
    private String name;

    /**
     * DocumentIssuance id
     */
    @NotNull(message = "the value of documentIssuance.id is null")
    private String id;

    /**
     * DocumentIssuance issuance date
     */
    private String issuanceDate;

    /**
     * DocumentIssuance valid from
     */
    private String validFrom;

    /**
     * DocumentIssuance issuer
     */
    private Issuer issuer;

    /**
     * DocumentIssuance type
     */
    private List<String> type;

    /**
     * DocumentIssuance credential subject
     */
    @NotNull(message = "the value of documentIssuance.credentialSubject is null")
    private CredentialSubject credentialSubject;

    /**
     * DocumentIssuance metadata
     */
    @NotNull(message = "the value of documentIssuance.metadata is null")
    private Metadata metadata;

    /**
     * DocumentIssuance proof
     */
    @NotNull(message = "the value of documentIssuance.proof is null")
    private Proof proof;

    
    /**
     * check the attribute value for DocumentIssuanceReq.
     */
    public void check() {
        List<ConstraintViolation> constraintViolationSet = validator.validate(this);
        List<String> messageList = new ArrayList<String>();
        constraintViolationSet.forEach(violationInfo -> {
            messageList.add(violationInfo.getMessage());
        });
        if (messageList.size() > 0) {
            logger.error("[DocumentIssuance.check] message: {}", messageList.get(0));
            throw new ChinaTrustBaseException(messageList.get(0));
        }

        List<Map<String, Object>> documents  = credentialSubject.getDocuments();
        if(CollUtil.isEmpty(documents) || CollUtil.isEmpty(documents.get(0))) {
            throw new ChinaTrustBaseException("the value of credentialSubject.documents is null");
        }
        ProofM proofM = metadata.getProof();
        if(proofM == null) {
            throw new ChinaTrustBaseException("the value of metadata.proof is null");
        }
        String didId = proofM.getValue();
        if(StringUtils.isBlank(didId)) {
            throw new ChinaTrustBaseException("the value of metadata.proof.value is null");
        }

        String targetHash = proof.getTargetHash();
        if(StringUtils.isBlank(targetHash)) {
            throw new ChinaTrustBaseException("the value of proof.targetHash is null");
        }
        String type = proof.getType();
        if(StringUtils.isBlank(type)) {
            throw new ChinaTrustBaseException("the value of proof.type is null");
        }
    }

}









