package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PostImageFavorite {

	public final String TABLE_NAME = "post_image_favorite";
	
	public final String COLUMN_POST_IMAGE_FAVORITE_ID = "postimagefavorite_id";
	public final String COLUMN_POST_IMAGE_ID = "postimage_id";
	public final String COLUMN_USER_ID = "user_id";

	private int postimageFavoriteId;
	private PostImage postImage;
	private LoginUser user;
	private long count;
	private boolean flag;
}
