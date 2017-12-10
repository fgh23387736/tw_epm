package com.epm.gdsa.specification;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface SpecificationDao {
	public Specification add(Specification specification);
	public void update(Specification specification);
	public void delete(Specification specification);
	public Specification getById(Integer id);
	public List<Specification> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProject(Specification specification);
	public DetachedCriteria getCriteriaByProjectAndName(Specification specification);
}
