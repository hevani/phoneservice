package com.phoneservice.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.phoneservice.demo.model.Phone;

@Repository
public class PhoneDAOImpl implements PhoneDAO{
	private final MongoTemplate mongoTemplate;
	@Autowired
	public PhoneDAOImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
		
	}
	@Override
	public Phone savePhone(Phone phone) {
		mongoTemplate.save(phone);
		return phone;
	}
}
