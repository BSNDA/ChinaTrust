package com.reddate.chinatrust.controller;


import com.alibaba.fastjson.JSONObject;
import com.reddate.chinatrust.dto.DocumentIssuance;
import com.reddate.chinatrust.service.IpfsService;
import com.reddate.chinatrust.vo.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@RestController
@Api(value = "IPFS接口",tags={"IPFS接口"})
public class IpfsController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(IpfsController.class);

    @Autowired
    private IpfsService ipfsService;


    @ApiOperation("上传数据到IPFS")
    @PostMapping("/uploadData2Ipfs")
    public ResponseEntity<ResultData<String>> uploadData2Ipfs(@RequestBody Map<Object, Object> map){

        try{
            return success(ipfsService.uploadData2Ipfs(map));
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return error(e.getMessage());
        }
    }

    @ApiOperation("从IPFS下载数据")
    @PostMapping("/downloadDataFromIpfs")
    public ResponseEntity<ResultData<String>> downloadDataFromIpfs(@RequestBody Map<Object, Object> map){

        try{
            return success(ipfsService.downloadDataFromIpfs(map));
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return error(e.getMessage());
        }
    }


}
