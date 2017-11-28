package com.epm.gdsa.point;

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

@Component(value="pointDaoImpl")
public class PointDaoImpl implements PointDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Point getById(Integer id) {
		return hibernateTemplate.get(Point.class, id);
	}
	
	@Override
	public Point add(Point point) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(point);
		System.out.println("Id:"+point.getPointId());
		return point;
	}
	
	@Override
	public void update(Point point) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(point);
		System.out.println("dao");
	}
	
	@Override
	public void delete(Point point) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(point);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Point> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Point> newPoints = new ArrayList<Point>();
		if(page != null && pageSize != null){
			newPoints = (List<Point>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newPoints = (List<Point>) hibernateTemplate.findByCriteria(criteria);
		}
		return newPoints;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Point.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("pointId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProject(Point point) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Point.class);
		criteria.add(Property.forName("project").eq(point.getProject()));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProjectAndName(Point point) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Point.class);
		criteria.add(Property.forName("project").eq(point.getProject()));
		criteria.add(Property.forName("name").like("%"+point.getName()+"%"));
		return criteria;
	}


	
}
