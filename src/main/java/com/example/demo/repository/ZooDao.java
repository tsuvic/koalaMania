package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Zoo;

public interface ZooDao {
	Zoo findById(int id);
	
	List<Zoo> getZooList();
}
