package com.example.demo.app.api;

import com.example.demo.repository.AnimalDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AnimalRestController {
    static ObjectMapper objectMapper = new ObjectMapper();
    private final AnimalDao animalDao;

    @GetMapping("/animals")
    public String getAnimals(
            @RequestParam(required = false, name = "keyword") Optional<String> keyword,
            @RequestParam(required = false, name = "zoo") Optional<List<Integer>> ZooId,
            @RequestParam(required = false, name = "animal") Optional<List<Integer>> AnimalId

    ) throws JsonProcessingException {

        List searchResult = new ArrayList();

        //TODO animalServiceからも参照するanimalDaoにsplit wordsの責務を持たせる形となっているため、リファクタリングが必要
        if(keyword.isPresent()){
            searchResult = animalDao.findByKeyword(keyword.get());
        }

        if (keyword.isEmpty() && ZooId.isEmpty() && AnimalId.isEmpty()) {
            searchResult = animalDao.getAll();
        }
        return objectMapper.writeValueAsString(searchResult);

    }
}
