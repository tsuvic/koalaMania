package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Post;

public interface PostFavoriteDao {
	List<Post> getFavoritePost(List<Post> postList);
	
	void insertPostFavorite(int post_id);
	
	void deletePostFavorite(int post_id);
	
	Post getPostFavorite(Post post);
	
	List<Post> getPostFavoirteByUserId(int user_id);
}
