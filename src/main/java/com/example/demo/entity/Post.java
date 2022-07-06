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
	public final String COLUMN_POST_ID = "postId";

	@JsonIgnore
	public final String COLUMN_USER_ID = "userId";

	@JsonIgnore
	public final String COLUMN_ZOO_ID = "zooId";

	@JsonIgnore
	public final String COLUMN_ANIMAL_TYPE_ID = "animaltype_id";

	@JsonIgnore
	public final String COLUMN_PARENT_ID = "parentId";

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
	private List<PostImage> postImageList;
	private Date visitDate;
	private Date createdDate; //削除予定
	private LoginUser loginUser;
	private Post parentPost;
	private List<Post> childrenPost;
	private String displayDiffTime;
	private long childrenCount;
	private boolean favoriteFlag;
	private long favoriteCount;
}
