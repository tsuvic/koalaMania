package com.example.demo.service;

import java.util.List;

import com.example.demo.app.CoaraInsertForm;
import com.example.demo.entity.Coara;


public interface CoaraService {
	
	List<Coara> getAll();
	
	void insert(CoaraInsertForm form);

}