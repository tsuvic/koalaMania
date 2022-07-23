package com.example.demo.repository;

import com.example.demo.entity.Animal;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.entity.PostImage;
import com.example.demo.util.CommonSqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class PostImageDaoImple implements PostImageDao {

	private final JdbcTemplate jdbcTemplate;

	public PostImageDaoImple(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	private CommonSqlUtil commonSqlUtil;

	@Autowired
	private PostImage ENTITY_POST_IMAGE;

	@Autowired
	private Post ENTITY_POST;

	@Autowired
	private LoginUser ENTITY_LOGIN_USER;

	@Autowired
	private Animal ENTITY_ANIMAL;

	@Override
	public List<PostImage> getPostImageListByUserId(int user_id) {
		String sql = "SELECT " +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_POST_ID + "," +
				ENTITY_POST.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + "," +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID + "," +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH + "," +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + "," +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_NAME +
				" FROM " + ENTITY_POST.TABLE_NAME +
				" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_USER_ID + " = " +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +
				" INNER JOIN " + ENTITY_POST_IMAGE.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_POST_ID + " = " +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
				" LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " ON " + ENTITY_POST_IMAGE.TABLE_NAME + "."
				+ ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID + " = " +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID +
				" WHERE " + ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_USER_ID + " =  ?  " +
				" ORDER BY " + ENTITY_POST_IMAGE.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + " DESC";;

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, user_id);

		List<PostImage> returnPostImageList = new ArrayList<PostImage>();

		for (Map<String, Object> result : resultList) {
			PostImage postImage = new PostImage();
			postImage.setPostimage_id((int) result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID));
			postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
			if (result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID) != null) {
				Animal animal = new Animal();
				animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
				animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
				postImage.setAnimal(animal);
			}

			Post post = new Post();
			post.setPostId((int) result.get(ENTITY_POST.COLUMN_POST_ID));
			post.setCreatedDate((Date) result.get(commonSqlUtil.COLUMN_CREATE_DATE));

			postImage.setPost(post);
			returnPostImageList.add(postImage);
		}
		return returnPostImageList;
	}

	@Override
	public int insertNewPostImage(int post_id, int animalId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = ((LoginUser) principal).getUser_id();

		String sql = "INSERT INTO " + ENTITY_POST_IMAGE.TABLE_NAME + "(" + ENTITY_POST_IMAGE.COLUMN_POST_ID + ", "
				+ ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID + ", " + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS + ")"
				+ "VALUES(?, ?, ?)RETURNING " + ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID + "";
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, post_id, animalId, null);
		commonSqlUtil.updateAllCommonColumn(ENTITY_POST_IMAGE.TABLE_NAME, ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID,
				user_id,
				(int) result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID));

		return (int) result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID);
	}

	@Override
	public void updateUrl(int postImageId, String url) {
		jdbcTemplate.update("UPDATE " + ENTITY_POST_IMAGE.TABLE_NAME + " SET " + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS
				+ " = ? WHERE " +
				ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID + " = ?", url, postImageId);
	}

	@Override
	public List<PostImage> getPostImageListByAnimalId(int animal_id) {
		String sql = "SELECT " +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID + "," +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS + "," +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
				" FROM " + ENTITY_POST_IMAGE.TABLE_NAME +
				" WHERE " + ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID + " =  ?  " +
				" ORDER BY " + ENTITY_POST_IMAGE.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + " DESC";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, animal_id);

		List<PostImage> returnPostImageList = new ArrayList<PostImage>();

		if (resultList.size() > 0) {
			for (Map<String, Object> result : resultList) {
				PostImage postImage = new PostImage();
				postImage.setPostimage_id((int) result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID));
				postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
				Post post = new Post();
				post.setPostId((int) result.get(ENTITY_POST_IMAGE.COLUMN_POST_ID));
				postImage.setPost(post);
				returnPostImageList.add(postImage);
			}
		}
		return returnPostImageList;
	}


	@Override
	public PostImage getPostImageByPostImageId(int postImage_id) {

		String sql = "SELECT " +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID + ", " +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_POST_ID + ", " +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID + ", " +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME +
				" FROM  " +
				ENTITY_POST_IMAGE.TABLE_NAME +
				" LEFT OUTER JOIN " + ENTITY_POST.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_POST_ID + " = " +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
				" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " ON " + ENTITY_LOGIN_USER.TABLE_NAME + "."
				+ ENTITY_LOGIN_USER.COLUMN_USER_ID + " = " +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_USER_ID +
				" WHERE " + ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID + " = ? ";

		Map<String, Object> resultList = jdbcTemplate.queryForMap(sql, postImage_id);

		PostImage postImage = new PostImage();
		postImage.setPostimage_id((int) resultList.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID));
		Post post = new Post();
		post.setPostId((int) resultList.get(ENTITY_POST.COLUMN_POST_ID));
		LoginUser user = new LoginUser();
		user.setUser_id((int) resultList.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
		user.setUserName((String) resultList.get(ENTITY_LOGIN_USER.COLUMN_USER_NAME));
		post.setUser(user);
		postImage.setPost(post);

		return postImage;
	}
}
