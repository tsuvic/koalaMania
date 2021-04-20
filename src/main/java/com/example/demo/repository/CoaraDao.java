package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Coara;

public interface CoaraDao {
	
	List<Coara> getAll();
}