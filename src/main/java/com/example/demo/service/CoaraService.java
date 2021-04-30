package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.CoaraInsertForm;
import com.example.demo.entity.Coara;


public interface CoaraService {
	
	List<Coara> getAll();
	
	void insert(CoaraInsertForm form);

	Coara findById(Long id);
	
	void update(CoaraInsertForm form);
	
	void delete(int coara_id);
	
	void inserCoaraImage(int coara_id, List<MultipartFile> coaraImage);
	
	void deleteCoaraImage(String CoaraImageIds , int coara_id);
}