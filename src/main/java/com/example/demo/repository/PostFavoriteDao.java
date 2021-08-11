package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Post;

public interface PostFavoriteDao {
	List<Post> getPostFavoriteList(List<Post> postList);
	
	long insertPostFavorite(int post_id);
	
	long deletePostFavorite(int post_id);
	
	Post getPostFavorite(Post post);
	
	List<Post> getPostFavoirteByUserId(int user_id);
	
	Post getPostFavoirteCount(Post post);
}
