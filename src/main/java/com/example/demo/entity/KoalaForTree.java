package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class KoalaForTree {
	
	public KoalaForTree() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private int koala_id;
	private String name;
	private int sex;
	private Date birthdate;
	private int is_alive;
	private Date deathdate;
	private String zooName;
	private int mother_id;
	private int father_id;
	private String profileImagePath;
	
}
