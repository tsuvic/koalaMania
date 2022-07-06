package com.example.demo.repository;

import com.example.demo.app.UserAuthenticationUtil;
import com.example.demo.entity.*;
import com.example.demo.util.CommonSqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PostFavoriteDaoImpl implements PostFavoriteDao {

	private final JdbcTemplate jdbcTemplate;

	private final CommonSqlUtil commonSqlUtil;

	@Autowired
	private PostFavorite ENTITY_POST_FAVORITE;

	@Autowired
	private Post ENTITY_POST;

	@Autowired
	private PostImage ENTITY_POST_IMAGE;

	@Autowired
	private LoginUser ENTITY_LOGIN_USER;

	@Autowired
	private Animal ENTITY_ANIMAL;

	@Autowired
	private Zoo ENTITY_ZOO;

	@Autowired
	private Prefecture ENTITY_PREFECTURE;

	private UserAuthenticationUtil userAuthenticationUtil;

	@Autowired
	public PostFavoriteDaoImpl(JdbcTemplate jdbcTemplate, CommonSqlUtil commonSqlUtil,
			UserAuthenticationUtil userAuthenticationUtil) {
		this.jdbcTemplate = jdbcTemplate;
		this.commonSqlUtil = commonSqlUtil;
		this.userAuthenticationUtil = userAuthenticationUtil;
	}

	@Override
	public List<Post> getPostFavoriteList(List<Post> postList) {
		
		postList.stream()
		.forEach(post ->getPostFavoirteCount(post));

		if (userAuthenticationUtil.isUserAuthenticated() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int user_id = ((LoginUser) principal).getUser_id();

			if (postList.size() > 0) {
				String placeHolder = postList.stream().map(post -> String.valueOf(post.getPostId()))
						.collect(Collectors.joining(","));

				String sql = "SELECT " +
						ENTITY_POST_FAVORITE.COLUMN_POST_ID +
						" FROM " + ENTITY_POST_FAVORITE.TABLE_NAME +
						" WHERE " + ENTITY_POST_FAVORITE.COLUMN_POST_ID + " IN (" + placeHolder + ") " +
						" AND " + ENTITY_POST_FAVORITE.COLUMN_USER_ID + " = ? ";
				List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, user_id);

				for (Map<String, Object> result : resultList) {
					postList.stream()
							.filter(post -> post.getPostId() == (int) result.get(ENTITY_POST_FAVORITE.COLUMN_POST_ID))
							.forEach(post -> post.setFavoriteFlag(true));
				}
			}

			return postList;

		} else {
			return postList;
		}

	}

	@Override
	public long insertPostFavorite(int post_id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = ((LoginUser) principal).getUser_id();

		String sql = "INSERT INTO " +
				ENTITY_POST_FAVORITE.TABLE_NAME +
				" ( " + ENTITY_POST_FAVORITE.COLUMN_POST_ID + " , " +
				ENTITY_POST_FAVORITE.COLUMN_USER_ID + " ) " +
				" VALUES (?,?)  RETURNING " + ENTITY_POST_FAVORITE.COLUMN_POST_FAVORITE_ID;

		Map<String, Object> result = jdbcTemplate.queryForMap(sql, post_id, user_id);
		commonSqlUtil.updateAllCommonColumn(ENTITY_POST_FAVORITE.TABLE_NAME,
				ENTITY_POST_FAVORITE.COLUMN_POST_FAVORITE_ID, user_id,
				(int) result.get(ENTITY_POST_FAVORITE.COLUMN_POST_FAVORITE_ID));
		
		Post post = new Post();
		post.setPostId(post_id);
		
		getPostFavoirteCount(post);
		
		return post.getFavoriteCount();
	}

	@Override
	public long deletePostFavorite(int post_id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = ((LoginUser) principal).getUser_id();

		String sql = "DELETE FROM " +
				ENTITY_POST_FAVORITE.TABLE_NAME +
				" WHERE " + ENTITY_POST_FAVORITE.COLUMN_POST_ID + " = ? " +
				" AND " + ENTITY_POST_FAVORITE.COLUMN_USER_ID + " = ? ";

		jdbcTemplate.update(sql, post_id, user_id);
		
		Post post = new Post();
		post.setPostId(post_id);
		
		getPostFavoirteCount(post);
		
		return post.getFavoriteCount();
	}

	@Override
	public Post getPostFavorite(Post post) {
		
		getPostFavoirteCount(post);

		if (userAuthenticationUtil.isUserAuthenticated() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int user_id = ((LoginUser) principal).getUser_id();

			String sql = "SELECT COUNT(*) FROM " +
					ENTITY_POST_FAVORITE.TABLE_NAME +
					" WHERE " + ENTITY_POST_FAVORITE.COLUMN_POST_ID + " = ? " +
					" AND " + ENTITY_POST_FAVORITE.COLUMN_USER_ID + " = ? ";

			Map<String, Object> result = jdbcTemplate.queryForMap(sql, post.getPostId(), user_id);

			if ((long) result.get("count") > 0) {
				post.setFavoriteFlag(true);
			}

			if (post.getChildrenPost() != null && post.getChildrenPost().size() > 0) {
				post.setChildrenPost(getPostFavoriteList(post.getChildrenPost()));
			}

			return post;
		} else {
			return post;
		}
	}

	@Override
	public List<Post> getPostFavoirteByUserId(int user_id) {
		String asOriginalPost = "originalPost";
		String asOriginalPostId = "originalPostId";
		String asFavoritePost = "favoritePost";
		String asCommentFromPost = "commentFromPost";
		String asCommentFromPostId = "commentFromPostId";
		String asOrginalLoginUser = "OrginalLoginUser";
		String asOrginalLoginUserName = "OrginalLoginUserName";
		String asCommentFromLoginUser = "CommentFromLoginUser";
		String asCommentFromLoginUserName = "CommentFromLoginUserName";

		String sql = "SELECT " +
				asOriginalPost + "." + ENTITY_POST.COLUMN_POST_ID + " AS " + asOriginalPostId + "," +
				asOriginalPost + "." + ENTITY_POST.COLUMN_CONTENTS + "," +
				asOriginalPost + "." + ENTITY_POST.COLUMN_VISIT_DATE + "," +
				asOriginalPost + "." + commonSqlUtil.COLUMN_CREATE_DATE + "," +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS + "," +
				asOrginalLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID + "," +
				asOrginalLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME + " AS " + asOrginalLoginUserName + "," +
				asOrginalLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH + "," +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + "," +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_NAME + "," +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + "," +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + "," +
				ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_NAME + "," +
				asCommentFromPost + "." + ENTITY_POST.COLUMN_POST_ID + " AS " + asCommentFromPostId + "," +
				asCommentFromLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME + " AS " + asCommentFromLoginUserName
				+ " " +
				" FROM " + ENTITY_POST_FAVORITE.TABLE_NAME + " AS " + asFavoritePost +
				" LEFT OUTER JOIN " + ENTITY_POST.TABLE_NAME + " AS " + asOriginalPost + " ON " + asOriginalPost + "."
				+ ENTITY_POST.COLUMN_POST_ID + " = "
				+ asFavoritePost + "." + ENTITY_POST_FAVORITE.COLUMN_POST_ID +
				" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " AS " + asOrginalLoginUser + " ON "
				+ asOriginalPost + "."
				+ ENTITY_POST.COLUMN_USER_ID + " = " +
				asOrginalLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +
				" LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME + " ON " + asOriginalPost + "."
				+ ENTITY_POST.COLUMN_ZOO_ID + " = " +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID +
				" LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME + " ON " + ENTITY_PREFECTURE.TABLE_NAME + "."
				+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " = " +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_PREFECTURE_ID +
				" LEFT OUTER JOIN " + ENTITY_POST_IMAGE.TABLE_NAME + " ON " + asOriginalPost + "."
				+ ENTITY_POST.COLUMN_POST_ID + " = " +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
				" LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " ON " + ENTITY_POST_IMAGE.TABLE_NAME + "."
				+ ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID + " = " +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID +
				" LEFT OUTER JOIN " + ENTITY_POST.TABLE_NAME + " AS " + asCommentFromPost + " ON " + asOriginalPost
				+ "."
				+ ENTITY_POST.COLUMN_PARENT_ID + " = " +
				asCommentFromPost + "." + ENTITY_POST.COLUMN_POST_ID +
				" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " AS " + asCommentFromLoginUser + " ON "
				+ asCommentFromPost + "."
				+ ENTITY_POST.COLUMN_USER_ID + " = " +
				asCommentFromLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +
				" WHERE " + asFavoritePost + "." + ENTITY_POST_FAVORITE.COLUMN_USER_ID + " =  ?  " +
				" ORDER BY " + asFavoritePost + "." + commonSqlUtil.COLUMN_CREATE_DATE + " DESC";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, user_id);

		List<Post> returnPostList = new ArrayList<Post>();

		int before_post_id = 0;

		for (Map<String, Object> result : resultList) {

			if (before_post_id == (int) result.get(asOriginalPostId)) {
				PostImage postImage = new PostImage();
				postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
				if (result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID) != null) {
					Animal animal = new Animal();
					animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
					animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
					postImage.setAnimal(animal);
				}
				Post post = new Post();
				post.setPostId((int) result.get(asOriginalPostId));
				postImage.setPost(post);
				returnPostList.get(returnPostList.size() - 1).getPostImageList().add(postImage);

				before_post_id = (int) result.get(asOriginalPostId);

			} else {
				Post post = new Post();
				post.setPostId((int) result.get(asOriginalPostId));
				post.setContents((String) result.get(ENTITY_POST.COLUMN_CONTENTS));
				post.setVisitDate((Date) result.get(ENTITY_POST.COLUMN_VISIT_DATE));
				post.setCreatedDate((Date) result.get(commonSqlUtil.COLUMN_CREATE_DATE));
				LoginUser loginUser = new LoginUser();
				loginUser.setUser_id((int) result.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
				loginUser.setUserName((String) result.get(asOrginalLoginUserName));
				loginUser.setProfileImagePath((String) result.get(ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH));
				post.setLoginUser(loginUser);
				Zoo zoo = new Zoo();
				zoo.setZoo_id((int) result.get(ENTITY_ZOO.COLUMN_ZOO_ID));
				zoo.setZoo_name((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
				Prefecture prefecture = new Prefecture();
				prefecture.setName((String) result.get(ENTITY_PREFECTURE.COLUMN_PREFECTURE_NAME));
				zoo.setPrefecture(prefecture);
				post.setZoo(zoo);

				if (result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS) != null) {
					PostImage postImage = new PostImage();
					postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
					if (result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID) != null) {
						Animal animal = new Animal();
						animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
						animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
						postImage.setAnimal(animal);
					}
					post.setPostImageList(new ArrayList<PostImage>());
					post.getPostImageList().add(postImage);
					postImage.setPost(post);
				}

				if (result.get(asCommentFromPostId) != null) {
					Post commentpost = new Post();
					commentpost.setPostId((int) result.get(asCommentFromPostId));
					LoginUser commentloginUser = new LoginUser();
					commentloginUser.setUserName((String) result.get(asCommentFromLoginUserName));
					commentpost.setLoginUser(commentloginUser);
					post.setParentPost(commentpost);
				}

				returnPostList.add(post);

				before_post_id = (int) result.get(asOriginalPostId);
			}
		}

		if (returnPostList.size() > 0) {
			final String placeHolder = returnPostList.stream().map(post -> String.valueOf(post.getPostId()))
					.collect(Collectors.joining(","));

			String asChildPost = "childpost";
			String asParentPost = "parentpost";

			String sql2 = "SELECT " +
					asParentPost + "." + ENTITY_POST.COLUMN_POST_ID + "," +
					"COUNT(" +
					asChildPost + "." + ENTITY_POST.COLUMN_POST_ID + ") " +
					" FROM " + ENTITY_POST.TABLE_NAME + " AS " + asParentPost +
					" LEFT OUTER JOIN " + ENTITY_POST.TABLE_NAME + " AS " + asChildPost + " ON " + asChildPost + "."
					+ ENTITY_POST.COLUMN_PARENT_ID + " = " +
					asParentPost + "." + ENTITY_POST.COLUMN_POST_ID +
					" WHERE " + asParentPost + "." + ENTITY_POST.COLUMN_POST_ID + " IN (" + placeHolder + ") " +
					" GROUP BY " + asParentPost + "." + ENTITY_POST.COLUMN_POST_ID;
			List<Map<String, Object>> resultList2 = jdbcTemplate.queryForList(sql2);

			for (Post parentPost : returnPostList) {
				resultList2.stream()
						.filter(count -> parentPost.getPostId() == (int) count.get(ENTITY_POST.COLUMN_POST_ID))
						.forEach(count -> parentPost.setChildrenCount((long) count.get("count")));
			}
		}

		return returnPostList;
	}

	@Override
	public Post getPostFavoirteCount(Post post) {
		String sql = "SELECT COUNT(*) FROM " +
				ENTITY_POST_FAVORITE.TABLE_NAME +
				" WHERE " + ENTITY_POST_FAVORITE.COLUMN_POST_ID + " = ? ";

		Map<String, Object> result = jdbcTemplate.queryForMap(sql, post.getPostId());

		post.setFavoriteCount((long) result.get("count"));
		
		return post;

	}

}
