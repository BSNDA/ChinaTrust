package com.reddate.chinatrust.controller;


import com.reddate.chinatrust.dto.DocumentIssuanceTx;
import com.reddate.chinatrust.param.req.*;
import com.reddate.chinatrust.service.BusinessService;
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

@RestController
@Api(value = "ChinaTrust服务接口",tags={"ChinaTrust服务接口"})
public class DocValidationController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(DocValidationController.class);

    @Autowired
    private BusinessService buzService;


    @ApiOperation("签发文档")
    @PostMapping("/issueDocument")
    public ResponseEntity<ResultData<DocumentIssuanceTx>> issueDocument(@RequestBody DocumentIssuanceReq docIssReq){

        try{
            return success(buzService.issueDocument(docIssReq));
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return error(e.getMessage());
        }
    }

    @ApiOperation("验证文档")
    @PostMapping("/validateDocument")
    public ResponseEntity<ResultData<Boolean>> validateDocument(@RequestBody DocumentValidationReq docValReq){

        try{
            return success(buzService.validateDocument(docValReq));
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return error(e.getMessage());
        }
    }

    @ApiOperation("移交文档")
    @PostMapping("/transferDocument")
    public ResponseEntity<ResultData<String>> transferDocument(@RequestBody DocumentTransferReq docTraReq){

        try{
            return success(buzService.transferDocument(docTraReq));
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return error(e.getMessage());
        }
    }

    @ApiOperation("查询文档拥有者")
    @PostMapping("/queryDocumentOwner")
    public ResponseEntity<ResultData<String>> queryDocumentOwner(@RequestBody DocOwnerQueryReq ownerQryReq){

        try{
            return success(buzService.queryDocumentOwner(ownerQryReq));
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return error(e.getMessage());
        }
    }

    @ApiOperation("销毁文档")
    @PostMapping("/destroyDocument")
    public ResponseEntity<ResultData<String>> destroyDocument(@RequestBody DocumentDestroyReq docDesReq){

        try{
            return success(buzService.destroyDocument(docDesReq));
        }catch (Exception e){
            StringWriter errorsWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorsWriter));
            logger.error(errorsWriter.toString());
            return error(e.getMessage());
        }
    }

}
