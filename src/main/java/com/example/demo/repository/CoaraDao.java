package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Coara;

public interface CoaraDao {
	
	List<Coara> getAll();
	
	int insert(Coara coara);

	Coara findById(Long id);
	
	void update(Coara coara);
	
	void delete(int coara_id);
}