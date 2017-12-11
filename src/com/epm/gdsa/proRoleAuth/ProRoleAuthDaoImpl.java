package com.epm.gdsa.proRoleAuth;

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

import com.epm.gdsa.proRoleAuth.ProRoleAuth;
import com.epm.gdsa.proRoleAuth.ProRoleAuthDao;
import com.epm.gdsa.proRoleAuth.ProRoleAuth;
import com.epm.gdsa.proRoleAuth.ProRoleAuth;
import com.epm.gdsa.project.Project;
import com.epm.utils.HibernateUtils;

@Component(value="proRoleAuthDaoImpl")
public class ProRoleAuthDaoImpl implements ProRoleAuthDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public ProRoleAuth getById(Integer id) {
		return hibernateTemplate.get(ProRoleAuth.class, id);
	}
	
	@Override
	public ProRoleAuth add(ProRoleAuth proRoleAuth) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(proRoleAuth);
		System.out.println("Id:"+proRoleAuth.getProRoleAuthId());
		return proRoleAuth;
	}
	
	@Override
	public void update(ProRoleAuth proRoleAuth) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(proRoleAuth);
		System.out.println("dao");
	}
	
	@Override
	public void delete(ProRoleAuth proRoleAuth) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(proRoleAuth);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<ProRoleAuth> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<ProRoleAuth> newProRoleAuths = new ArrayList<ProRoleAuth>();
		if(page != null && pageSize != null){
			newProRoleAuths = (List<ProRoleAuth>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newProRoleAuths = (List<ProRoleAuth>) hibernateTemplate.findByCriteria(criteria);
		}
		return newProRoleAuths;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProRoleAuth.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("proRoleAuthId").in( ids ) );
		}
		return criteria;
	}


	@Override
	public DetachedCriteria getCriteriaByProRole(ProRoleAuth proRoleAuth) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProRoleAuth.class);
		criteria.add(Property.forName("proRole").eq(proRoleAuth.getProRole()));
		return criteria;
	}


	
}
