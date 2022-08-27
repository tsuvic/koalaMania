package com.example.demo.service;

import com.example.demo.entity.Post;
import com.example.demo.entity.Zoo;
import com.example.demo.repository.PostDao;
import com.example.demo.repository.PostFavoriteDao;
import com.example.demo.repository.ZooDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZooServiceImple implements ZooService {
	
	private final ZooDao zooDao;
	private final PostDao postDao;
	private final PostFavoriteDao postFavoriteDao;

	public ZooServiceImple(ZooDao zooDao,PostDao postDao,PostFavoriteDao postFavoriteDao) {
		this.zooDao = zooDao;
		this.postDao = postDao;
		this.postFavoriteDao = postFavoriteDao;
	}
	
	@Override
	public Zoo findById(int zooId) {
		return zooDao.findById(zooId);
	}

	@Override
	public Zoo getFavoriteZoo(int userId){
		return zooDao.getFavoriteZoo(userId);
	}

	@Override
	public List<Post> getPostListByZooId(int zooId){
		return postFavoriteDao.getPostFavoriteList(postDao.getPostListByZooId(zooId));
	}
}
