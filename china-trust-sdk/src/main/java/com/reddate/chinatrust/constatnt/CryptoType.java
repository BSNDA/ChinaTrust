package com.reddate.chinatrust.constatnt;


public enum CryptoType {
	ECDSA,SM;
	
	public static CryptoType ofVlaue(Integer value) {
		for(CryptoType tmp : CryptoType.values()) {
			if(tmp.ordinal() == value) {
				return tmp;
			}
		}
		return null;
	}
	
	
	public static CryptoType getByName(String name) {
		if(name != null && !name.trim().isEmpty()) {
			String typeName = name.trim().toUpperCase();
			for(CryptoType tmp : values()) {
				if(typeName.equals(tmp.name())) {
					return tmp;
				}
			}
		}
		return null;
	}
	
}
