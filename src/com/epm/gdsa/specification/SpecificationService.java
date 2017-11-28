package com.epm.gdsa.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component(value="specificationService")
public class SpecificationService {
	
	@Autowired
	private SpecificationDao specificationDao;

	
	
	public SpecificationDao getSpecificationDao() {
		return specificationDao;
	}



	public void setSpecificationDao(SpecificationDao specificationDao) {
		this.specificationDao = specificationDao;
	}



	public void add() {
		System.out.println("service....");
		specificationDao.add();
		
	}
}
