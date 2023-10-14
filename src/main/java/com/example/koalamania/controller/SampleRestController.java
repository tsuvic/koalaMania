package com.example.koalamania.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class SampleRestController {

    @GetMapping("/api")
    public String searchAnimals() {
        return "test";
    }
}