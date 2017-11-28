package com.epm.gdsa.notice;

import oracle.sql.BFILE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component(value="noticeDaoImpl")
public class NoticeDaoImpl implements NoticeDao {
	
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
