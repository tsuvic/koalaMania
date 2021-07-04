package com.example.demo.entity;


import java.util.List;

import lombok.Data;

@Data
public class AnimalForTree {
	
	public AnimalForTree() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String name = "";
	private int id = 9999;
	private boolean hidden = true;
	private boolean no_parent = true;

	private String profileImagePath;
//	private int animal_id;
	private int sex = 99;
//	private Date birthdate;
//	private int is_alive;
//	private Date deathdate;
//	private String zooName;
	private int mother_id;
	private int father_id;
	private List<AnimalForTree> children; //リストの型に自分のオブジェクトを指定できる？（再帰的）
	
	
}
