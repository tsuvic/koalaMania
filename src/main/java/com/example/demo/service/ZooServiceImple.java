package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Post;
import com.example.demo.entity.Zoo;
import com.example.demo.repository.PostDao;
import com.example.demo.repository.ZooDao;

@Service
public class ZooServiceImple implements ZooService {
	
	private final ZooDao zooDao;
	private final PostDao postDao;

	public ZooServiceImple(ZooDao zooDao,PostDao postDao) {
		this.zooDao = zooDao;
		this.postDao = postDao;
	}
	
	@Override
	public Zoo findById(int zoo_id) {
		
		return zooDao.findById(zoo_id);
	}
	
	@Override
	public List<Post> getPostListByZooId(int zoo_id){
		
		return postDao. getPostListByZooId(zoo_id);
	}
	

}
