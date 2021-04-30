package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.CoaraImage;

public interface CoaraImageDao {
	
	int insert(CoaraImage coaraImage);
	
	void delete(List<Integer> coaraimage_idList);
}
