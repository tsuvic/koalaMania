package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Koala {
	
	public Koala() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private int koala_id;
	private String name;
	private int sex;
	private Date birthdate;
	private int is_alive;
	private Date deathdate;
	private int zoo;
	private String zooName;
	private String mother;
	private String father;
	private int mother_id;
	private int father_id;
	private String details;
	private String feature;
	private String stringBirthDate;
	private String stringDeathDate;
	private List<KoalaImage> koalaImageList;
	private KoalaProfileImage koalaProfileImage;
	
}
