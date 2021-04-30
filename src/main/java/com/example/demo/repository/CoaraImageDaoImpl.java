package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CoaraImage;

@Repository
public class CoaraImageDaoImpl implements CoaraImageDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CoaraImageDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insert(CoaraImage coaraImage) {
		/*
		jdbcTemplate.update("INSERT INTO coaraImage(coara_id,filetype) VALUES(?,?)",
			coaraImage.getCoara_id(),coaraImage.getFiletype());
		int insertCoaraImage_id = jdbcTemplate.queryForObject("SELECT LASTVAL()", Integer.class);
		*/
		Map<String, Object> insertId = jdbcTemplate.queryForMap("INSERT INTO coaraImage(coara_id,filetype) VALUES(?,?) RETURNING coaraimage_id",
				coaraImage.getCoara_id(),coaraImage.getFiletype());
		return (int)insertId.get("coaraimage_id");
	}
	
	@Override
	public void delete(List<Integer> coaraImage_idList) {
		String sql = "DELETE FROM coaraimage WHERE coaraimage_id = ";
		for (int i = 0; i < coaraImage_idList.size(); i++) {
			if (i != 0) {
				sql += " OR coaraimage_id = ";
			}
			sql += coaraImage_idList.get(i).toString();
		}
		jdbcTemplate.execute(sql);
	}

}
