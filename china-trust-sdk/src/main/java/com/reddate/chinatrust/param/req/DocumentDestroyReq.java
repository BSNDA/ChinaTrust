package com.reddate.chinatrust.param.req;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reddate.chinatrust.exception.ChinaTrustBaseException;
import com.reddate.ddc.DDCSdkClient;
import lombok.Data;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * DocumentDestroy request of ChinaTrust
 */
@Data
public class DocumentDestroyReq {

    private static final Logger logger = LoggerFactory.getLogger(DocumentDestroyReq.class);
    private static final Validator validator = new Validator();


    @NotEmpty(message = "the value of ddcClient is null")
    @JsonIgnore
    private DDCSdkClient ddcClient;

    @NotEmpty(message = "the value of chainAccAddress is null")
    @JsonIgnore
    private String chainAccAddress;

    @NotNull(message = "the value of ddcId is null")
    @NotBlank(message = "the value of ddcId is null")
    private String ddcId;

    
    /**
     * check the attribute value for DocumentIssuanceReq.
     */
    public void check() {

        List<ConstraintViolation> errors = validator.validate(DocumentDestroyReq.this);
        if (errors.size() > 0) {

            logger.error("[DocumentDestroyReq.check] message: {}", errors.get(0).getMessage());
            throw new ChinaTrustBaseException(errors.get(0).getMessage());
        }
    }

}