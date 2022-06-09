package com.reddate.chinatrust.vo.request;

import java.io.Serializable;

/**
 * 
 * Request Param data Structure
 *
 *
 * @param <T>
 */
public class RequestParams<T> implements Serializable {
	
	private String ddcOpenApiUrl;
	
	private String ddcOpenApiToken;
	
	/**
	 * business data
	 * 
	 */
	private T data;


	public RequestParams(String ddcOpenApiUrl, String ddcOpenApiToken, T data) {
		this.ddcOpenApiUrl = ddcOpenApiUrl;
		this.ddcOpenApiToken = ddcOpenApiToken;
		this.data = data;
	}

	public String getDdcOpenApiUrl() {
		return ddcOpenApiUrl;
	}

	public void setDdcOpenApiUrl(String ddcOpenApiUrl) {
		this.ddcOpenApiUrl = ddcOpenApiUrl;
	}

	public String getDdcOpenApiToken() {
		return ddcOpenApiToken;
	}

	public void setDdcOpenApiToken(String ddcOpenApiToken) {
		this.ddcOpenApiToken = ddcOpenApiToken;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
