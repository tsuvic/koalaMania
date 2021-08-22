package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.AnimalInsertForm;
import com.example.demo.entity.Animal;
import com.example.demo.entity.Zoo;

public interface AnimalService {

	List<Animal> getAll();

	List<Animal> getMotherList(int animal_id, String birthYear, String birthMonth, String birthDay);

	List<Animal> getFatherList(int animal_id, String birthYear, String birthMonth, String birthDay);

	List<Animal> findByKeyword(String keyword);

	List<Zoo> getZooList();

	void insert(AnimalInsertForm form);

	Animal findById(int id);

	void update(AnimalInsertForm form);

	void delete(int animal_id);
	
	void insertAnimalProfileImage(int animal_id, MultipartFile animalProfileImage, String profileImagePath);
	
	Map<String, Object> getAnimalForTree(int i);
	
	Date getDate(String year, String month, String day);
		
}