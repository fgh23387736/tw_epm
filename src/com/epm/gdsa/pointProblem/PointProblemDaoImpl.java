package com.epm.gdsa.pointProblem;

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

@Component(value="pointProblemDaoImpl")
public class PointProblemDaoImpl implements PointProblemDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public PointProblem getById(Integer id) {
		return hibernateTemplate.get(PointProblem.class, id);
	}
	
	@Override
	public PointProblem add(PointProblem pointProblem) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(pointProblem);
		System.out.println("Id:"+pointProblem.getPointProblemId());
		return pointProblem;
	}
	
	@Override
	public void update(PointProblem pointProblem) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(pointProblem);
		System.out.println("dao");
	}
	
	@Override
	public void delete(PointProblem pointProblem) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(pointProblem);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<PointProblem> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<PointProblem> newPointProblems = new ArrayList<PointProblem>();
		if(page != null && pageSize != null){
			newPointProblems = (List<PointProblem>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newPointProblems = (List<PointProblem>) hibernateTemplate.findByCriteria(criteria);
		}
		return newPointProblems;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PointProblem.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("pointProblemId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByPoint(PointProblem pointProblem) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PointProblem.class);
		criteria.add(Property.forName("point").eq(pointProblem.getPoint()));
		return criteria;
	}


	
}
