package com.example.demo.repository;

import java.util.List;

public interface PostImageDao {
	
	void insertNewPostImage(int post_id,List<Integer> animalIdList,List<String> imageAddressList);
}
