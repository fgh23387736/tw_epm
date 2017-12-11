package com.epm.gdsa.learnDoc;

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

@Component(value="learnDocDaoImpl")
public class LearnDocDaoImpl implements LearnDocDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public LearnDoc getById(Integer id) {
		return hibernateTemplate.get(LearnDoc.class, id);
	}
	
	@Override
	public LearnDoc add(LearnDoc learnDoc) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(learnDoc);
		System.out.println("Id:"+learnDoc.getLearnDocId());
		return learnDoc;
	}
	
	@Override
	public void update(LearnDoc learnDoc) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(learnDoc);
		System.out.println("dao");
	}
	
	@Override
	public void delete(LearnDoc learnDoc) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(learnDoc);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<LearnDoc> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<LearnDoc> newLearnDocs = new ArrayList<LearnDoc>();
		if(page != null && pageSize != null){
			newLearnDocs = (List<LearnDoc>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newLearnDocs = (List<LearnDoc>) hibernateTemplate.findByCriteria(criteria);
		}
		return newLearnDocs;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LearnDoc.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("learnDocId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProject(LearnDoc learnDoc) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LearnDoc.class);
		criteria.add(Property.forName("project").eq(learnDoc.getProject()));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProjectAndName(LearnDoc learnDoc) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LearnDoc.class);
		criteria.add(Property.forName("project").eq(learnDoc.getProject()));
		criteria.add(Property.forName("name").like("%"+learnDoc.getName()+"%"));
		return criteria;
	}


	
}
