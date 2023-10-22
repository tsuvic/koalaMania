package com.example.koalamania.repository;

import com.example.koalamania.model.AnimalModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimalRepository extends JpaRepository<AnimalModel, Integer>{
    List<AnimalModel> findByName(String name);
}
