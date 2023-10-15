package com.example.koalamania.repository;

import java.util.List;

import com.example.koalamania.entity.Animal;

public interface AnimalDao {
    List<Animal> searchAnimals(String keyword);
}
