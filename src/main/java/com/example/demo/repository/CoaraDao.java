package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Coara;

public interface CoaraDao {
	
	List<Coara> getAll();
	
	void insert(Coara coara);

  Coara findById(Long id);
	
}