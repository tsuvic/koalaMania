package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@JsonIgnoreProperties({"TABLE_NAME","COLUMN_POSTIMAGE_ID","COLUMN_POST_ID","COLUMN_ANIMAL_ID","COLUMN_IMAGE_ADDRESS","post"})
public class PostImage {

	public final String TABLE_NAME = "post_image";
	public final String COLUMN_POSTIMAGE_ID = "postimage_id";
	public final String COLUMN_POST_ID = "post_id";
	public final String COLUMN_ANIMAL_ID = "animal_id";
	public final String COLUMN_IMAGE_ADDRESS = "image_address";

	private int postimage_id;
	private String imageAddress;
	private Animal animal;
	private Post post;
	private PostImageFavorite postImageFavorite;
}
