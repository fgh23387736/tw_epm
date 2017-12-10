package com.epm.gdsa.log;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.epm.gdsa.log.Log;


public interface LogDao {
	public Log add(Log log);
	public void update(Log log);
	public void delete(Log log);
	public Log getById(Integer id);
	public List<Log> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProject(Log log);
}
