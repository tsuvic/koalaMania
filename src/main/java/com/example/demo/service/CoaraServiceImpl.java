package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void save(Coara coara) {
		dao.insertCoara(coara);
	}

	
//  This method is used in the latter chapter
//	@Override
//	public void update(Inquiry inquiry) {
//		
//		//return dao.updateInquiry(inquiry);
//		if(dao.updateInquiry(inquiry) == 0) {
//			throw new InquiryNotFoundException("can't find the same ID");
//		}
//	}testdayo test2
	
	@Override
	public List<Coara> getAll() {
		return dao.getAll();
	}
}
