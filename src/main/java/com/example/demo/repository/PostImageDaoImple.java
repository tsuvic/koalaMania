package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LoginUser;
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
	
	public void insertNewPostImage(int post_id,List<Integer> animalIdList,
			List<String> imageAddressList) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = ((LoginUser) principal).getUser_id();
		
		for(int i=0; i < imageAddressList.size() ; ++i) {
			String sql = "INSERT INTO "+ ENTITY_POST_IMAGE.TABLE_NAME +"("+ ENTITY_POST_IMAGE.COLUMN_POST_ID +", "
					+ ENTITY_POST_IMAGE.COLUMN_ANIMAL_ID +", " + ENTITY_POST_IMAGE.COLUMN_IMAGE_ADDRESS  + ")" 
					+ "VALUES(?, ?, ?)RETURNING "+ ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID +"";
			Map<String, Object> result = jdbcTemplate.queryForMap(sql, post_id,animalIdList.get(i),imageAddressList.get(i));
			commonSqlUtil.updateAllCommonColumn( ENTITY_POST_IMAGE.TABLE_NAME, ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID, user_id, (int) result.get(ENTITY_POST_IMAGE.COLUMN_POSTIMAGE_ID));
		}
		
	}

}
