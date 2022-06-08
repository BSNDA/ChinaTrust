package com.reddate.chinatrust.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * Credential Subject of ChinaTrust
 */
@Data
public class CredentialSubject {

    /**
     * ID
     */
    private String id;

    /**
     * Recipient
     */
    private Recipient recipient;

    /**
     * List document
     */
    private List<Map<String, Object>> documents;
}