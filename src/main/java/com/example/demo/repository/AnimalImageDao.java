package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.AnimalImage;


public interface AnimalImageDao {
	
int insert(AnimalImage koalaImage);
	
	void delete(List<String> koalaimage_idList);
	
	List<AnimalImage> findByAnimal_id(int id);
}
