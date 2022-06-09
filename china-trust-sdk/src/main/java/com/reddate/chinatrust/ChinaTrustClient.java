package com.reddate.chinatrust;


import com.reddate.chinatrust.service.VerificationDocumentService;

/**
 * SDK entrance
 * @author unkonw
 */
public class ChinaTrustClient {
	
	private VerificationDocumentService verificationDocumentService = new VerificationDocumentService();


	public VerificationDocumentService getVerificationDocumentService() {
		return verificationDocumentService;
	}

	public void setVerificationDocumentService(VerificationDocumentService verificationDocumentService) {
		this.verificationDocumentService = verificationDocumentService;
	}
}
