package com.epm.gdsa.document;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component(value="documentDaoImpl")
public class DocumentDaoImpl implements DocumentDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public void add() {
		// TODO 自动生成的方法存根
		System.out.println("Dao....");
		
	}
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
}
