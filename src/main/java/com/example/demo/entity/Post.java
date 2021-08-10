package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Post {

	public final String TABLE_NAME = "post";
		
	public final String COLUMN_POST_ID = "post_id";
	public final String COLUMN_USER_ID = "user_id";
	public final String COLUMN_ZOO_ID = "zoo_id";
	public final String COLUMN_ANIMALTYPE_ID = "animaltype_id";
	public final String COLUMN_PARENT_ID = "parent_id";
	public final String COLUMN_CONTENTS = "contents";
	public final String COLUMN_VISIT_DATE = "visit_date";

	private int post_id;
	private String contents;
	private List<PostImage> postImageList;
	private Date visitDate;
	private Date createdDate;
	private LoginUser loginUser;
	private Post parentPost;
	private List<Post> childrenPost;
	private Zoo zoo;
	private String displayDiffTime;
	private long childrenCount;
	private boolean favoriteFlag;
}
