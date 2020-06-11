package com.phoneservice.demo;

import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.phoneservice.demo.config.MongoConfig;
import com.mongodb.CommandResult;
import com.mongodb.client.MongoDatabase;

public class MongoProcedureTest {
	public static void main(String args[]) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		MongoTemplate template = (MongoTemplate) ctx.getBean("mongoTemplate");
		System.out.println(">>"  + template.getCollection("Agency").count());
		MongoDatabase mdb = template.getDb();

		System.out.println("count>>" + mdb.getCollection("Agency").count());
		//Document doc1 = mdb.runCommand(new Document("$eval", "sum(1,2)"));
		//System.out.println(doc1);
		
		//Document doc1 = mdb.runCommand(new Document("sum(1,2)");
		//System.out.println(doc1);
		
	}
}
