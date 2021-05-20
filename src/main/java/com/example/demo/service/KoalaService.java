package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.KoalaInsertForm;
import com.example.demo.entity.Koala;
import com.example.demo.entity.Zoo;
import com.example.demo.entity.KoalaImage;

public interface KoalaService {

	List<Koala> getAll();

	List<Koala> getMotherList(int koala_id, String birthYear, String birthMonth, String birthDay);

	List<Koala> getFatherList(int koala_id, String birthYear, String birthMonth, String birthDay);

	List<Koala> findByKeyword(String keyword);

	List<Zoo> getZooList();

	void insert(KoalaInsertForm form);

	Koala findById(int i);

	void update(KoalaInsertForm form);

	void delete(int koala_id);

	List<KoalaImage> findKoalaImageById(int id);
	
	void insertKoalaProfileImage(int koala_id, MultipartFile koalaProfileImage, String profileImagePath);

	void insertKoalaImage(int koala_id, List<MultipartFile> koalaImage);

	void deleteKoalaImage(String KoalaImageIds, int koala_id);
}