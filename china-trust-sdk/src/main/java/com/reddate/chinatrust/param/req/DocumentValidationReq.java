package com.reddate.chinatrust.param.req;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reddate.chinatrust.dto.DocumentIssuance;
import com.reddate.chinatrust.dto.DocumentIssuanceTx;
import com.reddate.chinatrust.exception.ChinaTrustBaseException;
import com.reddate.ddc.DDCSdkClient;
import com.reddate.did.sdk.DidClient;
import lombok.Data;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * DocumentValidation request of ChinaTrust
 */
@Data
public class DocumentValidationReq {

    private static final Logger logger = LoggerFactory.getLogger(DocumentValidationReq.class);
    private static final Validator validator = new Validator();


    @NotEmpty(message = "the value of privateKey is null")
    @JsonIgnore
    private String privateKey;

    @NotEmpty(message = "the value of publicKey is null")
    @JsonIgnore
    private String publicKey;

    @NotEmpty(message = "the value of didClient is null")
    @JsonIgnore
    private DidClient didClient;

    @NotEmpty(message = "the value of ddcClient is null")
    @JsonIgnore
    private DDCSdkClient ddcClient;

    @NotNull(message = "the value of documentIssuance is null")
    private DocumentIssuance documentIssuance;



    
    /**
     * check the attribute value for DocumentIssuanceReq.
     */
    public void check() {

        List<ConstraintViolation> errors = validator.validate(DocumentValidationReq.this);
        if (errors.size() > 0) {

            logger.error("[DocumentValidationReq.check] message: {}", errors.get(0).getMessage());
            throw new ChinaTrustBaseException(errors.get(0).getMessage());
        }
        documentIssuance.check();
    }

}