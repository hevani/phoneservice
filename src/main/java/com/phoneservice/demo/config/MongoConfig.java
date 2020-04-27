package com.phoneservice.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

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

  @Override
  public MongoClient mongoClient() {
	  Builder optionsBuilder = MongoClientOptions.builder()
				.connectionsPerHost(10)
				.connectTimeout(10000)
				.threadsAllowedToBlockForConnectionMultiplier(5);
		
		MongoClientURI mclienturiObj = new MongoClientURI(connectionUri, optionsBuilder);
		return new MongoClient(mclienturiObj );
  }
}