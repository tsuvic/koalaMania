package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Koala;

public interface KoalaDao {
	
	List<Koala> getAll();
	
	int insert(Koala koala);

	Koala findById(int id);
	
	void update(Koala koala);
	
	void delete(int koala_id);
}