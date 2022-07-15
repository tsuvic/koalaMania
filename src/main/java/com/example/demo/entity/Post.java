package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
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

	@Id
	private int postId;

	@NotBlank /* 文字列 */
	@Length(max = 50)
	private String title;

	@NotBlank /* 文字列 */
	@Length(max = 400)
	private String contents;

	@NotNull /* null */
	@NotEmpty /* 空配列 */
	private List<PostImage> postImageList;

	@NotNull /* null */
	@NotEmpty /* 空配列 */
	private List<Animal> animalList;

	@NotNull /* null */
	private LoginUser user;

	@NotNull /* null */
	@PastOrPresent
	private Date visitDate;

	@NotNull /* null */
	private Zoo zoo;

	private Post parentPost;

	private List<Post> childrenPost;

	private boolean favoriteFlag;

	@PositiveOrZero
	private long favoriteCount;

	private String displayDiffTime;//削除予定
	private long childrenCount;//削除予定
	private Date createdDate; //削除予定
}
