package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.PostImage;
import com.example.demo.repository.PostImageDao;

@Service
public class PostImageServiceImpl implements PostImageService {
	
	final private PostImageDao postImageDao;
	
	@Autowired
	public PostImageServiceImpl(PostImageDao postImageDao) {
		this.postImageDao = postImageDao;
	}

	@Override
	public List<PostImage> getPostImageListByAnimalId(int animal_id) {
		
		return postImageDao.getPostImageListByAnimalId(animal_id);
	}

}
