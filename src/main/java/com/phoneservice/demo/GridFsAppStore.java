package com.phoneservice.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.phoneservice.demo.config.MongoConfig;

public class GridFsAppStore {

	public static void main(String[] args) throws IllegalStateException, IOException {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");

		DBObject metaData = new BasicDBObject();
		metaData.put("extra1", "anything 1");
		metaData.put("extra2", "anything 2");

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/Users/hevani/Documents/ReviewFetch.json");
			gridOperations.store(inputStream, "ReviewFetch.json", "application/json", metaData);

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
	               new Query().addCriteria(Criteria.where("filename").is("ReviewFetch.json")));
        System.out.println("result::" + result);
        try ( MongoCursor<GridFSFile> iterator = result.iterator() ) {
            while ( iterator.hasNext() ) {
              GridFSFile file = iterator.next();
              GridFsResource resource = gridOperations.getResource(file.getFilename());
              System.out.println("fileName:" + file.toString());
              System.out.println("file content:" + resource.getInputStream());
            }
          }

		System.out.println("Done");
	}
}
