package com.phoneservice.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.context.annotation.Bean;

@Document(collection="person")
public class Phone {
	@Id
	private String _id;
	private String name;
	private String number;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Phone [_id=" + _id + ", name=" + name + ", number=" + number + "]";
	}
}
