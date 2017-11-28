package com.epm.gdsa.proRole;

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
import com.epm.gdsa.proRole.ProRoleDao;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.project.Project;
import com.epm.utils.HibernateUtils;

@Component(value="proRoleDaoImpl")
public class ProRoleDaoImpl implements ProRoleDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public ProRole getById(Integer id) {
		return hibernateTemplate.get(ProRole.class, id);
	}
	
	@Override
	public ProRole add(ProRole proRole) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(proRole);
		System.out.println("Id:"+proRole.getProRoleId());
		return proRole;
	}
	
	@Override
	public void update(ProRole proRole) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(proRole);
		System.out.println("dao");
	}
	
	@Override
	public void delete(ProRole proRole) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(proRole);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<ProRole> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<ProRole> newProRoles = new ArrayList<ProRole>();
		if(page != null && pageSize != null){
			newProRoles = (List<ProRole>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newProRoles = (List<ProRole>) hibernateTemplate.findByCriteria(criteria);
		}
		return newProRoles;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProRole.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("proRoleId").in( ids ) );
		}
		return criteria;
	}


	@Override
	public DetachedCriteria getCriteriaByProjectAndName(ProRole proRole) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProRole.class);
		criteria.add(Property.forName("project").eq(proRole.getProject()));
		criteria.add(Property.forName("name").like("%"+proRole.getName()+"%"));
		return criteria;
	}


	
}
