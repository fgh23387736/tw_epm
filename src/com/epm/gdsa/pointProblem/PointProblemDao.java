package com.epm.gdsa.pointProblem;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface PointProblemDao {
	public PointProblem add(PointProblem pointProblem);
	public void update(PointProblem pointProblem);
	public void delete(PointProblem pointProblem);
	public PointProblem getById(Integer id);
	public List<PointProblem> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByPoint(PointProblem pointProblem);
}
