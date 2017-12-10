package com.epm.gdsa.document;

import java.util.ArrayList;
import java.util.Date;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.epm.gdsa.proRole.ProRole;
import com.epm.utils.HibernateUtils;

@Component(value="documentDaoImpl")
public class DocumentDaoImpl implements DocumentDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Document getById(Integer id) {
		return hibernateTemplate.get(Document.class, id);
	}
	
	@Override
	public Document add(Document document) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(document);
		System.out.println("Id:"+document.getDocumentId());
		return document;
	}
	
	@Override
	public void update(Document document) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(document);
		System.out.println("dao");
	}
	
	@Override
	public void delete(Document document) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(document);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Document> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Document> newDocuments = new ArrayList<Document>();
		if(page != null && pageSize != null){
			newDocuments = (List<Document>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newDocuments = (List<Document>) hibernateTemplate.findByCriteria(criteria);
		}
		return newDocuments;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("documentId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProject(Document document) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class);
		criteria.add(Property.forName("project").eq(document.getProject()));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProjectAndName(Document document) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class);
		criteria.add(Property.forName("project").eq(document.getProject()));
		criteria.add(Property.forName("name").like("%"+document.getName()+"%"));
		return criteria;
	}


	
}
