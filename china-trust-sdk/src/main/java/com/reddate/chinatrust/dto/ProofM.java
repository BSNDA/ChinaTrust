package com.reddate.chinatrust.dto;

import lombok.Data;


/**
 * DocumentIssuance proofm relevant information
 */
@Data
public class ProofM {

    private String type;

    private String method;

    private String value;
}