package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Zoo;
import com.example.demo.repository.ZooDao;

@Service
public class PostServiceImpl implements PostService {
	
	private final ZooDao zooDao;
	
	@Autowired
	public PostServiceImpl(ZooDao zooDao) {
		this.zooDao = zooDao;
	}

	@Override
	public Zoo getZooById(int zoo_id) {
		return zooDao.findById(zoo_id);
	}

}
