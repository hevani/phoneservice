package com.phoneservice.demo.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.util.JSON;
import com.phoneservice.demo.config.MongoConfig;
import com.phoneservice.util.PaginationUtil;
import com.phoneservice.util.PhoneNumberCombinationUtil;
import org.apache.commons.io.IOUtils;

import org.springframework.http.MediaType;

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
	@GetMapping("/v1.0/file/download")
	public ResponseEntity retrieveFile() throws IllegalStateException, IOException {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");

		DBObject metaData = new BasicDBObject();
		metaData.put("extra1", "anything 1");
		metaData.put("extra2", "anything 2");

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/Users/hevani/Desktop/iron883.jpg");
			gridOperations.store(inputStream, "iron883.jpg", "application/jpg", metaData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		GridFSFindIterable result = gridOperations.find(
	               new Query().addCriteria(Criteria.where("filename").is("iron883.jpg")));
        System.out.println("result::" + result);
        GridFSFile gridFsFile = null;
        GridFsResource resource = null;
        try ( MongoCursor<GridFSFile> iterator = result.iterator() ) {
            while ( iterator.hasNext() ) {
              gridFsFile = iterator.next();
              resource = gridOperations.getResource(gridFsFile.getFilename());
              System.out.println("fileName:" + gridFsFile.toString());
              //System.out.println("file content:" + resource.getInputStream());
            }
          }
        
        byte[] content = IOUtils.toByteArray(resource.getInputStream()); 

   
        return ResponseEntity.ok()
                .contentLength(gridFsFile.getLength())
                .contentType(MediaType.parseMediaType("application/jpg"))
                .body(content);
	}
}
