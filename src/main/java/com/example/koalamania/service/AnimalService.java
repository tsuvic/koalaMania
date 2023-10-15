package com.example.koalamania.service;

import java.util.List;

import com.example.koalamania.entity.Animal;

public interface AnimalService  {
    List<Animal> searchAnimals(String keyword);
}