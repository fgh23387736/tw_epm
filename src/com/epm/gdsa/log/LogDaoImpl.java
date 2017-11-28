package com.epm.gdsa.log;

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

import com.epm.gdsa.log.Log;
import com.epm.gdsa.log.LogDao;
import com.epm.gdsa.log.Log;
import com.epm.gdsa.log.Log;
import com.epm.utils.HibernateUtils;

@Component(value="logDaoImpl")
public class LogDaoImpl implements LogDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Log getById(Integer id) {
		return hibernateTemplate.get(Log.class, id);
	}
	
	@Override
	public Log add(Log log) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(log);
		System.out.println("Id:"+log.getLogId());
		return log;
	}
	
	@Override
	public void update(Log log) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(log);
		System.out.println("dao");
	}
	
	@Override
	public void delete(Log log) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(log);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Log> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Log> newLogs = new ArrayList<Log>();
		if(page != null && pageSize != null){
			newLogs = (List<Log>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newLogs = (List<Log>) hibernateTemplate.findByCriteria(criteria);
		}
		return newLogs;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Log.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("logId").in( ids ) );
		}
		return criteria;
	}


	
}
