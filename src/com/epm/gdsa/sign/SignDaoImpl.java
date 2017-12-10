package com.epm.gdsa.sign;

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

@Component(value="signDaoImpl")
public class SignDaoImpl implements SignDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Sign getById(Integer id) {
		return hibernateTemplate.get(Sign.class, id);
	}
	
	@Override
	public Sign add(Sign sign) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(sign);
		System.out.println("Id:"+sign.getSignId());
		return sign;
	}
	
	@Override
	public void update(Sign sign) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(sign);
		System.out.println("dao");
	}
	
	@Override
	public void delete(Sign sign) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(sign);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Sign> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Sign> newSigns = new ArrayList<Sign>();
		if(page != null && pageSize != null){
			newSigns = (List<Sign>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newSigns = (List<Sign>) hibernateTemplate.findByCriteria(criteria);
		}
		return newSigns;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Sign.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("signId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProject(Sign sign) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Sign.class);
		criteria.add(Property.forName("project").eq(sign.getProject()));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByUser(Sign sign) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Sign.class);
		criteria.add(Property.forName("user").eq(sign.getUser()));
		return criteria;
	}
	
}
