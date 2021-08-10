package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Animal;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.entity.PostImage;
import com.example.demo.entity.Prefecture;
import com.example.demo.entity.Zoo;

@Repository
public class PostDaoImple implements PostDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PostDaoImple(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	private CommonSqlUtil commonSqlUtil;

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

	private int defaultParentId = 0;

	@Override
	public int insertNewPost(Post post) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = ((LoginUser) principal).getUser_id();
		String sql = "INSERT INTO " + ENTITY_POST.TABLE_NAME + "(" + ENTITY_POST.COLUMN_USER_ID + ", "
				+ ENTITY_POST.COLUMN_ZOO_ID + ", " + ENTITY_POST.COLUMN_PARENT_ID + ", "
				+ ENTITY_POST.COLUMN_CONTENTS + ", " + ENTITY_POST.COLUMN_VISIT_DATE + ")"
				+ "VALUES(?, ?, ?, ?, ?) RETURNING " + ENTITY_POST.COLUMN_POST_ID + "";

		Map<String, Object> result = jdbcTemplate.queryForMap(sql, user_id, post.getZoo().getZoo_id(),
				post.getParentPost().getPost_id(), post.getContents(), post.getVisitDate());

		commonSqlUtil.updateAllCommonColumn(ENTITY_POST.TABLE_NAME, ENTITY_POST.COLUMN_POST_ID, user_id,
				(int) result.get(ENTITY_POST.COLUMN_POST_ID));

		return (int) result.get(ENTITY_POST.COLUMN_POST_ID);
	}

	@Override
	public Post getPostByPostId(int post_id) {
		String sql = "SELECT " +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_POST_ID + "," +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_CONTENTS + "," +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_VISIT_DATE + "," +
				ENTITY_POST.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + "," +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + "," +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + "," +
				ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_NAME + "," +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID + "," +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH + "," +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + "," +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_NAME +
				" FROM " + ENTITY_POST.TABLE_NAME +
				" LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_ZOO_ID + " = " +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID +
				" LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME + " ON " + ENTITY_ZOO.TABLE_NAME + "."
				+ ENTITY_ZOO.COLUMN_PREFECTURE_ID + " = " +
				ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +
				" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_USER_ID + " = " +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +
				" LEFT OUTER JOIN " + ENTITY_POST_IMAGE.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_POST_ID + " = " +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
				" LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " ON " + ENTITY_POST_IMAGE.TABLE_NAME + "."
				+ ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID + " = " +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID +
				" WHERE " + ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_POST_ID + " =  ?  ";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, post_id);

		Post returnPost = new Post();

		returnPost.setPost_id((int) resultList.get(0).get(ENTITY_POST.COLUMN_POST_ID));
		returnPost.setContents((String) resultList.get(0).get(ENTITY_POST.COLUMN_CONTENTS));
		returnPost.setVisitDate((Date) resultList.get(0).get(ENTITY_POST.COLUMN_VISIT_DATE));
		returnPost.setCreatedDate((Date) resultList.get(0).get(commonSqlUtil.COLUMN_CREATE_DATE));
		Zoo zoo = new Zoo();
		zoo.setZoo_id((int) resultList.get(0).get(ENTITY_ZOO.COLUMN_ZOO_ID));
		zoo.setZoo_name((String) resultList.get(0).get(ENTITY_ZOO.COLUMN_ZOO_NAME));
		Prefecture prefecture = new Prefecture();
		prefecture.setName((String) resultList.get(0).get(ENTITY_PREFECTURE.COLUMN_PREFECTURE_NAME));
		zoo.setPrefecture(prefecture);
		returnPost.setZoo(zoo);
		LoginUser loginUser = new LoginUser();
		loginUser.setUser_id((int) resultList.get(0).get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
		loginUser.setUserName((String) resultList.get(0).get(ENTITY_LOGIN_USER.COLUMN_USER_NAME));
		loginUser.setProfileImagePath((String) resultList.get(0).get(ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH));
		returnPost.setLoginUser(loginUser);

		returnPost.setPostImageList(new ArrayList<PostImage>());

		for (Map<String, Object> result : resultList) {

			if (result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID) != null) {
				PostImage postImage = new PostImage();
				postImage.setPostimage_id((int) result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID));
				postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
				if (result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID) != null) {
					Animal animal = new Animal();
					animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
					animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
					postImage.setAnimal(animal);
				}
				returnPost.getPostImageList().add(postImage);
			}
		}

		String asChildPost = "childpost";

		String sql2 = "SELECT " +
				asChildPost + "." + ENTITY_POST.COLUMN_POST_ID + "," +
				asChildPost + "." + ENTITY_POST.COLUMN_CONTENTS + "," +
				asChildPost + "." + commonSqlUtil.COLUMN_CREATE_DATE + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH +
				" FROM " + ENTITY_POST.TABLE_NAME + " AS " + asChildPost +
				" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " ON " + asChildPost + "."
				+ ENTITY_POST.COLUMN_USER_ID + " = " +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +
				" WHERE " + asChildPost + "." + ENTITY_POST.COLUMN_PARENT_ID + " =  ?  " +
				" ORDER BY " + asChildPost + "." + commonSqlUtil.COLUMN_CREATE_DATE + " ASC";
		List<Map<String, Object>> resultList2 = jdbcTemplate.queryForList(sql2, post_id);

		if (resultList2.size() > 0 && resultList2.get(0).get(ENTITY_POST.COLUMN_POST_ID) != null) {
			List<Post> childrenPostLsit = new ArrayList<Post>();

			for (Map<String, Object> result : resultList2) {

				Post post = new Post();
				post.setPost_id((int) result.get(ENTITY_POST.COLUMN_POST_ID));
				post.setContents((String) result.get(ENTITY_POST.COLUMN_CONTENTS));
				post.setCreatedDate((Date) result.get(commonSqlUtil.COLUMN_CREATE_DATE));
				LoginUser loginUser2 = new LoginUser();
				loginUser2.setUser_id((int) result.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
				loginUser2.setUserName((String) result.get(ENTITY_LOGIN_USER.COLUMN_USER_NAME));
				loginUser2.setProfileImagePath((String) result.get(ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH));
				post.setLoginUser(loginUser2);

				childrenPostLsit.add(post);
			}
			returnPost.setChildrenPost(childrenPostLsit);
		}

		return returnPost;
	}

	@Override
	public List<Post> getPostListByZooId(int zoo_id) {
		String sql = "SELECT " +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_POST_ID + "," +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_CONTENTS + "," +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_VISIT_DATE + "," +
				ENTITY_POST.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + "," +
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
				" LEFT OUTER JOIN " + ENTITY_POST_IMAGE.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_POST_ID + " = " +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
				" LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " ON " + ENTITY_POST_IMAGE.TABLE_NAME + "."
				+ ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID + " = " +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID +
				" WHERE " + ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_ZOO_ID + " =  ?  " +
				" AND " + ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_PARENT_ID + " = " + defaultParentId +
				" ORDER BY " + ENTITY_POST.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + " DESC";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, zoo_id);

		List<Post> returnPostList = new ArrayList<Post>();

		int before_post_id = 0;

		for (Map<String, Object> result : resultList) {

			if (before_post_id == (int) result.get(ENTITY_POST.COLUMN_POST_ID)) {
				PostImage postImage = new PostImage();
				postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
				if (result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID) != null) {
					Animal animal = new Animal();
					animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
					animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
					postImage.setAnimal(animal);
				}
				Post post = new Post();
				post.setPost_id((int) result.get(ENTITY_POST.COLUMN_POST_ID));
				postImage.setPost(post);
				returnPostList.get(returnPostList.size() - 1).getPostImageList().add(postImage);

				before_post_id = (int) result.get(ENTITY_POST.COLUMN_POST_ID);

			} else {
				Post post = new Post();
				post.setPost_id((int) result.get(ENTITY_POST.COLUMN_POST_ID));
				post.setContents((String) result.get(ENTITY_POST.COLUMN_CONTENTS));
				post.setVisitDate((Date) result.get(ENTITY_POST.COLUMN_VISIT_DATE));
				post.setCreatedDate((Date) result.get(commonSqlUtil.COLUMN_CREATE_DATE));
				LoginUser loginUser = new LoginUser();
				loginUser.setUser_id((int) result.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
				loginUser.setUserName((String) result.get(ENTITY_LOGIN_USER.COLUMN_USER_NAME));
				loginUser.setProfileImagePath((String) result.get(ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH));
				post.setLoginUser(loginUser);
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

				returnPostList.add(post);

				before_post_id = (int) result.get(ENTITY_POST.COLUMN_POST_ID);
			}
		}

		if (returnPostList.size() > 0) {
			final String placeHolder = returnPostList.stream().map(post -> String.valueOf(post.getPost_id()))
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
						.filter(count -> parentPost.getPost_id() == (int) count.get(ENTITY_POST.COLUMN_POST_ID))
						.forEach(count -> parentPost.setChildrenCount((long) count.get("count")));
			}
		}

		return returnPostList;

	}

	@Override
	public List<Post> getPostListByUserId(int user_id) {
		String sql = "SELECT " +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_POST_ID + "," +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_CONTENTS + "," +
				ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_VISIT_DATE + "," +
				ENTITY_POST.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + "," +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME + "," +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH + "," +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + "," +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_NAME + "," +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + "," +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + "," +
				ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_NAME +
				" FROM " + ENTITY_POST.TABLE_NAME +
				" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_USER_ID + " = " +
				ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +
				" LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_ZOO_ID + " = " +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID +
				" LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME + " ON " + ENTITY_PREFECTURE.TABLE_NAME + "."
				+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " = " +
				ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_PREFECTURE_ID +
				" LEFT OUTER JOIN " + ENTITY_POST_IMAGE.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "."
				+ ENTITY_POST.COLUMN_POST_ID + " = " +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
				" LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " ON " + ENTITY_POST_IMAGE.TABLE_NAME + "."
				+ ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID + " = " +
				ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID +
				" WHERE " + ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_USER_ID + " =  ?  " +
				" AND " + ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_PARENT_ID + " = " + defaultParentId +
				" ORDER BY " + ENTITY_POST.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + " DESC";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, user_id);

		List<Post> returnPostList = new ArrayList<Post>();

		int before_post_id = 0;

		for (Map<String, Object> result : resultList) {

			if (before_post_id == (int) result.get(ENTITY_POST.COLUMN_POST_ID)) {
				PostImage postImage = new PostImage();
				postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
				if (result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID) != null) {
					Animal animal = new Animal();
					animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
					animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
					postImage.setAnimal(animal);
				}
				Post post = new Post();
				post.setPost_id((int) result.get(ENTITY_POST.COLUMN_POST_ID));
				postImage.setPost(post);
				returnPostList.get(returnPostList.size() - 1).getPostImageList().add(postImage);

				before_post_id = (int) result.get(ENTITY_POST.COLUMN_POST_ID);

			} else {
				Post post = new Post();
				post.setPost_id((int) result.get(ENTITY_POST.COLUMN_POST_ID));
				post.setContents((String) result.get(ENTITY_POST.COLUMN_CONTENTS));
				post.setVisitDate((Date) result.get(ENTITY_POST.COLUMN_VISIT_DATE));
				post.setCreatedDate((Date) result.get(commonSqlUtil.COLUMN_CREATE_DATE));
				LoginUser loginUser = new LoginUser();
				loginUser.setUser_id((int) result.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
				loginUser.setUserName((String) result.get(ENTITY_LOGIN_USER.COLUMN_USER_NAME));
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

				returnPostList.add(post);

				before_post_id = (int) result.get(ENTITY_POST.COLUMN_POST_ID);
			}
		}

		if (returnPostList.size() > 0) {
			final String placeHolder = returnPostList.stream().map(post -> String.valueOf(post.getPost_id()))
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
						.filter(count -> parentPost.getPost_id() == (int) count.get(ENTITY_POST.COLUMN_POST_ID))
						.forEach(count -> parentPost.setChildrenCount((long) count.get("count")));
			}
		}

		return returnPostList;
	}
	
	@Override
	public void deletePost(int post_id) {
		String sql = "DELETE FROM " +
				ENTITY_POST.TABLE_NAME  +
				" WHERE " +  ENTITY_POST.COLUMN_POST_ID + " = ? " +
				" or " + ENTITY_POST.COLUMN_PARENT_ID + " = ? ";
		
		jdbcTemplate.update(sql,post_id,post_id);
		
	}

	@Override
	public List<Post> getCommentListByUserId(int user_id) {
		String asOriginalPost = "originalPost";
		String asOriginalPostId = "originalPostId";
		String asCommentFromPost = "commentFromPost";
		String asCommentFromPostId = "commentFromPostId";
		String asOrginalLoginUser = "OrginalLoginUser";
		String asOrginalLoginUserName = "OrginalLoginUserName";
		String asCommentFromLoginUser = "CommentFromLoginUser";
		String asCommentFromLoginUserName = "CommentFromLoginUserName";
		
		String sql ="SELECT " +
				asOriginalPost + "." + ENTITY_POST.COLUMN_POST_ID + " AS " + asOriginalPostId +"," +
				asOriginalPost + "." + ENTITY_POST.COLUMN_CONTENTS + "," +
				asOriginalPost + "." + commonSqlUtil.COLUMN_CREATE_DATE + "," +
				asOrginalLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID + "," +
				asOrginalLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME + " AS " + asOrginalLoginUserName +"," +
				asOrginalLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH + "," +
				asCommentFromPost + "." + ENTITY_POST.COLUMN_POST_ID +  " AS " + asCommentFromPostId +"," +
				asCommentFromLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME + " AS " + asCommentFromLoginUserName +" " +
				" FROM " + ENTITY_POST.TABLE_NAME + " AS " + asOriginalPost + 
				" LEFT OUTER JOIN " + ENTITY_POST.TABLE_NAME + " AS " + asCommentFromPost  + " ON " + asOriginalPost + "."
				+ ENTITY_POST.COLUMN_PARENT_ID + " = " 
				+ asCommentFromPost + "." + ENTITY_POST.COLUMN_POST_ID +
				" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " AS " + asOrginalLoginUser + " ON " + asOriginalPost + "."
				+ ENTITY_POST.COLUMN_USER_ID + " = " +
				asOrginalLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +
				" LEFT OUTER JOIN " + ENTITY_LOGIN_USER.TABLE_NAME + " AS " + asCommentFromLoginUser + " ON " + asCommentFromPost + "."
				+ ENTITY_POST.COLUMN_USER_ID + " = " +
				asCommentFromLoginUser + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +
				" WHERE " + asOriginalPost + "." + ENTITY_POST.COLUMN_USER_ID + " =  ?  " +
				" AND " + asOriginalPost + "." + ENTITY_POST.COLUMN_PARENT_ID + " <> " + defaultParentId   +
				" ORDER BY " + asOriginalPost + "." + commonSqlUtil.COLUMN_CREATE_DATE + " DESC";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,user_id);
		
		List<Post> returnPostList = new ArrayList<Post>();


		for (Map<String, Object> result : resultList) {

				Post post = new Post();
				post.setPost_id((int) result.get(asOriginalPostId));
				post.setContents((String) result.get(ENTITY_POST.COLUMN_CONTENTS));
				post.setCreatedDate((Date) result.get(commonSqlUtil.COLUMN_CREATE_DATE));
				LoginUser loginUser = new LoginUser();
				loginUser.setUser_id((int) result.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
				loginUser.setUserName((String) result.get(asOrginalLoginUserName));
				loginUser.setProfileImagePath((String) result.get(ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH));
				post.setLoginUser(loginUser);
				
				Post commentpost = new Post();
				commentpost.setPost_id((int) result.get(asCommentFromPostId));
				LoginUser commentloginUser = new LoginUser();
				commentloginUser.setUserName((String) result.get(asCommentFromLoginUserName));
				commentpost.setLoginUser(commentloginUser);
				post.setParentPost(commentpost);

				returnPostList.add(post);
		}
		return returnPostList;
	}

}
