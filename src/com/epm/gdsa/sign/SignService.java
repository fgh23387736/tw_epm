package com.epm.gdsa.sign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component(value="signService")
public class SignService {
	
	@Autowired
	private SignDao signDao;

	
	
	public SignDao getSignDao() {
		return signDao;
	}



	public void setSignDao(SignDao signDao) {
		this.signDao = signDao;
	}



	public void add() {
		System.out.println("service....");
		signDao.add();
		
	}
}
