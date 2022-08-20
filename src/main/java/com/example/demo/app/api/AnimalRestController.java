package com.example.demo.app.api;

import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.AnimalDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AnimalRestController {
    static ObjectMapper objectMapper = new ObjectMapper();
    private final AnimalDao animalDao;
    @GetMapping("/animals")
    @CrossOrigin(origins = {"http://localhost:3000","https://loquacious-pasca-67dd24.netlify.app"})
    public String searchAnimals(
            //* @ModelAttribute AnimalSearchForm animalSearchForm RESTであるため、Formは不要 **/
            //* IDもリクエストパラメータとしてinputするため、Stringとなる *//
            //* 分岐のロジック・動的SQLの生成はDAOの責務とする　searchメソッドに渡すためにデフォルト値を設定する *//
            @RequestParam(required = false, defaultValue = "", name = "keyword") Optional<String> keyword,
            @RequestParam(required = false, defaultValue = "", name = "zoo") Optional<String> ZooId,
            @RequestParam(required = false, defaultValue = "", name = "animal") Optional<String> AnimalId,
            @RequestParam(required = false, defaultValue = "false", name = "isMale") boolean isMale,
            @RequestParam(required = false, defaultValue = "false", name = "isFemale") boolean isFemale,
            @RequestParam(required = false, defaultValue = "false", name = "isAlive") boolean isAlive,
            @RequestParam(required = false, defaultValue = "false", name = "isDead") boolean isDead,
            @RequestParam(required = false, defaultValue = "1", name = "page") int page
    ) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                animalDao.searchAnimals(keyword, ZooId, AnimalId, isMale, isFemale,isAlive,isDead,page)
        );
    }
}
