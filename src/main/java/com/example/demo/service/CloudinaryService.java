package com.example.demo.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface CloudinaryService {
	Map uploadAnimalImage(File uploadFile,int animal_id);
	
	Map uploadAnimalProfileImage(File uploadFile,int animal_id);
	
	Map uploadUserProfileImage(File uploadFile,int user_id);
	
	List<String> deleteAnimalImage(String[] AnimalImageFiles,int animal_id);
	
	void  deleteDirs(int animal_id);
	
	Map uploadPostImage(File uploadFile,int post_id);
}
