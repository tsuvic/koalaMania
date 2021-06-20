package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.KoalaImage;
import com.example.demo.entity.LoginUser;

@Repository
public class KoalaImageDaoImpl implements KoalaImageDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private KoalaImage ENTITY_KOALA_IMAGE;
	
	@Autowired
	private CommonSqlUtil commonSqlUtil;
	
	@Autowired
	public KoalaImageDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insert(KoalaImage koalaImage) {
		/*
		jdbcTemplate.update("INSERT INTO koalaImage("+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_ID +",filetype) VALUES(?,?)",
			koalaImage.getKoala_id(),koalaImage.getFiletype());
		int insertKoalaImage_id = jdbcTemplate.queryForObject("SELECT LASTVAL()", Integer.class);
		*/
		Map<String, Object> insertId = jdbcTemplate.queryForMap("INSERT INTO "+ ENTITY_KOALA_IMAGE.TABLE_NAME +"("+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_ID +","+ ENTITY_KOALA_IMAGE.COLUMN_FILETYPE +") VALUES(?,?) RETURNING "+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID +"",
				koalaImage.getKoala_id(),koalaImage.getFiletype());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commonSqlUtil.updateAllCommonColumn(ENTITY_KOALA_IMAGE.TABLE_NAME,ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID,(int) ((LoginUser) principal).getUser_id() ,(int)insertId.get(ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID));
		return (int)insertId.get(ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID);
	}
	
	@Override
	public void delete(List<String> koalaImage_idList) {
		String sql = "DELETE FROM "+ ENTITY_KOALA_IMAGE.TABLE_NAME +" WHERE "+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID +" = ";
		for (int i = 0; i < koalaImage_idList.size(); i++) {
			if (i != 0) {
				sql += " OR "+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID +" = ";
			}
			sql += koalaImage_idList.get(i).toString();
		}
		jdbcTemplate.execute(sql);
	}
	
	@Override
	public List<KoalaImage> findByKoala_id(int id){
		String sql = "SELECT  "+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID +" ,"+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_ID +","+ ENTITY_KOALA_IMAGE.COLUMN_FILETYPE +" FROM  "+ ENTITY_KOALA_IMAGE.TABLE_NAME +" WHERE "+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_ID +" = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);
		
		List<KoalaImage> koalaImageList = new ArrayList<KoalaImage>();
		for(Map<String, Object> result : resultList) {
			KoalaImage koalaImage = new KoalaImage();
			koalaImage.setKoalaimage_id((int)result.get(ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID));
			koalaImage.setKoala_id((int)resultList.get(0).get(ENTITY_KOALA_IMAGE.COLUMN_KOALA_ID));
			koalaImage.setFiletype((String)result.get(ENTITY_KOALA_IMAGE.COLUMN_FILETYPE));
			koalaImageList.add(koalaImage);
			koalaImage = null;
		}
		return koalaImageList;
	}

}
