package com.epm.gdsa.sign;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface SignDao {
	public Sign add(Sign sign);
	public void update(Sign sign);
	public void delete(Sign sign);
	public Sign getById(Integer id);
	public List<Sign> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProject(Sign sign);
	public DetachedCriteria getCriteriaByUser(Sign sign);
}
