package com.example.demo.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface CloudinaryService {
	Map uploadKoalaImage(File uploadFile,int koala_id);
	
	List<String> deleteKoalaImage(String[] KoalaImageFiles,int koala_id);
	
	void  deleteDirs(int koala_id);
}
