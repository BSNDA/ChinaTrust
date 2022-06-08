package com.reddate.chinatrust.dto;

import lombok.Data;


/**
 * Document attribute
 */
@Data
public class Document {

    /**
     * Block document name
     */
    private String blDocName;

    /**
     * Block Issuer
     */
    private String blIssuer;
}