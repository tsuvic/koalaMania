package com.example.demo.repository;

import com.example.demo.entity.Post;

import java.util.List;

public interface PostDao {
	List<Post> getPostsListByUserId(int userId);
	List<Post> getCommentsListByUserId(int userId);
	List<Post> getImagesListByUserId(int userId);
	List<Post> getFavoritesListByUserId(int userId);

	int insertNewPost(Post post);
	
	List<Post> getPostListByZooId(int zoo_id);
	
	Post getPostByPostId(int post_id);
	

	void deletePost(int post_id);
}
