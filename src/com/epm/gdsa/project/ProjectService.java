package com.epm.gdsa.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gdsa.project.Project;
import com.epm.gdsa.point.Point;
import com.epm.gdsa.project.ProjectDao;
import com.epm.gdsa.user.User;

@Transactional
@Component(value="projectService")
public class ProjectService {
	
	@Autowired
	private ProjectDao projectDao;

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
	public List<Map<String, Object>> getProjectByKeys(String keys,List<Project> projects){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"projectId",
					"content",
					"name",
					"startDate",
					"endDateA",
					"endDateB",
					"points",
					"user",
					"percentage",
					"longitude",
					"latitude",
					"radius"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Project project : projects) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(project,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Project project,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
			case "projectId":
				return project.getProjectId();
			case "name":
				return project.getName();
			case "startDate":
				return project.getStartDate();
			case "endDateA":
				return project.getEndDateA();
			case "endDateB":
				return project.getEndDateB();
			case "content":
				return project.getContent();
			case "longitude":
				return project.getLongitude();
			case "latitude":
				return project.getLatitude();
			case "radius":
				return project.getRadius();
			case "percentage":
				list = new ArrayList<Map<String, Object>>();
				Set<Point> thePoints = project.getPoints();
				Integer thepercent = 0;
				for (Point point : thePoints) {
					if(point.getState() > thepercent){
						thepercent = point.getState();
					}
				}
				return thepercent;
			case "points":
				list = new ArrayList<Map<String, Object>>();
				Set<Point> points = project.getPoints();
				
				for (Point point : points) {
					theMap = new HashMap<String, Object>();
					theMap.put("pointId", point.getPointId());
					theMap.put("name", point.getName());
					theMap.put("date", point.getDate());
					theMap.put("state", point.getState());
					list.add(theMap);
				}
				return list;
			case "user":
				theMap = new HashMap<String, Object>();
				User theUser = project.getUser();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			default:
				return null;
		}
	
	}
	
	public Project getNewProjectByKeys(Project project,Project newProject,String keys){
		if(project == null || newProject == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					project.setName(newProject.getName());
					break;
				case "content":
					project.setContent(newProject.getContent());
					break;
				case "startDate":
					project.setStartDate(newProject.getStartDate());
					break;
				case "endDateA":
					project.setEndDateA(newProject.getEndDateA());
					break;
				case "endDateB":
					project.setEndDateB(newProject.getEndDateB());
					break;
				case "longitude":
					project.setLongitude(newProject.getLongitude());
					break;
				case "latitude":
					project.setLatitude(newProject.getLatitude());
					break;
				case "radius":
					project.setRadius(newProject.getRadius());
					break;
				default:
					
			}
		}
		
		return project;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<Project> projects = projectDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getProjectByKeys(keys,projects);
		if(page != null && pageSize != null){
			total = projectDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Project add(Project project) {
		System.out.println("service....");
		return projectDao.add(project);
		
	}
	

	public Map<String, Object> getProjectByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = projectDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Project project, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			Project project2 = projectDao.getById(integer);
			if(project2 != null){
				if(project2.getUser().getUserId().equals(loginUser.getUserId())){
					project2 = getNewProjectByKeys(project2,project,keys);
					projectDao.update(project2);
				}else{
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
					}
				}
				
			}else{
				map.put("code", 404);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据修改失败;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败;");
				}
			}
		}
		
		map.put("result", theMap);
		return map;
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			Project project2 = projectDao.getById(integer);
			if(project2 != null){
				if(project2.getUser().getUserId().equals(loginUser.getUserId())){
					projectDao.delete(project2);
				}else{
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据删除失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据删除失败:您不具有权限;");
					}
				}
			}else{
				map.put("code", 404);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据删除失败：数据不存在;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据删除失败：数据不存在;");
				}
			}
		}
		
		map.put("result", theMap);
		return map;
	}

	public Map<String, Object> getProjectByUserAndName(String keys,
			Integer page, Integer pageSize, Project project) {
		DetachedCriteria criteria = projectDao.getCriteriaByUserAndName(project);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getProjectByJoinUser(String keys, Integer page,
			Integer pageSize, Project project) {
		DetachedCriteria criteria = projectDao.getCriteriaByJoinUser(project);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Project getById(Integer projectId) {
		return projectDao.getById(projectId);
	}

	public Map<String, Object> getProjectByJoinUserAndName(String keys,
			Integer page, Integer pageSize, Project project) {
		DetachedCriteria criteria = projectDao.getCriteriaByJoinUserAndName(project);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public int getAllProjectNumber() {
		// TODO 自动生成的方法存根
		return projectDao.getAllCountByCriteria(projectDao.getCriteriaByIds(null));
	}

	public int getAllFinishedProjectNumber() {
		// TODO 自动生成的方法存根
		Project project = new Project();
		project.setEndDateB(null);
		return projectDao.getAllCountByCriteria(projectDao.getCriteriaByEndDateBNot(project));
	}

	public int getProjectNumberByStartDateBetween(Date startDate, Date endDate) {
		// TODO 自动生成的方法存根
		return projectDao.getAllCountByCriteria(projectDao.getCriteriaByStartDateBetween(startDate,endDate));
	}

	public int getProjectNumberByEndDateBBetween(Date startDate, Date endDate) {
		// TODO 自动生成的方法存根
		return projectDao.getAllCountByCriteria(projectDao.getCriteriaByEndDateBBetween(startDate,endDate));
	}




}
