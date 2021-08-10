package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Animal {

	public Animal() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public final String TABLE_NAME = "animal";
	
	public final String COLUMN_ANIMAL_ID = "animal_id";
	public final String COLUMN_ANIMALTYPE_ID = "animaltype_id";
	public final String COLUMN_NAME = "name";
	public final String COLUMN_SEX = "sex";
	public final String COLUMN_BIRTHDATE = "birthdate";
	public final String COLUMN_IS_ALIVE = "is_alive";
	public final String COLUMN_DEATHDATE = "deathdate";
	public final String COLUMN_MOTHER = "mother";
	public final String COLUMN_FATHER = "father";
	public final String COLUMN_DETAILS = "details";
	public final String COLUMN_FEATURE = "feature";
	public final String COLUMN_PROFILE_IMAGE_TYPE = "profile_image_type";
	
	
	private int animal_id;
	private String name;
	private int sex;
	private Date birthdate;
	private int is_alive;
	private Date deathdate;
	/*private String mother;
	private String father;
	private int mother_id;
	private int father_id;*/
	private String details;
	private String feature;
	private String stringBirthDate;
	private String stringDeathDate;
	private String profileImagePath;
	private AnimalType animalType;
	private Animal motherAnimal;
	private Animal fatherAnimal;
	private List<AnimalZooHistory> animalZooHistoryList;
}
