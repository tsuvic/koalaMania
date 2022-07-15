package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AnimalType {

	public AnimalType() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public final String TABLE_NAME = "animal_type";
	public final String COLUMN_ANIMAL_ID = "animaltype_id";
	public final String COLUMN_NAME = "name";
	private int animalTypeId;
	String name;
}
