package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import com.example.demo.entity.Koala;
import com.example.demo.entity.KoalaForTree;
import com.example.demo.entity.RelationForTree;
import com.example.demo.entity.Zoo;

public interface KoalaDao {

	List<Koala> getAll();

	List<Koala> getMotherList(int koala_id, Date birthDay);

	List<Koala> getFatherList(int koala_id, Date birthDay);

	List<Koala> findByKeyword(String keyword);

	List<Zoo> getZooList();

	int insert(Koala koala);

	Koala findById(int id);

	void update(Koala koala);

	void delete(int koala_id);
	
	void urlUpdate(int koala_id, String url);
	
	KoalaForTree getKoalaForTree(int id);

	List<KoalaForTree> getBrotherKoalaForTree(int koala_id, int mother_id, int father_id);
	
	List<KoalaForTree> getChildrenKoalaForTree(int koala_id, int sex);
	
}