package com.example.demo.repository;

public interface PostImageDao {
	
	int insertNewPostImage(int post_id,int animalId);
	void updateUrl(int postImageId,String url);
}
