package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PostImage {

	public final String TABLE_NAME = "post_image";
	
	public final String COLUMN_POSTIMAGE_ID = "postimage_id";
	public final String COLUMN_POST_ID = "post_id";
	public final String COLUMN_TYPE = "type";
	public final String COLUMN_ANIMAL_ID = "animal_id";
	public final String COLUMN_IMAHE_ADDRESS = "image_address";

	private int postimage_id;
	private String type;
	private String imageAddress;
	private Animal animal;
	private Post post;
}
