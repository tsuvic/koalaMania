package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalZooHistory;
import com.example.demo.entity.LoginUser;


@Repository
public class AnimalZooHistoryDaoImpl implements AnimalZooHistoryDao {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AnimalZooHistory ENTITY_ANIMAL_ZOO_HISTORY;
	
	@Autowired
	private CommonSqlUtil commonSqlUtil;

	@Autowired
	public AnimalZooHistoryDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void deleteAllAnimalZooHistory(int id) {
		String sql = "DELETE" + " " + "FROM" + " " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
				+ "WHERE" + " " + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + " " + "=" + "?" ;
		jdbcTemplate.update(sql,id);
	}

	
	@Override
	public Animal addAnimalZooHistory(int id, Animal animal) {
		
		String sql = "SELECT"+ " " + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ZOO_HISTORY_ID + ", "
		+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ", "
		+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID + ", "
		+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ADMISSION_DATE + ", "
		+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + " "
		+ "FROM" +" "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
		+ "WHERE" + " " + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + " " + "=" + "?" ;
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);
		List<AnimalZooHistory> animalZooHistoryList = new ArrayList<AnimalZooHistory>();
		for (Map<String, Object> result : resultList) {
			AnimalZooHistory animalZooHistory = new AnimalZooHistory();
			animalZooHistory.setAnimal_zoo_history_id((int)result.get(ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ZOO_HISTORY_ID));
			animalZooHistory.setAnimal_id((int)result.get(ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID));
			animalZooHistory.setZoo_id((int)result.get(ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID));
			animalZooHistory.setAdmission_date((Date)result.get(ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ADMISSION_DATE));
			animalZooHistory.setExit_date((Date)result.get(ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE));
			animalZooHistoryList.add(animalZooHistory);
		}
		animal.setAnimalZooHistory(animalZooHistoryList);
		
		return animal;
		
	}
	
	@Override
	public void insertZooHistory(int animal_id, List<Integer> zooList, List<Date> admissionDateList, List<Date> exitDateList) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for (int i = 0; i < zooList.size(); i++) {
			Map<String, Object> insertedAnimalZooHistory = jdbcTemplate.queryForMap(
					"INSERT INTO " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "(" 
					+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ", "
					+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID + ", "
					+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ADMISSION_DATE + ", "
					+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + ") VALUES(?, ?, ?, ?) RETURNING " 
					+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ZOO_HISTORY_ID, 
					animal_id, zooList.get(i), admissionDateList.get(i), exitDateList.get(i));
			commonSqlUtil.updateAllCommonColumn(ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME, ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ZOO_HISTORY_ID , (int) ((LoginUser) principal).getUser_id(),(int) insertedAnimalZooHistory.get(ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ZOO_HISTORY_ID));

		}
	}
}
