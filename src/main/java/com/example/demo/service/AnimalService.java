package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.AnimalInsertForm;
import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalForTree;
import com.example.demo.entity.AnimalImage;
import com.example.demo.entity.RelationForTree;
import com.example.demo.entity.Zoo;

public interface AnimalService {

	List<Animal> getAll();

	List<Animal> getMotherList(int animal_id, String birthYear, String birthMonth, String birthDay);

	List<Animal> getFatherList(int animal_id, String birthYear, String birthMonth, String birthDay);

	List<Animal> findByKeyword(String keyword);

	List<Zoo> getZooList();

	void insert(AnimalInsertForm form);

	Animal findById(int i);

	void update(AnimalInsertForm form);

	void delete(int animal_id);

	List<AnimalImage> findAnimalImageById(int id);
	
	void insertAnimalProfileImage(int animal_id, MultipartFile animalProfileImage, String profileImagePath);

	void insertAnimalImage(int animal_id, List<MultipartFile> animalImage);

	void deleteAnimalImage(String AnimalImageIds, int animal_id);
	
	Map<String, Object> getAnimalForTree(int i);
		
}