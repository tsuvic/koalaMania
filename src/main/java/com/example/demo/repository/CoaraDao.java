package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Coara;

public interface CoaraDao {
	
	void insertCoara(Coara Coara);
	
//  This is used in the latter chapter
//  こちらは後で使用
//	int updateCoara(Coara Coara);
	
	List<Coara> getAll();
}