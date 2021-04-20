package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Coara;

@Repository
public class CoaraDaoImpl implements CoaraDao{
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CoaraDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Coara> getAll() {
		String sql = "SELECT id, name, email, contents, created FROM coara";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);//実行結果をリストへ		
		List<Coara> list = new ArrayList<Coara>(); //viewに返却用のリスト
		for(Map<String, Object> result : resultList) {//resultListからMAP型resultを繰り返し出力
			Coara coara = new Coara();//出力したMAP型resultからインスタンスに値を詰め込み
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
