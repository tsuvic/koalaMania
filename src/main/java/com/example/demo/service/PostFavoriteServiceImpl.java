package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String,Object> insertPostFavoirte(int post_id) {
		long count = postFavoriteDao.insertPostFavorite(post_id);
		Map<String, Object> map =  new HashMap<String,Object>(){{put("count", count);}};
		return map;
	}

	@Override
	public Map<String,Object> deletePostFavoirte(int post_id) {
		long count  = postFavoriteDao.deletePostFavorite(post_id);
		Map<String, Object> map =  new HashMap<String,Object>(){{put("count", count);}};
		return map;
	}
	
	@Override
	public List<Post> getPostFavoirteByUserId(int user_id) {
		return postFavoriteDao.getPostFavoriteList(postFavoriteDao.getPostFavoirteByUserId(user_id));
	}

}
