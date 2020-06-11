package com.phoneservice.demo.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoClientOptions.Builder;

@Configuration
@PropertySource("classpath:application.properties")
public class MongoConfig extends AbstractMongoConfiguration {

	@Autowired
	private Environment env;

	@Value("${mongo.connectionUri}")
	private String connectionUri;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("mongo.database");
	}

	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter(), "filestore");
	}

	@Override
	public MongoClient mongoClient() {
		Builder optionsBuilder = MongoClientOptions.builder().connectionsPerHost(10).connectTimeout(10000)
				.threadsAllowedToBlockForConnectionMultiplier(5);

		MongoClientURI mclienturiObj = new MongoClientURI(connectionUri, optionsBuilder);
		return new MongoClient(mclienturiObj);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = new MongoTemplate(mongoDbFactory(), mappingMongoConverter());
			mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mongoTemplate;
	}

	@Bean
	public MongoDbFactory mongoDbFactory()  {
		return new SimpleMongoDbFactory(mongo(), getDatabaseName());
	}

	@Bean
	public MongoClient mongo(){
		Builder optionsBuilder = MongoClientOptions.builder().connectionsPerHost(10).connectTimeout(10000)
				.threadsAllowedToBlockForConnectionMultiplier(5);

		MongoClientURI mclienturiObj = new MongoClientURI(connectionUri, optionsBuilder);
		return new MongoClient(mclienturiObj);
	}
}