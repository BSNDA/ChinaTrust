package com.reddate.chinatrust.exception;

import com.reddate.chinatrust.constatnt.ErrorMessage;

public class ChinaTrustException extends RuntimeException{

	private static final long serialVersionUID = 6756244229028172189L;
	
	private Integer code;
	
	public ChinaTrustException(Integer code,String mesage) {
		super(mesage);
		this.code = code;
	}
	
	public ChinaTrustException(ErrorMessage errorMesage) {
		super(errorMesage.getMsg());
		this.code = errorMesage.getCode();
	}

	public Integer getCode() {
		return code;
	}
	
	
}
