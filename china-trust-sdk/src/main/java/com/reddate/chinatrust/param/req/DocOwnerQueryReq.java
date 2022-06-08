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
 * DocOwnerQuery request of ChinaTrust
 */
@Data
public class DocOwnerQueryReq {

    private static final Logger logger = LoggerFactory.getLogger(DocOwnerQueryReq.class);
    private static final Validator validator = new Validator();


    @NotEmpty(message = "the value of ddcClient is null")
    @JsonIgnore
    private DDCSdkClient ddcClient;

    @NotNull(message = "the value of ddcId is null")
    @NotBlank(message = "the value of ddcId is null")
    private String ddcId;

    
    /**
     * check the attribute value for DocumentIssuanceReq.
     */
    public void check() {

        List<ConstraintViolation> errors = validator.validate(DocOwnerQueryReq.this);
        if (errors.size() > 0) {

            logger.error("[DocOwnerQueryReq.check] message: {}", errors.get(0).getMessage());
            throw new ChinaTrustBaseException(errors.get(0).getMessage());
        }
    }

}