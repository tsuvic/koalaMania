package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
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
		String sql = "SELECT coara_id, name, is_male, birthdate, is_alive, deathdate, zoo, mother, father FROM coara";
		//SQL実行結果をMap型リストへ代入		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		 //view返却用のリストを生成
		List<Coara> list = new ArrayList<Coara>();
		//MAP型リストからMapを繰り返し出力し、MapのバリューObjectをCoaraインスタンスに詰め込む
		for(Map<String, Object> result : resultList) {
			Coara coara = new Coara();
			coara.setCoara_id((int)result.get("coara_id"));
			coara.setName((String)result.get("name"));
			coara.setIs_male((int)result.get("is_male"));
			coara.setBirthdate((Date)result.get("birthdate"));
			coara.setIs_alive((int)result.get("is_alive"));
			coara.setDeathdate((Date)result.get("deathdate"));
			coara.setZoo((String)result.get("zoo"));
			coara.setMother((String)result.get("mother"));
			coara.setFather((String)result.get("father"));
			// Coaraインスタンスをview返却用のリストに詰め込んでいく
			list.add(coara);
		}
		return list;
	}
	
	@Override
	public void insert(Coara coara){
		jdbcTemplate.update("INSERT INTO coara(name, is_male, birthdate, is_alive, deathdate, zoo, mother, father, details, feature) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				coara.getName(),coara.getIs_male(),coara.getBirthdate(),coara.getIs_alive(),coara.getDeathdate(),coara.getZoo(),coara.getMother(),coara.getFather(),coara.getDetails(),coara.getFeature());
	}
	
}
