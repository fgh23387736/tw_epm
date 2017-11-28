package com.epm.gdsa.userPro;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.project.Project;


public interface UserProDao {
	public UserPro add(UserPro userPro);
	public void update(UserPro userPro);
	public void delete(UserPro userPro);
	public UserPro getById(Integer id);
	public List<UserPro> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProjectAndUserName(UserPro userPro,
			String userName);
	public DetachedCriteria getCriteriaByProjectAndUser(UserPro userPro);
}
