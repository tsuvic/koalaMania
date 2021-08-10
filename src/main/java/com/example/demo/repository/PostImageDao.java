package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.PostImage;

public interface PostImageDao {
	
	int insertNewPostImage(int post_id,int animalId);
	
	void updateUrl(int postImageId,String url);
	
	List<PostImage> getPostImageListByAnimalId(int animal_id);
	
	List<PostImage> getPostImageListByUserId(int user_id);
	
	PostImage getPostImageByPostImageId(int postImage_id);
}
