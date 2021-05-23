package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.KoalaImage;
import com.example.demo.entity.KoalaProfileImage;

@Repository
public class KoalaProfielImageDaoImpl implements KoalaProfileImageDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public KoalaProfielImageDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insert(KoalaProfileImage koalaProfileImage) {
		/*
		jdbcTemplate.update("INSERT INTO koalaImage(koala_id,filetype) VALUES(?,?)",
			koalaImage.getKoala_id(),koalaImage.getFiletype());
		int insertKoalaImage_id = jdbcTemplate.queryForObject("SELECT LASTVAL()", Integer.class);
		*/
		Map<String, Object> insertId = jdbcTemplate.queryForMap("INSERT INTO koalaProfileImage (koala_id,filetype) VALUES(?,?) RETURNING koalaProfileimage_id",
				koalaProfileImage.getKoala_id(),koalaProfileImage.getFiletype());
		return (int)insertId.get("koalaProfileimage_id");
	}
	
	@Override
	public void delete(KoalaProfileImage koalaProfileImage) {
		String sql = "DELETE FROM koalaimage WHERE koalaimage_id = ";
//		for (int i = 0; i < koalaImage_idList.size(); i++) {
//			if (i != 0) {
//				sql += " OR koalaimage_id = ";
//			}
//			sql += koalaImage_idList.get(i).toString();
//		}
		jdbcTemplate.execute(sql);
	}
	
	@Override
	public KoalaProfileImage findByKoala_id(int id){
		String sql = "SELECT  koalaimage_id ,koala_id,filetype FROM  koalaimage WHERE koala_id = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);
		
		List<KoalaImage> koalaImageList = new ArrayList<KoalaImage>();
		for(Map<String, Object> result : resultList) {
			KoalaImage koalaImage = new KoalaImage();
			koalaImage.setKoalaimage_id((int)result.get("koalaimage_id"));
			koalaImage.setKoala_id((int)resultList.get(0).get("koala_id"));
			koalaImage.setFiletype((String)result.get("filetype"));
			koalaImageList.add(koalaImage);
			koalaImage = null;
		}
		return null;
	}

}
