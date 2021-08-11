package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.Post;

public interface PostFavoriteService {
	Map<String, Object> insertPostFavoirte(int post_id);

	Map<String, Object> deletePostFavoirte(int post_id);

	List<Post> getPostFavoirteByUserId(int user_id);
}
