package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.app.UserAuthenticationUtil;
import com.example.demo.entity.Animal;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.entity.PostImage;
import com.example.demo.entity.PostImageFavorite;
import com.example.demo.util.CommonSqlUtil;

@Repository
public class PostImageFavoriteDaoImpl implements PostImageFavoriteDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	private CommonSqlUtil commonSqlUtil;

	@Autowired
	private PostImageFavorite ENTITY_POST_IMAGE_FAVORITE;

	@Autowired
	private PostImage ENTITY_POST_IMAGE;

	@Autowired
	private Post ENTITY_POST;

	@Autowired
	private LoginUser ENTITY_LOGIN_USER;

	@Autowired
	private Animal ENTITY_ANIMAL;

	private UserAuthenticationUtil userAuthenticationUtil;

	public PostImageFavoriteDaoImpl(JdbcTemplate jdbcTemplate, UserAuthenticationUtil userAuthenticationUtil) {
		this.jdbcTemplate = jdbcTemplate;
		this.userAuthenticationUtil = userAuthenticationUtil;
	}

	@Override
	public PostImageFavorite checkPostImageFavoriteByPostImageId(int postImage_id) {

		String sql = "SELECT COUNT(*) FROM " +
				ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME +
				" WHERE " + ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_ID + " = ? ";

		Map<String, Object> result1 = jdbcTemplate.queryForMap(sql, postImage_id);

		PostImageFavorite postImageFavorite = new PostImageFavorite();

		postImageFavorite.setCount((long) result1.get("count"));

		if (userAuthenticationUtil.isUserAuthenticated() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int user_id = ((LoginUser) principal).getUser_id();
			String sql2 = "SELECT " +
					ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME + "."
					+ ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_FAVORITE_ID + ", " +
					ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID + ", " +
					ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_POST_ID + ", " +
					ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID + ", " +
					ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME +
					" FROM  " +
					ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME +
					" LEFT OUTER JOIN " + ENTITY_POST_IMAGE.TABLE_NAME + " ON " + ENTITY_POST_IMAGE.TABLE_NAME + "."
					+ ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID + " = " +
					ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME + "." + ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_ID +
					" LEFT OUTER JOIN " + ENTITY_POST.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
					+ ENTITY_POST.COLUMN_POST_ID + " = " +
					ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
					" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " ON " + ENTITY_LOGIN_USER.TABLE_NAME + "."
					+ ENTITY_LOGIN_USER.COLUMN_USER_ID + " = " +
					ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_USER_ID +
					" WHERE " + ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME + "."
					+ ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_ID + " = ? " +
					" AND " + ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME + "." + ENTITY_POST_IMAGE_FAVORITE.COLUMN_USER_ID
					+ " = ? ";

			List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql2, postImage_id, user_id);

			if (resultList.size() > 0) {
				postImageFavorite.setPostimageFavoriteId(
						(int) resultList.get(0).get(ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_FAVORITE_ID));
				postImageFavorite.setFlag(true);
				PostImage postImage = new PostImage();
				postImage.setPostimage_id((int) resultList.get(0).get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID));
				Post post = new Post();
				post.setPost_id((int) resultList.get(0).get(ENTITY_POST.COLUMN_POST_ID));
				LoginUser user = new LoginUser();
				user.setUser_id((int) resultList.get(0).get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
				user.setUserName((String) resultList.get(0).get(ENTITY_LOGIN_USER.COLUMN_USER_NAME));
				post.setLoginUser(user);
				postImage.setPost(post);
				postImageFavorite.setPostImage(postImage);
			}

			return postImageFavorite;

		} else {
			return postImageFavorite;
		}
	}

	@Override
	public int insertPostImageFavorite(int postImage_id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = ((LoginUser) principal).getUser_id();

		String sql = "INSERT INTO " +
				ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME +
				" ( " + ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_ID + "," +
				ENTITY_POST_IMAGE_FAVORITE.COLUMN_USER_ID + ")" +
				" VALUES(?,?) RETURNING " + ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_FAVORITE_ID;
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, postImage_id, user_id);

		commonSqlUtil.updateAllCommonColumn(ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME,
				ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_FAVORITE_ID, user_id,
				(int) result.get(ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_FAVORITE_ID));

		return (int) result.get(ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_FAVORITE_ID);
	}

	@Override
	public void deletePostImageFavorite(int postImage_id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = ((LoginUser) principal).getUser_id();

		String sql = "DELETE FROM " +
				ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME +
				" WHERE " + ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_ID + " = ?" +
				" AND " + ENTITY_POST_IMAGE_FAVORITE.COLUMN_USER_ID + " = ?";
		jdbcTemplate.update(sql, postImage_id, user_id);
	}

	@Override
	public List<PostImage> getPostImageFavoirteByUserId(int user_id) {
		String sql = "SELECT " +
				ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME + "." + ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_FAVORITE_ID
				+ "," +
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
				" INNER JOIN " + ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME + " ON " + ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME
				+ "."
				+ ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_ID + " = " +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID +
				" WHERE " + ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME + "." + ENTITY_POST_IMAGE_FAVORITE.COLUMN_USER_ID
				+ " =  ?  " +
				" ORDER BY " + ENTITY_POST_IMAGE_FAVORITE.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + " DESC";

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
			post.setPost_id((int) result.get(ENTITY_POST.COLUMN_POST_ID));
			post.setCreatedDate((Date) result.get(commonSqlUtil.COLUMN_CREATE_DATE));
			LoginUser loginUser = new LoginUser();
			loginUser.setUser_id((int) result.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
			loginUser.setUserName((String) result.get(ENTITY_LOGIN_USER.COLUMN_USER_NAME));

			post.setLoginUser(loginUser);
			postImage.setPost(post);

			PostImageFavorite postImageFavorite = new PostImageFavorite();
			postImageFavorite
					.setPostimageFavoriteId((int) result.get(ENTITY_POST_IMAGE_FAVORITE.COLUMN_POST_IMAGE_FAVORITE_ID));
			postImage.setPostImageFavorite(postImageFavorite);
			returnPostImageList.add(postImage);
		}

		return returnPostImageList;
	}

}
