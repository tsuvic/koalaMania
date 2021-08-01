package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.entity.PostImage;

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
	
	@Override
	public int insertNewPostImage(int post_id,int animalId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = ((LoginUser) principal).getUser_id();
		
			String sql = "INSERT INTO "+ ENTITY_POST_IMAGE.TABLE_NAME +"("+ ENTITY_POST_IMAGE.COLUMN_POST_ID +", "
					+ ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID +", " + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS  + ")" 
					+ "VALUES(?, ?, ?)RETURNING "+ ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID +"";
			Map<String, Object> result = jdbcTemplate.queryForMap(sql, post_id,animalId,null);
			commonSqlUtil.updateAllCommonColumn( ENTITY_POST_IMAGE.TABLE_NAME, ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID, user_id, 
					(int) result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID));
		
		return (int) result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID);
	}
	
	@Override
	public void updateUrl(int postImageId,String url) {
		jdbcTemplate.update("UPDATE "+ ENTITY_POST_IMAGE.TABLE_NAME +" SET "+ ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS +" = ? WHERE "+ 
				ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID +" = ?", url, postImageId);
	}
	
	@Override
	public List<PostImage> getPostImageListByAnimalId(int animal_id) {
		String sql = "SELECT " + 
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID +"," + 
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS +"," +
				ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_POST_ID +
				" FROM " + ENTITY_POST_IMAGE.TABLE_NAME +
				" WHERE "  + ENTITY_POST_IMAGE.TABLE_NAME + "." + ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID + " =  ?  " +
				" ORDER BY " + ENTITY_POST_IMAGE.TABLE_NAME + "." + commonSqlUtil.COLUMN_CREATE_DATE + " DESC";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,animal_id);
		
		List<PostImage> returnPostImageList = new ArrayList<PostImage>();
		
		if(resultList.size() > 0) {
			for(Map<String, Object> result:resultList) {
				PostImage postImage = new PostImage();
				postImage.setPostimage_id((int) result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID));
				postImage.setImageAddress((String) result.get(ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS));
				Post post = new Post();
				post.setPost_id((int) result.get(ENTITY_POST_IMAGE.COLUMN_POST_ID));
				postImage.setPost(post);	
				returnPostImageList.add(postImage);
			}
		}
		return returnPostImageList;
	}
}
