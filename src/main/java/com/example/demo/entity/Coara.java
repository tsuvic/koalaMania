package com.example.demo.entity;

import java.util.Date;

public class Coara {
	
	public Coara() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private int coara_id;
	private String name;
	private int is_male;
	private Date birthdate;
	private int is_alive;
	private Date deathdate;
	private String zoo;
	private String mother;
	private String father;
	
	public int getCoara_id() {
		return coara_id;
	}
	public void setCoara_id(int coara_id) {
		this.coara_id = coara_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIs_male() {
		return is_male;
	}
	public void setIs_male(int is_male) {
		this.is_male = is_male;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public int getIs_alive() {
		return is_alive;
	}
	public void setIs_alive(int is_alive) {
		this.is_alive = is_alive;
	}
	public String getZoo() {
		return zoo;
	}
	public void setZoo(String zoo) {
		this.zoo = zoo;
	}
	public String getMother() {
		return mother;
	}
	public void setMother(String mother) {
		this.mother = mother;
	}
	public String getFather() {
		return father;
	}
	public void setFather(String father) {
		this.father = father;
	}
	public Date getDeathdate() {
		return deathdate;
	}
	public void setDeathdate(Date deathdate) {
		this.deathdate = deathdate;
	}
}
