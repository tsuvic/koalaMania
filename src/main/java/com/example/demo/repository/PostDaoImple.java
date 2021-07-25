package com.example.demo.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;

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
	
	public int isertNewPost(Post post) {
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

}
