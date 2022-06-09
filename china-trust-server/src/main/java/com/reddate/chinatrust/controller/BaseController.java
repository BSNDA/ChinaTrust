package com.reddate.chinatrust.controller;

import java.util.List;

import com.reddate.chinatrust.vo.ResultData;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class BaseController {

	public <T> ResponseEntity<ResultData<T>> success(T data){
		
		ResultData<T> response =  new ResultData<>();
		response.setCode(0);
		response.setMsg("success");
		response.setData(data);
		
		return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin","*")
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
	}

	public <T> ResponseEntity<ResultData<T>> error(String message){
		ResultData<T> response =  new ResultData<>();
		response.setMsg(message);

		return ResponseEntity.ok()
				.header("Access-Control-Allow-Origin","*")
				.contentType(MediaType.APPLICATION_JSON)
				.body(response);
	}
	
	
	public <T> ResponseEntity<ResultData<T>> error(String message,Class<T> classes){
		ResultData<T> response =  new ResultData<>();
		response.setMsg(message);
		
		return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin","*")
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
	}
	
	
	public <T> ResponseEntity<ResultData<List<T>>> listError(Integer code,String message,Class<T> classes){		
		ResultData<List<T>> response =  new ResultData<>();
		response.setCode(code);
		response.setMsg(message);
		
		return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin","*")
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
	}


	
}
