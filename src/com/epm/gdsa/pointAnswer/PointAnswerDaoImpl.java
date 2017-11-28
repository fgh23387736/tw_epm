package com.epm.gdsa.pointAnswer;

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

@Component(value="pointAnswerDaoImpl")
public class PointAnswerDaoImpl implements PointAnswerDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public PointAnswer getById(Integer id) {
		return hibernateTemplate.get(PointAnswer.class, id);
	}
	
	@Override
	public PointAnswer add(PointAnswer pointAnswer) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(pointAnswer);
		System.out.println("Id:"+pointAnswer.getPointAnswerId());
		return pointAnswer;
	}
	
	@Override
	public void update(PointAnswer pointAnswer) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(pointAnswer);
		System.out.println("dao");
	}
	
	@Override
	public void delete(PointAnswer pointAnswer) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(pointAnswer);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<PointAnswer> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<PointAnswer> newPointAnswers = new ArrayList<PointAnswer>();
		if(page != null && pageSize != null){
			newPointAnswers = (List<PointAnswer>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newPointAnswers = (List<PointAnswer>) hibernateTemplate.findByCriteria(criteria);
		}
		return newPointAnswers;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PointAnswer.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("pointAnswerId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByPointProblem(PointAnswer pointAnswer) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PointAnswer.class);
		criteria.add(Property.forName("pointProblem").eq(pointAnswer.getPointProblem()));
		return criteria;
	}


	
}
