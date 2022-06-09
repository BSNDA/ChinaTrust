package com.reddate.chinatrust.param.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reddate.chinatrust.dto.DocumentIssuInput;
import com.reddate.chinatrust.dto.DocumentIssuance;
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
 * DocumentIssuance request of ChinaTrust
 */
@Data
public class DocumentIssuanceReq {

    private static final Logger logger = LoggerFactory.getLogger(DocumentIssuanceReq.class);
    private static final Validator validator = new Validator();


    @NotEmpty(message = "the value of privateKey is null")
    @JsonIgnore
    private String privateKey;

    @NotEmpty(message = "the value of senderAddress is null")
    @JsonIgnore
    private String senderAddress;

    @NotEmpty(message = "the value of didClient is null")
    @JsonIgnore
    private DidClient didClient;

    @NotEmpty(message = "the value of ddcClient is null")
    @JsonIgnore
    private DDCSdkClient ddcClient;

    @NotEmpty(message = "the value of ddcToAddress is null")
    @JsonIgnore
    private String ddcToAddress;
    
    @NotNull(message = "the value of documentIssuance is null")
    private DocumentIssuInput documentIssuance;

    
    /**
     * check the attribute value for DocumentIssuanceReq.
     */
    public void check() {

        List<ConstraintViolation> errors = validator.validate(DocumentIssuanceReq.this);
        if (errors.size() > 0) {

            logger.error("[DocumentIssuanceReq.check] message: {}", errors.get(0).getMessage());
            throw new ChinaTrustBaseException(errors.get(0).getMessage());
        }
        documentIssuance.check();
    }

}