package com.example.demo.repository;

import com.example.demo.entity.Prefecture;
import com.example.demo.entity.Zoo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ZooDaoImple implements ZooDao {

private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Zoo ENTITY_ZOO;
	
	@Autowired
	private Prefecture ENTITY_PREFECTURE;
	
	@Autowired
	public ZooDaoImple(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Zoo findById(int zooId) {
		String sql = "SELECT  "+ ENTITY_ZOO.COLUMN_ZOO_ID +" ,"+ ENTITY_ZOO.COLUMN_ZOO_NAME +","+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_NAME 
				+" FROM  "+ ENTITY_ZOO.TABLE_NAME
				+" LEFT OUTER JOIN  "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME  +"."+ ENTITY_ZOO.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+" WHERE "+ ENTITY_ZOO.COLUMN_ZOO_ID +" = ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, zooId);

			Zoo  zoo = new Zoo();
			Prefecture prefecture = new Prefecture();
			zoo.setZoo_id((int)result.get(ENTITY_ZOO.COLUMN_ZOO_ID));
			zoo.setZoo_name((String)result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			prefecture.setName((String)result.get(ENTITY_PREFECTURE.COLUMN_PREFECTURE_NAME));
			zoo.setPrefecture(prefecture);
			return zoo;
	}

	@Override
	public Zoo getFavoriteZoo(int userId){
		String sql = """
				SELECT * FROM zoo
				INNER JOIN
				(SELECT favorite_zoo FROM login_user WHERE user_id = ?) AS login_user
				ON zoo.zoo_id = login_user.favorite_zoo		
				""";
		Map<String, Object> rs = jdbcTemplate.queryForMap(sql, userId);
		Zoo favoriteZoo = new Zoo();
		favoriteZoo.setZoo_id((int) rs.get("zoo_id"));
		favoriteZoo.setZoo_name((String) rs.get("zoo_name"));
		return favoriteZoo;
	}
	
	@Override
	public List<Zoo> getZooList() {
		String sql = "SELECT "+ ENTITY_ZOO.COLUMN_ZOO_ID +", " + ENTITY_ZOO.COLUMN_ZOO_NAME
				+ " FROM "+ ENTITY_ZOO.TABLE_NAME +" ORDER BY "+ ENTITY_ZOO.COLUMN_ZOO_ID +" ASC";
		List<Map<String, Object>> resultZooList = jdbcTemplate.queryForList(sql);
		List<Zoo> zooList = new ArrayList<>();
		for (Map<String, Object> result : resultZooList) {
			Zoo zoo = new Zoo();
			zoo.setZoo_id((int) result.get(ENTITY_ZOO.COLUMN_ZOO_ID));
			zoo.setZoo_name((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			zooList.add(zoo);
		}
		return zooList;
	}

}
