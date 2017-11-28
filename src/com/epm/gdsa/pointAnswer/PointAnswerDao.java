package com.epm.gdsa.pointAnswer;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface PointAnswerDao {
	public PointAnswer add(PointAnswer pointAnswer);
	public void update(PointAnswer pointAnswer);
	public void delete(PointAnswer pointAnswer);
	public PointAnswer getById(Integer id);
	public List<PointAnswer> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByPointProblem(PointAnswer pointAnswer);
}
