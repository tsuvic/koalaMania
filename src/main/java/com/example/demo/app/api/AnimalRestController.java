package com.example.demo.app.api;

import com.example.demo.repository.AnimalDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AnimalRestController {
    static ObjectMapper objectMapper = new ObjectMapper();
    private final AnimalDao animalDao;

    @GetMapping("/animals")
    public String searchAnimals(
            //* @ModelAttribute AnimalSearchForm animalSearchForm RESTであるため、Formは不要 **/
            //* IDもリクエストパラメータとしてinputするため、Stringとなる *//
            //* 分岐のロジック・動的SQLの生成はDAOの責務とする　searchメソッドに渡すためにデフォルト値を設定する *//
            @RequestParam(required = false, defaultValue = "", name = "keyword") Optional<String> keyword,
            @RequestParam(required = false, defaultValue = "", name = "zoo") Optional<String> ZooId,
            @RequestParam(required = false, defaultValue = "", name = "animal") Optional<String> AnimalId
    ) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                animalDao.searchAnimals(keyword, ZooId, AnimalId)
        );
    }
}
