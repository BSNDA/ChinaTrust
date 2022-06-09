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
public class DocumentIssuInput {

    private static final Logger logger = LoggerFactory.getLogger(DocumentIssuInput.class);
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
     * DocumentIssuance name
     */
    private String name;

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
     * check the attribute value for DocumentIssuanceReq.
     */
    public void check() {
        List<ConstraintViolation> constraintViolationSet = validator.validate(this);
        List<String> messageList = new ArrayList<String>();
        constraintViolationSet.forEach(violationInfo -> {
            messageList.add(violationInfo.getMessage());
        });
        if (messageList.size() > 0) {
            logger.error("[DocumentIssuanceReq.check] message: {}", messageList.get(0));
            throw new ChinaTrustBaseException(messageList.get(0));
        }

        ProofM proof = metadata.getProof();
        if(proof == null) {
            throw new ChinaTrustBaseException("the value of metadata.proof is null");
        }
        String didId = proof.getValue();
        if(StringUtils.isBlank(didId)) {
            throw new ChinaTrustBaseException("the value of metadata.proof.value is null");
        }
        List<Map<String, Object>> documents  = credentialSubject.getDocuments();
        if(CollUtil.isEmpty(documents) || CollUtil.isEmpty(documents.get(0)) || documents.get(0).containsKey("") ||
                documents.get(0).containsValue("") || documents.get(0).containsValue(null)) {
            throw new ChinaTrustBaseException("the value of credentialSubject.documents is null");
        }
    }

}









