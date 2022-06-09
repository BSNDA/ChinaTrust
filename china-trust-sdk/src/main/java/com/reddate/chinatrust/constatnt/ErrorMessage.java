package com.reddate.chinatrust.constatnt;

public enum ErrorMessage {

	SUCCESS(0,"Success"),
	EXCEPTION(99998,"eroor"),
	;
	
	Integer code;
	
	String msg;

	private ErrorMessage(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public static ErrorMessage getByCode(Integer code) {
		if(code != null) {
			for(ErrorMessage tmp : ErrorMessage.values()) {
				if(tmp.getCode().equals(code)) {
					return tmp;
				}
			}
		}
		
		return null;
	}
	
	
	
}
