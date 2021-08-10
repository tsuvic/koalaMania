package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostFavoriteDao;

@Service
public class PostFavoriteServiceImpl implements PostFavoriteService {
	
	final private PostFavoriteDao postFavoriteDao;
	
	@Autowired
	public PostFavoriteServiceImpl(PostFavoriteDao postFavoriteDao) {
		this.postFavoriteDao = postFavoriteDao;
	}

	@Override
	public void insertPostFavoirte(int post_id) {
		postFavoriteDao.insertPostFavorite(post_id);
	}

	@Override
	public void deletePostFavoirte(int post_id) {
		postFavoriteDao.deletePostFavorite(post_id);
	}
	
	@Override
	public List<Post> getPostFavoirteByUserId(int user_id) {
		return postFavoriteDao.getFavoritePost(postFavoriteDao.getPostFavoirteByUserId(user_id));
	}

}
