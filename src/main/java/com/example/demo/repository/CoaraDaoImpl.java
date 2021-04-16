package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.coara;

@Repository
public class CoaraDaoImpl implements CoaraDao{
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CoaraDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertcoara(coara coara) {
		jdbcTemplate.update("INSERT INTO coara(name, email, contents, created) VALUES(?,?,?,?)",
				coara.getName(), coara.getEmail(), coara.getContents(), coara.getCreated());
	}
	
//  This method is used in the latter chapter
//	@Override
//	public int updatecoara(coara coara) {
//		return jdbcTemplate.update("UPDATE coara SET name = ?, email = ?,contents = ? WHERE id = ?",
//				 coara.getName(), coara.getEmail(), coara.getContents(), coara.getId() );	
//	}

	@Override
	public List<coara> getAll() {
		String sql = "SELECT id, name, email, contents, created FROM coara";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);//実行結果をリストへ		
		List<coara> list = new ArrayList<coara>(); //viewに返却用のリスト
		for(Map<String, Object> result : resultList) {//resultListからMAP型resultを繰り返し出力
			coara coara = new coara();//出力したMAP型resultからインスタンスに値を詰め込み
			coara.setId((int)result.get("id"));
			coara.setName((String)result.get("name"));
			coara.setEmail((String)result.get("email"));
			coara.setContents((String)result.get("contents"));
			coara.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			list.add(coara);//viewに返却用のリスト
		}
		return list;
	}
	
}
