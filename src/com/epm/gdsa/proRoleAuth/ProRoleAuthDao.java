package com.epm.gdsa.proRoleAuth;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.epm.gdsa.proRoleAuth.ProRoleAuth;
import com.epm.gdsa.project.Project;


public interface ProRoleAuthDao {
	public ProRoleAuth add(ProRoleAuth proRoleAuth);
	public void update(ProRoleAuth proRoleAuth);
	public void delete(ProRoleAuth proRoleAuth);
	public ProRoleAuth getById(Integer id);
	public List<ProRoleAuth> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProRole(ProRoleAuth proRoleAuth);
	public DetachedCriteria getCriteriaByProRoleAndAuth(ProRoleAuth proRoleAuth);
}
