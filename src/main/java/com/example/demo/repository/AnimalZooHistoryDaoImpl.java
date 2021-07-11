package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalZooHistory;

@Repository
public class AnimalZooHistoryDaoImpl implements AnimalZooHistoryDao {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AnimalZooHistory ENTITY_ANIMAL_ZOO_HISTORY;

	@Autowired
	public AnimalZooHistoryDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Animal addAnimalZooHistory(int id, Animal animal) {
		
		String sql2 = "SELECT"+ " " + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ZOO_HISTORY_ID + ", "
		+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ", "
		+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID + ", "
		+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ADMISSION_DATE + ", "
		+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + " "
		+ "FROM" +" "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
		+ "WHERE" + " " + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + " " + "=" + "?" ;
		
		List<Map<String, Object>> resultList2 = jdbcTemplate.queryForList(sql2, id);
		List<AnimalZooHistory> animalZooHistoryList = new ArrayList<AnimalZooHistory>();
		for (Map<String, Object> result : resultList2) {
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
}
