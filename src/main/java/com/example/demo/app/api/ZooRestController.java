package com.example.demo.app.api;

import com.example.demo.repository.ZooDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getAllZoo() throws JsonProcessingException {
        var allZooList = zooDao.getZooList();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allZooList);
    }
}
