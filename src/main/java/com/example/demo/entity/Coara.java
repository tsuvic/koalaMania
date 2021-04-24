package com.example.demo.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Coara {
	
	public Coara() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private int coara_id;
	private String name;
	private int sex;
	private Date birthdate;
	private int is_alive;
	private Date deathdate;
	private String zoo;
	private String mother;
	private String father;
	private String details;
	private String feature;
	
	public Coara(String name, String zoo) {
		this.name = name;
		this.zoo = zoo;
	}
}
