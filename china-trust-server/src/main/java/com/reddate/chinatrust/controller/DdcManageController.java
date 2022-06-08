package com.reddate.chinatrust.controller;


import com.alibaba.fastjson.JSONObject;
import com.reddate.chinatrust.service.DdcManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@RestController
@Api(value = "DDC管理接口",tags={"DDC管理接口"})
public class DdcManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(DdcManageController.class);

    @Autowired
    private DdcManageService ddcManageService;


    @ApiOperation("查询文档移交记录")
    @PostMapping("/queryDocTransferRec")
    public JSONObject queryDocTransferRec(@RequestBody Map<Object, Object> map){

        try{
            return ddcManageService.queryDocTransferRec(map);
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return null;
        }
    }

    @ApiOperation("在线注册链账户")
    @PostMapping("/createPublicPrivateKey")
    public JSONObject createPublicPrivateKey(){

        try{
            return ddcManageService.createPublicPrivateKey();
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return null;
        }
    }

    @ApiOperation("业务费充值")
    @PostMapping("/rechargeBusinessFee")
    public JSONObject rechargeBusinessFee(@RequestBody Map<Object, Object> map){

        try{
            return ddcManageService.rechargeBusinessFee(map);
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return null;
        }
    }

    @ApiOperation("能量值充值")
    @PostMapping("/rechargeGasFee")
    public JSONObject rechargeGasFee(@RequestBody Map<Object, Object> map){

        try{
            return ddcManageService.rechargeGasFee(map);
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return null;
        }
    }
}
