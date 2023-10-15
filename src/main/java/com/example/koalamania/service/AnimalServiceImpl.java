package com.example.koalamania.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.koalamania.entity.Animal;
import com.example.koalamania.repository.AnimalDao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService{
    
    AnimalDao animalDao;

    @Override
	public List<Animal> searchAnimals(String keyword) {
		return animalDao.searchAnimals(keyword);
	}
    
}
