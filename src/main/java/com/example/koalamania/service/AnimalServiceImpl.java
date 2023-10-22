package com.example.koalamania.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.koalamania.entity.AnimalEntity;
import com.example.koalamania.model.AnimalModel;
import com.example.koalamania.repository.AnimalRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService{
    
    AnimalRepository animalRepository;

    @Override
	public List<AnimalEntity> searchAnimals(String keyword) {
        List<AnimalModel> animalModelList = animalRepository.findByName(keyword);
		List<AnimalEntity> animaEntityList = new ArrayList<AnimalEntity>();

		for(AnimalModel model:animalModelList){
			AnimalEntity entity = new AnimalEntity();
			entity.setAnimal_id(model.getAnimal_id());
			entity.setName(model.getName());
			animaEntityList.add(entity);
		}
		return animaEntityList;
	}
    
}
