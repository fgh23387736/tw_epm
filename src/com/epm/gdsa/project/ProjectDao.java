package com.epm.gdsa.project;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;


public interface ProjectDao {
	public Project add(Project project);
	public void update(Project project);
	public void delete(Project project);
	public Project getById(Integer id);
	public List<Project> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByUserAndName(Project project);
	public DetachedCriteria getCriteriaByJoinUser(Project project);
	public DetachedCriteria getCriteriaByJoinUserAndName(Project project);
	public DetachedCriteria getCriteriaByEndDateBNot(Project project);
	public DetachedCriteria getCriteriaByStartDateBetween(Date startDate,
			Date endDate);
	public DetachedCriteria getCriteriaByEndDateBBetween(Date startDate,
			Date endDate);
}
