package com.example.demo.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.ZooDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ZooRestController {
    static ObjectMapper objectMapper = new ObjectMapper();
    private final ZooDao zooDao;

    @Autowired
    public ZooRestController(ZooDao zooDao) {
        this.zooDao = zooDao;
    }

    @GetMapping("/zoo")
    @CrossOrigin(origins = {"http://localhost:3000","https://loquacious-pasca-67dd24.netlify.app"})
    public String getAllZoo() throws JsonProcessingException {
        var allZooList = zooDao.getZooList();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allZooList);
    }
}
