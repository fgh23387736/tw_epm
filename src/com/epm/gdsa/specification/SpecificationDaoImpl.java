package com.epm.gdsa.specification;

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

@Component(value="specificationDaoImpl")
public class SpecificationDaoImpl implements SpecificationDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Specification getById(Integer id) {
		return hibernateTemplate.get(Specification.class, id);
	}
	
	@Override
	public Specification add(Specification specification) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(specification);
		System.out.println("Id:"+specification.getSpecificationId());
		return specification;
	}
	
	@Override
	public void update(Specification specification) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(specification);
		System.out.println("dao");
	}
	
	@Override
	public void delete(Specification specification) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(specification);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Specification> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Specification> newSpecifications = new ArrayList<Specification>();
		if(page != null && pageSize != null){
			newSpecifications = (List<Specification>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newSpecifications = (List<Specification>) hibernateTemplate.findByCriteria(criteria);
		}
		return newSpecifications;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Specification.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("specificationId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProject(Specification specification) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Specification.class);
		criteria.add(Property.forName("project").eq(specification.getProject()));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProjectAndName(Specification specification) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Specification.class);
		criteria.add(Property.forName("project").eq(specification.getProject()));
		criteria.add(Property.forName("name").like("%"+specification.getName()+"%"));
		return criteria;
	}


	
}
