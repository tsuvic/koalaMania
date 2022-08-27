package com.example.demo.service;

import com.example.demo.entity.Post;
import com.example.demo.entity.Zoo;

import java.util.List;

public interface ZooService {
	Zoo findById(int zooId);
	Zoo getFavoriteZoo(int userId);
	List<Post> getPostListByZooId(int zooId);
}
