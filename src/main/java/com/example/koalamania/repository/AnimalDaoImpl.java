package com.example.koalamania.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.koalamania.entity.Animal;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class AnimalDaoImpl implements AnimalDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Animal> searchAnimals(String keyword){
        String sql = "SELECT * FROM animal WHERE name LIKE ?;";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, "%" + keyword + "%");
        List<Animal> animalList = new ArrayList<Animal>();
        for (Map<String, Object> result : resultList) {
            Animal animal = new Animal();
            animal.setAnimal_id((int) result.get("animal_id"));
            animal.setName((String) result.get("name"));
            animal.setSex((int) result.get("sex"));
            animal.setBirthdate((Date) result.get("birthdate"));
            animalList.add(animal);
        }
        return animalList;
    }
}
