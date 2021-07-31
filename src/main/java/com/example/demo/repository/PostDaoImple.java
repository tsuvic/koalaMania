package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Animal;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.entity.PostImage;

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
	
	@Override
	public int insertNewPost(Post post) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = ((LoginUser) principal).getUser_id();
		String sql = 
				"INSERT INTO "+ ENTITY_POST.TABLE_NAME +"("+ ENTITY_POST.COLUMN_USER_ID +", "
				+ ENTITY_POST.COLUMN_ZOO_ID +", " + ENTITY_POST.COLUMN_PARENT_ID +", " 
				+ ENTITY_POST.COLUMN_CONTENTS +", "+ ENTITY_POST.COLUMN_VISIT_DATE + ")" 
				+ "VALUES(?, ?, ?, ?, ?) RETURNING "+ ENTITY_POST.COLUMN_POST_ID +"";
		
		Map<String, Object> result = jdbcTemplate.queryForMap(sql,user_id,post.getZoo().getZoo_id(),
				post.getParent().getPost_id(),post.getContents(),post.getVisitDate());
		
		commonSqlUtil.updateAllCommonColumn(ENTITY_POST.TABLE_NAME , ENTITY_POST.COLUMN_POST_ID, user_id, (int) result.get(ENTITY_POST.COLUMN_POST_ID));
		
		return (int) result.get(ENTITY_POST.COLUMN_POST_ID);	
	}
	
	@Override
	public List<Post> getPostListByZooId(int zoo_id){
		String sql = "SELECT " + 
						ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_POST_ID +"," + 
						ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_CONTENTS +"," + 
						ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_VISIT_DATE  +"," +
						ENTITY_POST.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE   +"," +
						ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS  +"," +
						ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +"," +
						ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_NAME +"," +
						ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH +"," +
						ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID   +"," +   
						ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_NAME +
						" FROM " + ENTITY_POST.TABLE_NAME +
						" LEFT OUTER JOIN "  + ENTITY_LOGIN_USER.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_USER_ID + " = " + 
						ENTITY_LOGIN_USER.TABLE_NAME + "." + ENTITY_LOGIN_USER.COLUMN_USER_ID +
						" LEFT OUTER JOIN "  + ENTITY_POST_IMAGE.TABLE_NAME + " ON " + ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_POST_ID + " = " +
						ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
						" LEFT OUTER JOIN "  + ENTITY_ANIMAL.TABLE_NAME + " ON " + ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID + " = " +
						ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID +
						" where "  + ENTITY_POST.TABLE_NAME + "." + ENTITY_POST.COLUMN_ZOO_ID + " =  ?  " + 
						"ORDER BY " + ENTITY_POST.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + " DESC";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,zoo_id);
		
		List<Post> returnPostList = new ArrayList<Post>();
		
		int before_post_id = 0;
		
		for(Map<String, Object> result : resultList) {
			
			if(before_post_id == (int) result.get(ENTITY_POST.COLUMN_POST_ID)) {
				PostImage postImage = new PostImage();
				postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
				if(result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID) != null) {
					Animal animal = new Animal();
					animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
					animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
					postImage.setAnimal(animal);
				}
				returnPostList.get(returnPostList.size() - 1).getPostImageList().add(postImage);
				
				before_post_id = (int) result.get(ENTITY_POST.COLUMN_POST_ID);
				
			}else {
				Post post  = new Post();
				post.setPost_id((int) result.get(ENTITY_POST.COLUMN_POST_ID));
				post.setContents((String) result.get(ENTITY_POST.COLUMN_CONTENTS));
				post.setVisitDate((Date) result.get(ENTITY_POST.COLUMN_VISIT_DATE));
				post.setCreatedDate((Date) result.get(commonSqlUtil.COLUMN_CREATE_DATE));
				LoginUser loginUser = new LoginUser();
				loginUser.setUser_id((int) result.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
				loginUser.setUserName((String) result.get(ENTITY_LOGIN_USER.COLUMN_USER_NAME));
				loginUser.setProfileImagePath((String) result.get(ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH));
				post.setLoginUser(loginUser);
				if(result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS) != null) {
					PostImage postImage = new PostImage();
					postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
					if(result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID) != null) {
						Animal animal = new Animal();
						animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
						animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
						postImage.setAnimal(animal);
					}
					post.setPostImageList(new ArrayList<PostImage>());
					post.getPostImageList().add(postImage);
				}
	
				returnPostList.add(post);
				
				before_post_id = (int) result.get(ENTITY_POST.COLUMN_POST_ID);
			}
		}
		
		return  returnPostList;
		
	}

}
