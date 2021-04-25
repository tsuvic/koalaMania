package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.CoaraInsertForm;
import com.example.demo.entity.Coara;
import com.example.demo.repository.CoaraDao;

@Service
public class CoaraServiceImpl implements CoaraService{

	private final CoaraDao dao;
	
	@Autowired
	public CoaraServiceImpl(CoaraDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Coara> getAll() {
		return dao.getAll();
	}
	
	Coara coara;
	@Override
	public void insert(CoaraInsertForm form){
		Coara coara = new Coara();
		coara.setName(form.getName());
		coara.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(),form.getBirthMonth(),form.getBirthDay());
		if(birthDate != null) {
			coara.setBirthdate(birthDate);
		}
		coara.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(),form.getDeathMonth(),form.getDeathDay());
		if(deathDate != null) {
			coara.setDeathdate(deathDate);
		}
		coara.setZoo(form.getZoo());
		coara.setMother(form.getMother());
		coara.setFather(form.getFather());
		coara.setDetails(form.getDetails());
		coara.setFeature(form.getFeature());
		dao.insert(coara);
	}
	
	public Date getDate(String year , String month , String day){
		String hyphen = "-";
		//年、月、日の中に"--"が含まれていたら、"9999-01-01"を返す。
		String dummyValue = "0";
		if(year.equals(dummyValue) || month.equals(dummyValue) || day.equals(dummyValue)) {
			year = "9999";
			month = "01";
			day = "01";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(year);
		sb.append(hyphen);
		sb.append(month);
		sb.append(hyphen);
		sb.append(day);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = dateFormat.parse(sb.toString());
			return date;
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Coara findById(Long id) {
		return dao.findById(id);
	}
	
	@Override
	public void update(CoaraInsertForm form) {
		Coara coara = new Coara();
		coara.setCoara_id(form.getCoara_id());
		coara.setName(form.getName());
		coara.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(),form.getBirthMonth(),form.getBirthDay());
		if(birthDate != null) {
			coara.setBirthdate(birthDate);
		}
		coara.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(),form.getDeathMonth(),form.getDeathDay());
		if(deathDate != null) {
			coara.setDeathdate(deathDate);
		}
		coara.setZoo(form.getZoo());
		coara.setMother(form.getMother());
		coara.setFather(form.getFather());
		coara.setDetails(form.getDetails());
		coara.setFeature(form.getFeature());
		dao.update(coara);
	}
	
	@Override
	public void delete(int coara_id) {
		dao.delete(coara_id);
	}
		
}
