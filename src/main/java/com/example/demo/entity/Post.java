package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@Component
public class Post {

	public final String TESSTTTTTTTT = "TESTTTTTTTTTTT";

	@JsonIgnore
	public final String TABLE_NAME = "post";

	@JsonIgnore
	public final String COLUMN_POST_ID = "post_id";

	@JsonIgnore
	public final String COLUMN_USER_ID = "user_id";

	@JsonIgnore
	public final String COLUMN_ZOO_ID = "zoo_id";

	@JsonIgnore
	public final String COLUMN_ANIMALTYPE_ID = "animaltype_id";

	@JsonIgnore
	public final String COLUMN_PARENT_ID = "parent_id";

	@JsonIgnore
	public final String COLUMN_CONTENTS = "contents";

	@JsonIgnore
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
	private long favoriteCount;
}
