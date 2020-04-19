package com.phoneservice.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.phoneservice.util.PaginationUtil;
import com.phoneservice.util.PhoneNumberCombinationUtil;

@CrossOrigin
@RestController
public class PhoneServiceController {
	
	@PostMapping("/v1.0/phoneNumber")
    public ResponseEntity<BasicDBObject> getPhoneNumberCombinations(
    		HttpServletRequest httpServRequest,  
    		@RequestBody String json) 
    {
		DBObject inputObj = (DBObject) JSON.parse(json);
		int page = (int) inputObj.get("page");
		int limit = (int) inputObj.get("limit");
		DBObject filterObj = (DBObject) inputObj.get("filters");
		String phoneNumber = (String) filterObj.get("phoneNumber");
		List<DBObject> responseList = new ArrayList<>();
		List<?> lettersList = PhoneNumberCombinationUtil.fetchPhoneNumberCombinations(phoneNumber); 
		for(int i=0;i<lettersList.size();i++) {
			DBObject obj = new BasicDBObject();
			obj.put("key", lettersList.get(i));
			responseList.add(obj);
		}
		System.out.println("list size:" + responseList.size());
		return new ResponseEntity<>(new BasicDBObject("total", 
				responseList.size()).append("page", page).append("limit", limit).append("data", 
				PaginationUtil.paginateDbObjListHelper(responseList, page, limit)),
				HttpStatus.OK);

    }
}
