package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.KoalaImage;
import com.example.demo.entity.KoalaProfileImage;

public interface KoalaProfileImageDao {
	
	int insert(KoalaProfileImage koalaprofileImage);
	
	void delete(KoalaProfileImage koalaprofileImage);
	
	KoalaProfileImage findByKoala_id(int id);
}
