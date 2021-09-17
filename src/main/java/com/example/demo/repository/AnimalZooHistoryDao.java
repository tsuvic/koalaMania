package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalZooHistory;

public interface AnimalZooHistoryDao {
	
	void deleteAllAnimalZooHistory(int id);
	
	Animal getAnimalZooHistory(int id, Animal animal);
	
	void insertZooHistory(List<AnimalZooHistory> animalZooHistoryList);
}
