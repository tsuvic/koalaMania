package com.example.demo.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.app.AnimalFilterForm;
import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalForTree;

public interface AnimalDao {
	List<Animal> getAll();

	List<Animal> getMotherList(int animal_id, Date birthDay);

	List<Animal> getFatherList(int animal_id, Date birthDay);

	List<Animal> findByKeyword(String keyword);

	int insert(Animal animal);

	Animal findById(int id);
	
	List<Animal> getAnimalListByZooId(int zoo_id);

	void update(Animal animal);

	void delete(int animal_id);
	
	void urlUpdate(int animal_id, String url);
	
	AnimalForTree getAnimalForTree(int id);

	List<AnimalForTree> getBrotherAnimalForTree(int animal_id, int mother_id, int father_id);
	
	List<AnimalForTree> getChildrenAnimalForTree(int animal_id, int sex);

    List<Animal> filter(AnimalFilterForm animalSearchForm);

    Map<String, Object> searchAnimals(Optional<String> keyword, Optional<String> ZooId, Optional<String> AnimalId,boolean isMale,boolean isFemale, boolean isAlive, boolean isDead,int page);
}
