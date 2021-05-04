package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.KoalaInsertForm;
import com.example.demo.entity.Koala;
import com.example.demo.entity.KoalaImage;


public interface KoalaService {
	
	List<Koala> getAll();
	
	void insert(KoalaInsertForm form);

	Koala findById(int i);
	
	void update(KoalaInsertForm form);
	
	void delete(int koala_id);
	
	List<KoalaImage> findKoalaImageById(int id);
	
	void inserKoalaImage(int koala_id, List<MultipartFile> koalaImage);
	
	void deleteKoalaImage(String KoalaImageIds , int koala_id);
}