package com.example.demo.entity;


import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AnimalImage {
	
	public final String TABLE_NAME = "animalimage";
	
	public final String COLUMN_ANIMAL_IMAGE_ID = "animalimage_id";
	public final String COLUMN_ANIMAL_ID = "animal_id";
	public final String COLUMN_FILETYPE = "filetype";
	
	
	private int animalimage_id;	
	private int animal_id;
	private String filetype;
	
	public AnimalImage(int animalimage_id,int animal_id,String filetype) {
		this.animalimage_id = animalimage_id;
		this.animal_id = animal_id;
		this.filetype = filetype;
	}

	public AnimalImage() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
}
