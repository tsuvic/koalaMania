package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PostFavorite {

	public final String TABLE_NAME = "post_favorite";
		
	public final String COLUMN_POST_FAVORITE_ID = "post_id";
	public final String COLUMN_POST_ID = "post_id";
	public final String COLUMN_USER_ID = "user_id";

	private int postFavoriteId;
	private Post post;
	private LoginUser user;
}
