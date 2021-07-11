package com.example.demo.repository;

import com.example.demo.entity.Animal;

public interface AnimalZooHistoryDao {
	
	void deleteAllAnimalZooHistory(int id);
	
	Animal addAnimalZooHistory(int id, Animal animal);
	
}
