package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.KoalaImage;

public interface KoalaImageDao {
	
	int insert(KoalaImage koalaImage);
	
	void delete(List<Integer> koalaimage_idList);
}
