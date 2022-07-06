package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PostFavorite {

	@JsonIgnore
	public final String TABLE_NAME = "post_favorite";

	@JsonIgnore
	public final String COLUMN_POST_FAVORITE_ID = "postId";

	@JsonIgnore
	public final String COLUMN_POST_ID = "postId";

	@JsonIgnore
	public final String COLUMN_USER_ID = "userId";

	private int postFavoriteId;
	private Post post;
	private LoginUser user;
}
