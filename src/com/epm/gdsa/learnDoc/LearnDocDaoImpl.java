package com.epm.gdsa.learnDoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component(value="learnDocDaoImpl")
public class LearnDocDaoImpl implements LearnDocDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public void add() {
		// TODO 自动生成的方法存根
		System.out.println("Dao....");
		LearnDoc learnDoc = new LearnDoc();
		learnDoc.setContent("ceshi");
		learnDoc.setType(1);
		hibernateTemplate.save(learnDoc);
	}
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
}
