package com.example.koalamania.service;

import java.util.List;

import com.example.koalamania.entity.AnimalEntity;

public interface AnimalService  {
    List<AnimalEntity> searchAnimals(String keyword);
}