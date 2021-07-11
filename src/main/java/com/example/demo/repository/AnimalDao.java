package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalForTree;
import com.example.demo.entity.Zoo;

public interface AnimalDao {
	List<Animal> getAll();

	List<Animal> getMotherList(int animal_id, Date birthDay);

	List<Animal> getFatherList(int animal_id, Date birthDay);

	List<Animal> findByKeyword(String keyword);

	List<Zoo> getZooList();

	int insert(Animal animal);

	Animal findById(int id);

	void update(Animal animal);

	void delete(int animal_id);
	
	void urlUpdate(int animal_id, String url);
	
	AnimalForTree getAnimalForTree(int id);

	List<AnimalForTree> getBrotherAnimalForTree(int animal_id, int mother_id, int father_id);
	
	List<AnimalForTree> getChildrenAnimalForTree(int animal_id, int sex);
	
	void insertZooHistory(int animal_id, List<Integer> zooList, List<Date> admissionDateList, List<Date> exitDateList);
	
}
