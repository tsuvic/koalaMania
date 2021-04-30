package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.KoalaImage;

@Repository
public class KoalaImageDaoImpl implements KoalaImageDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public KoalaImageDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insert(KoalaImage koalaImage) {
		/*
		jdbcTemplate.update("INSERT INTO koalaImage(koala_id,filetype) VALUES(?,?)",
			koalaImage.getKoala_id(),koalaImage.getFiletype());
		int insertKoalaImage_id = jdbcTemplate.queryForObject("SELECT LASTVAL()", Integer.class);
		*/
		Map<String, Object> insertId = jdbcTemplate.queryForMap("INSERT INTO koalaImage(koala_id,filetype) VALUES(?,?) RETURNING koalaimage_id",
				koalaImage.getKoala_id(),koalaImage.getFiletype());
		return (int)insertId.get("koalaimage_id");
	}
	
	@Override
	public void delete(List<Integer> koalaImage_idList) {
		String sql = "DELETE FROM koalaimage WHERE koalaimage_id = ";
		for (int i = 0; i < koalaImage_idList.size(); i++) {
			if (i != 0) {
				sql += " OR koalaimage_id = ";
			}
			sql += koalaImage_idList.get(i).toString();
		}
		jdbcTemplate.execute(sql);
	}

}
