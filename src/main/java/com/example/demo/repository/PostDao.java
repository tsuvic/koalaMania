package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Post;

public interface PostDao {
	
	int insertNewPost(Post post);
	
	List<Post> getPostListByZooId(int zoo_id);
}
