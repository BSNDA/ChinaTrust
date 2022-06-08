package com.reddate.chinatrust.dto;


import lombok.Data;

import java.util.List;


/**
 * DocumentIssuance proof relevant information
 */
@Data
public class Proof {

    private String type;

    private String proofPurpose;

    private String targetHash;

    private List<String> proofs;

    private String merkleRoot;
}