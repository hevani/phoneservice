package com.phoneservice.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.phoneservice.demo.dao.PhoneDAO;
import com.phoneservice.demo.model.Phone;

@PropertySource("classpath:application.properties")
@ComponentScan("com")
@SpringBootApplication
public class PhoneServiceApplication implements CommandLineRunner {
	private final PhoneDAO phoneDAO;
	  @Autowired
	  public PhoneServiceApplication(PhoneDAO phoneDAO) {
	     this.phoneDAO = phoneDAO;
	  }
	public static void main(String[] args) {
		SpringApplication.run(PhoneServiceApplication.class, args);
		
	}
	@Override
	  public void run(String... args) {
		Phone phone = new Phone();
		phone.setName("iphone7Plus");
		phone.setNumber("1234");
		
		phoneDAO.savePhone(phone);
		
	}
}
