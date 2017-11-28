package com.epm.gdsa.proRole;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.project.Project;


public interface ProRoleDao {
	public ProRole add(ProRole proRole);
	public void update(ProRole proRole);
	public void delete(ProRole proRole);
	public ProRole getById(Integer id);
	public List<ProRole> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProjectAndName(ProRole proRole);
}
