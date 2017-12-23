package com.epm.gdsa.user;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;




public interface UserDao {
	public User add(User user);
	public void update(User user);
	public void delete(User user);
	public User getById(Integer id);
	public List<User> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByLoginName(String loginName);
	public DetachedCriteria getCriteriaByNameAndLoginName(User user);
	public DetachedCriteria getCriteriaByMinType(User user);
	
}
