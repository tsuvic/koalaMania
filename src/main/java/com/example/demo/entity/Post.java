package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@Component
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
	public final String COLUMN_ANIMAL_TYPE_ID = "animaltype_id";

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
	private Zoo zoo;
	private Date visitDate;
	private List<PostImage> postImageList;
	private LoginUser user;
	private Post parentPost;//コメント先
	private List<Post> childrenPost;//コメント
	private boolean favoriteFlag;
	private long favoriteCount;

	private String displayDiffTime;//削除予定
	private long childrenCount;//削除予定

	private Date createdDate; //削除予定
}
