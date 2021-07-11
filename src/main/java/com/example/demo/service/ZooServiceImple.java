package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Zoo;
import com.example.demo.repository.ZooDao;

@Service
public class ZooServiceImple implements ZooService {
	
	private final ZooDao zooDao;

	public ZooServiceImple(ZooDao zooDao) {
		this.zooDao = zooDao;
	}
	
	public Zoo findById(int id) {
		
		return zooDao.findById(id);
	}

}
