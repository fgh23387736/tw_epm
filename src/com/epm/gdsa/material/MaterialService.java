package com.epm.gdsa.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component(value="materialService")
public class MaterialService {
	
	@Autowired
	private MaterialDao materialDao;

	
	
	public MaterialDao getMaterialDao() {
		return materialDao;
	}



	public void setMaterialDao(MaterialDao materialDao) {
		this.materialDao = materialDao;
	}



	public void add() {
		System.out.println("service....");
		materialDao.add();
		
	}
}
