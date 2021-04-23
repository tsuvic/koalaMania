package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Coara;
import com.example.demo.repository.CoaraDao;

@Service
public class CoaraServiceImpl implements CoaraService{

	private final CoaraDao dao;
	
	@Autowired
	public CoaraServiceImpl(CoaraDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Coara> getAll() {
		return dao.getAll();
	}
	
	@Override
	public Coara findById(Long id) {
		return dao.findById(id);
	}
	
	
}
