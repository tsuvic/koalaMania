package com.example.koalamania.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.koalamania.entity.AnimalEntity;
import com.example.koalamania.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @GetMapping("/animals")
    public String searchAnimals(@RequestParam(required = false, name = "keyword") String keyword)  throws Exception {
        List<AnimalEntity> result = animalService.searchAnimals(keyword);
        ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(result);
        return json;
    }
}