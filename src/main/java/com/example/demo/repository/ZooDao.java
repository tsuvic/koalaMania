package com.example.demo.repository;

import com.example.demo.entity.Zoo;

import java.util.List;

public interface ZooDao {
	Zoo findById(int zooId);
	Zoo getFavoriteZoo(int userId);
	List<Zoo> getZooList();
}
