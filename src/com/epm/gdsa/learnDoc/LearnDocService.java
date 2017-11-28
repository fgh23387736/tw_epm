package com.epm.gdsa.learnDoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component(value="learnDocService")
public class LearnDocService {
	
	@Autowired
	private LearnDocDao learnDocDao;

	
	
	public LearnDocDao getLearnDocDao() {
		return learnDocDao;
	}



	public void setLearnDocDao(LearnDocDao learnDocDao) {
		this.learnDocDao = learnDocDao;
	}



	public void add() {
		System.out.println("service....");
		learnDocDao.add();
		
	}
}
