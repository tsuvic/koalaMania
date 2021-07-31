package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Post;
import com.example.demo.entity.Zoo;

public interface ZooService {
	Zoo findById(int zoo_id);
	List<Post> getPostListByZooId(int zoo_id);  
}
