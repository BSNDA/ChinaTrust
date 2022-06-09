package com.reddate.chinatrust.dto;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Attribute of DocumentIssuance
 */
@Data
public class DocumentIssuanceTx {

    private static final Logger logger = LoggerFactory.getLogger(DocumentIssuanceTx.class);


    /**
     * Document issuance info
     */
    private DocumentIssuance documentIssuance;

    /**
     * TX of DDC mint function
     */
    private String tx;



}









