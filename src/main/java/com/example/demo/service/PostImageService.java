package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.PostImage;

public interface PostImageService {
	List<PostImage> getPostImageListByAnimalId(int animal_id);
}
