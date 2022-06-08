package com.reddate.chinatrust.service;


import cn.hutool.core.collection.CollUtil;
import com.reddate.chinatrust.exception.ChinaTrustBaseException;
import com.reddate.chinatrust.util.OkHttpUtils;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@Service
public class IpfsService {

    private static final Logger logger = LoggerFactory.getLogger(IpfsService.class);

    private final static String UPLOAD_URL_KEY = "uploadUrl";

    private final static String DATA_CONTENT_KEY = "dataContent";

    private final static String DOWNLOAD_URL_KEY = "downloadUrl";


    public String uploadData2Ipfs(Map<Object, Object> map) throws Exception{

        validateDataUploadInput(map);
        File file = null;
        try {
            file = new File(System.currentTimeMillis() + ".txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(String.valueOf(map.get(DATA_CONTENT_KEY)).getBytes("utf-8"));
            fileOutputStream.close();

            RequestBody fileRequestBody = RequestBody.create(OkHttpUtils.TEXT_PLAIN, file);
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("arg", file.getName(), fileRequestBody).build();
            Request request = new Request.Builder().url(String.valueOf(map.get(UPLOAD_URL_KEY))).post(requestBody).header("Connection", "close").build();

            return OkHttpUtils.doOkHttpCall(request);
        } finally {
            if (file != null && file.exists())
                file.delete();
        }
    }

    public String downloadDataFromIpfs(Map<Object, Object> map) throws Exception{

        validateDataDownloadInput(map);
        FormBody requestBody = new FormBody.Builder().build();
        Request request = new Request.Builder().url(String.valueOf(map.get(DOWNLOAD_URL_KEY))).post(requestBody).header("Connection", "close").build();
        return OkHttpUtils.doOkHttpCall(request);
    }

    private void validateDataDownloadInput(Map<Object, Object> map) {

        if(CollUtil.isEmpty(map)) {
            throw new ChinaTrustBaseException("The value of map is null");
        }
        if(!map.containsKey(DOWNLOAD_URL_KEY) || map.get(DOWNLOAD_URL_KEY) == null) {
            throw new ChinaTrustBaseException("The value of map.downloadUrl is null");
        }
    }

    private void validateDataUploadInput(Map<Object, Object> map) {

        if(CollUtil.isEmpty(map)) {
            throw new ChinaTrustBaseException("The value of map is null");
        }
        if(!map.containsKey(UPLOAD_URL_KEY) || map.get(UPLOAD_URL_KEY) == null) {
            throw new ChinaTrustBaseException("The value of map.uploadUrl is null");
        }
        if(!map.containsKey(DATA_CONTENT_KEY) || map.get(DATA_CONTENT_KEY) == null) {
            throw new ChinaTrustBaseException("The value of map.dataContent is null");
        }
    }
}
