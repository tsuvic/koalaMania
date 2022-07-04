package com.example.demo.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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

	public final String COLUMN_TITLE = "title";

	private int post_id;
	private String title;
	private String contents;
	private Zoo zoo;
	private List<PostImage> postImageList;
	private Date visitDate;
	private Date createdDate;
	private LoginUser loginUser; //削除予定 フロントからユーザー自体を受け取るため、IDではなくユーザーオブジェクトを保持している
	private int userId; //新規追加
	private Post parentPost; //削除予定
	private int parentPostId; //新規追加
	private List<Post> childrenPost;
	private String displayDiffTime;
	private long childrenCount;
	private boolean favoriteFlag;
	private long favoriteCount;
}
