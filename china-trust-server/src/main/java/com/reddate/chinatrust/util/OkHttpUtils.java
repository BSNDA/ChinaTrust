package com.reddate.chinatrust.util;


import com.alibaba.fastjson.JSONObject;
import com.reddate.chinatrust.exception.ChinaTrustBaseException;
import com.reddate.chinatrust.vo.request.RequestParams;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Http/Https utils class,
 * some http(s) protocal common tools method implement in this class
 * 
 */
public class OkHttpUtils<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

	private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

	public static final MediaType TEXT_PLAIN = MediaType.parse("text/plain");

	public static final String CONTENT_TYPE_KEY = "Content-Type";

	public static final String MULTIPART = "multipart/form-data";


	/**
	 * 
	 * call the OKHttp post method to send some request data to server ,and process server return data to the target type
	 *
	 */
	public static JSONObject doPostHttpCall(RequestParams<Map<Object, Object>> requestParam) throws Exception{

		String body = JSONObject.toJSONString(requestParam.getData());
		RequestBody requestBody = RequestBody.create(body, JSON);
		Request request = new Request.Builder().url(requestParam.getDdcOpenApiUrl()).post(requestBody)
			      .addHeader("apitoken", requestParam.getDdcOpenApiToken()).header("Connection", "close").build();
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS).build();

		String respDataStr = null;
		logger.info("The request urlrequestParam = {RequestParams@7735}  is: {}, the request body is: {}", requestParam.getDdcOpenApiUrl(), body);
		try (Response response = client.newCall(request).execute()) {
			respDataStr = Objects.requireNonNull(response.body()).string();
			logger.info("The response data is: {}", respDataStr);
		}

		return JSONObject.parseObject(respDataStr);
	}

	public static String doOkHttpCall(Request request) throws Exception{

		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS).build();

		String respDataStr = null;
		logger.info("The request is: {}", JSONObject.toJSONString(request.body()));
		try (Response response = client.newCall(request).execute()) {

			if (response.code() != 200) {
				throw new ChinaTrustBaseException(response.message());
			}
			respDataStr = Objects.requireNonNull(response.body()).string();
			logger.info("The response data is: {}", respDataStr);
		}

		return respDataStr;
	}
	
}
