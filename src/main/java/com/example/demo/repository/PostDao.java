package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Post;

public interface PostDao {
	
	int insertNewPost(Post post);
	
	List<Post> getPostListByZooId(int zoo_id);
	
	Post getPostByPostId(int post_id);
	
	List<Post> getPostListByUserId(int user_id);
	
	List<Post> getCommentListByUserId(int user_id);
	
	void deletePost(int post_id);
}
