package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import com.example.demo.entity.Animal;

public interface AnimalZooHistoryDao {
	
	void deleteAllAnimalZooHistory(int id);
	
	Animal addAnimalZooHistory(int id, Animal animal);
	
	void insertZooHistory(int animal_id, List<Integer> zooList, List<Date> admissionDateList, List<Date> exitDateList);
}
