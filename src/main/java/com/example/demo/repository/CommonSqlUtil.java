package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CommonSqlUtil {
	
	public final String COLUMN_CREATE_BY = "created_by";
	public final String COLUMN_UPDATE_BY = "updated_by";
	public final String COLUMN_CREATE_DATE = "created_date";
	public final String COLUMN_UPDATE_DATE = "updated_date";
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CommonSqlUtil(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void updateAllCommonColumn(String tableName,String primary_column,int userId ,int primaryId) {
		
		jdbcTemplate.update(
				"UPDATE " + tableName +  " SET "+ COLUMN_CREATE_BY + "= ? , "+ COLUMN_UPDATE_BY + " = ? , " + COLUMN_CREATE_DATE + " = now() ," + COLUMN_UPDATE_DATE + "=now() WHERE " + primary_column + " = ?",
				userId, userId ,primaryId);
	}
	
	public void updateOnlyUpdateCommonColumn(String tableName,String primary_column,int userId ,int primaryId) {
		
		jdbcTemplate.update(
				"UPDATE " + tableName +  " SET "+ COLUMN_UPDATE_BY + " = ? , " + COLUMN_UPDATE_DATE + "=now() WHERE " + primary_column + " = ?",
				 userId ,primaryId);
	}

}
