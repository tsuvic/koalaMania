package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Post;

public interface PostFavoriteService {
	void insertPostFavoirte(int post_id);
	
	void deletePostFavoirte(int post_id);
	
	List<Post> getPostFavoirteByUserId(int user_id);
}
