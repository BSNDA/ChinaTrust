package com.reddate.chinatrust.param.resp;

import java.io.Serializable;

import lombok.Data;


/**
 * Verify document resp
 */
@Data
public class VerifyDocumentResp implements Serializable {

	private boolean result;
	
	private String message;
	
}
