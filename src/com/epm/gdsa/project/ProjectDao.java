package com.epm.gdsa.project;

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
}
