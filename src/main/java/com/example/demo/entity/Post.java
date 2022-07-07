package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@JsonIgnore
	public final String TABLE_NAME = "post";

	@JsonIgnore
	public final String COLUMN_POST_ID = "post_id";

	@JsonIgnore
	public final String COLUMN_USER_ID = "user_id";

	@JsonIgnore
	public final String COLUMN_ZOO_ID = "zoo_id";

	@JsonIgnore
	public final String COLUMN_PARENT_ID = "parent_id";

	@JsonIgnore
	public final String COLUMN_CONTENTS = "contents";

	@JsonIgnore
	public final String COLUMN_VISIT_DATE = "visit_date";

	@JsonIgnore
	public final String COLUMN_TITLE = "title";

	private int postId;
	private String title;
	private String contents;
	private List<PostImage> postImageList;
	private List<Animal> animalList;

	private LoginUser user;
	private Date visitDate;
	private Zoo zoo;

	private Post parentPost;
	private List<Post> childrenPost;

	private boolean favoriteFlag;
	private long favoriteCount;

	private String displayDiffTime;//削除予定
	private long childrenCount;//削除予定
	private Date createdDate; //削除予定
}
