package com.reddate.chinatrust.service;


import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.reddate.chinatrust.config.ConfConstant;
import com.reddate.chinatrust.exception.ChinaTrustBaseException;
import com.reddate.chinatrust.util.OkHttpUtils;
import com.reddate.chinatrust.vo.request.RequestParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DdcManageService {

    private static final Logger logger = LoggerFactory.getLogger(DdcManageService.class);

    @Value("${ddcOpenApiUrl}")
    private String ddcOpenApiUrl;

    @Value("${ddcOpenApiToken}")
    private String ddcOpenApiToken;


    public JSONObject queryDocTransferRec(Map<Object, Object> map) throws Exception{

        validateInput(map);
        RequestParams<Map<Object, Object>> reqParam = new RequestParams<Map<Object, Object>>(
                ddcOpenApiUrl + ConfConstant.TRANSFER_QUERY_PATH, ddcOpenApiToken, map);
        return OkHttpUtils.doPostHttpCall(reqParam);
    }

    public JSONObject queryOpbFrameworks() throws Exception{

        RequestParams<Map<Object, Object>> reqParam = new RequestParams<Map<Object, Object>>(
                ddcOpenApiUrl + ConfConstant.OPB_FRAMEWORK_QUERY_PATH, ddcOpenApiToken, null);
        return OkHttpUtils.doPostHttpCall(reqParam);
    }

    public JSONObject createPublicPrivateKey() throws Exception{

        Map<Object, Object> map = new HashMap<>();
        map.put("opbChainId", ConfConstant.TAIAN_OPB_CHAIN_ID);
        RequestParams<Map<Object, Object>> reqParam = new RequestParams<Map<Object, Object>>(
                ddcOpenApiUrl + ConfConstant.PUBLIC_PRIVATE_KEY_CREATE_PATH, ddcOpenApiToken, map);
        return OkHttpUtils.doPostHttpCall(reqParam);
    }

    public JSONObject rechargeBusinessFee(Map<Object, Object> map) throws Exception{

        validateInput(map);
        map.put("opbChainId", ConfConstant.TAIAN_OPB_CHAIN_ID);
        RequestParams<Map<Object, Object>> reqParam = new RequestParams<Map<Object, Object>>(
                ddcOpenApiUrl + ConfConstant.BUSINESS_FEE_RECHARGE_PATH, ddcOpenApiToken, map);
        return OkHttpUtils.doPostHttpCall(reqParam);
    }

    public JSONObject rechargeGasFee(Map<Object, Object> map) throws Exception{

        validateInput(map);
        map.put("opbChainId", ConfConstant.TAIAN_OPB_CHAIN_ID);
        RequestParams<Map<Object, Object>> reqParam = new RequestParams<Map<Object, Object>>(
                ddcOpenApiUrl + ConfConstant.GAS_FEE_RECHARGE_PATH, ddcOpenApiToken, map);
        return OkHttpUtils.doPostHttpCall(reqParam);
    }

    private void validateInput(Map<Object, Object> map) {

        if(CollUtil.isEmpty(map)) {
            throw new ChinaTrustBaseException("The value of map is null");
        }
    }
}
