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
	private String zoo;
	private String mother;
	private String father;
	private String details;
	private String feature;
	private String stringBirthDate;
	private String stringDeathDate;
	private List<KoalaImage> koalaImageList;
	
	public Koala(int koala_id,String name,int sex ,Date birthDate,
			int is_alive,Date deathDate,String zoo,String mother,String father,
			String details,String feature) {
		this.koala_id = koala_id;
		this.name = name;
		this.sex = sex;
		this.birthdate = birthDate;
		this.is_alive = is_alive;
		this.deathdate = deathDate;
		this.zoo = zoo;
		this.mother = mother;
		this.father = father;
		this.details = details;
		this.feature = feature;
	}
}
