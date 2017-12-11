package com.epm.gdsa.material;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface MaterialDao {
	public Material add(Material material);
	public void update(Material material);
	public void delete(Material material);
	public Material getById(Integer id);
	public List<Material> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProject(Material material);
}
