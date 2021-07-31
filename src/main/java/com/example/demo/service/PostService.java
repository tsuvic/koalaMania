package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.PostInsertForm;
import com.example.demo.entity.Animal;
import com.example.demo.entity.Zoo;

public interface PostService {
	Zoo getZooById(int zoo_id);
	
	List<Animal> getAnimalListByZooId(int zoo_id);
	
	void insertNewPost(PostInsertForm postInsertForm);
	
	String insertPostImage(int post_id, MultipartFile postImageUpload ,int post_image_id);
}
