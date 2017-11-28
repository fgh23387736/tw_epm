package com.epm.gdsa.point;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface PointDao {
	public Point add(Point point);
	public void update(Point point);
	public void delete(Point point);
	public Point getById(Integer id);
	public List<Point> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProject(Point point);
	public DetachedCriteria getCriteriaByProjectAndName(Point point);
}
