package com.epm.gdsa.pointProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gdsa.point.Point;
import com.epm.gdsa.point.PointDao;
import com.epm.gdsa.pointAnswer.PointAnswer;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.utils.PublicUtils;

@Transactional
@Component(value="pointProblemService")
public class PointProblemService {
	
	@Autowired
	private PointProblemDao pointProblemDao;
	
	@Autowired
	private PointDao pointDao;

	public PointProblemDao getPointProblemDao() {
		return pointProblemDao;
	}
	
	public PointDao getPointDao() {
		return pointDao;
	}

	public void setPointDao(PointDao pointDao) {
		this.pointDao = pointDao;
	}

	public void setPointProblemDao(PointProblemDao pointProblemDao) {
		this.pointProblemDao = pointProblemDao;
	}
	
	public List<Map<String, Object>> getPointProblemByKeys(String keys,List<PointProblem> pointProblems){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"pointProblemId",
					"problem",
					"point",
					"user",
					"date",
					"answers"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (PointProblem pointProblem : pointProblems) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(pointProblem,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(PointProblem pointProblem,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
			case "pointProblemId":
				return pointProblem.getPointProblemId();
			case "date":
				return pointProblem.getDate();
			case "problem":
				return pointProblem.getProblem();
			case "user":
				theMap = new HashMap<String, Object>();
				User theUser = pointProblem.getUser();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "point":
				theMap = new HashMap<String, Object>();
				Point thePoint = pointProblem.getPoint();
				theMap.put("pointId", thePoint.getPointId());
				theMap.put("name", thePoint.getName());
				return theMap;
			case "answers":
				list = new ArrayList<Map<String, Object>>();
				Set<PointAnswer> pointAnswers = pointProblem.getPointAnswers();
				for (PointAnswer pointAnswer : pointAnswers) {
					theMap = new HashMap<String, Object>();
					theMap.put("pointAnswerId", pointAnswer.getPointAnswerId());
					theMap.put("answer", pointAnswer.getAnswer());
					theMap.put("date", pointAnswer.getDate());
					Map<String,Object> tempMap = new HashMap<String, Object>();
					tempMap.put("userId", pointAnswer.getUser().getUserId());
					tempMap.put("name", pointAnswer.getUser().getName());
					theMap.put("user", tempMap);
					list.add(theMap);
				}
				return list;
			default:
				return null;
		}
	}
	
	public PointProblem getNewPointProblemByKeys(PointProblem pointProblem,PointProblem newPointProblem,String keys){
		if(pointProblem == null || newPointProblem == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "problem":
					pointProblem.setProblem(newPointProblem.getProblem());;
					break;
				default:
					
			}
		}
		
		return pointProblem;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<PointProblem> pointProblems = pointProblemDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getPointProblemByKeys(keys,pointProblems);
		if(page != null && pageSize != null){
			total = pointProblemDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public PointProblem add(PointProblem pointProblem) {
		return pointProblemDao.add(pointProblem);
		
	}
	

	public Map<String, Object> getPointProblemByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		System.out.println("this 0");
		DetachedCriteria criteria = pointProblemDao.getCriteriaByIds(ids);
		System.out.println("this 1");
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		System.out.println(map.size());
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			PointProblem pointProblem, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			PointProblem pointProblem2 = pointProblemDao.getById(integer);
			if(pointProblem2 != null){
				Point thePoint = pointDao.getById(pointProblem2.getPoint().getPointId());
				Project theProject = thePoint.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(theProject.getUser().getUserId().equals(loginUser.getUserId()) || pointProblem2.getUser().getUserId().equals(loginUser.getUserId())){
					//具有权限时的业务逻辑
					pointProblem2 = getNewPointProblemByKeys(pointProblem2,pointProblem,keys);
					pointProblemDao.update(pointProblem2);
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
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			PointProblem pointProblem2 = pointProblemDao.getById(integer);
			if(pointProblem2 != null){
				Point thePoint = pointDao.getById(pointProblem2.getPoint().getPointId());
				Project theProject = thePoint.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(theProject.getUser().getUserId().equals(loginUser.getUserId()) || pointProblem2.getUser().getUserId().equals(loginUser.getUserId())){
					//具有权限时的业务逻辑
					pointProblemDao.delete(pointProblem2);
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
					theMap.put("error", "id为"+integer+"的数据删除失败：数据不存在;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败：数据不存在;");
				}
			}
		}
		
		map.put("result", theMap);
		return map;
	}

	public Map<String, Object> getByPoint(String keys, Integer page,
			Integer pageSize, PointProblem pointProblem) {
		DetachedCriteria criteria = pointProblemDao.getCriteriaByPoint(pointProblem);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public PointProblem getById(Integer pointProblemId) {
		// TODO 自动生成的方法存根
		return pointProblemDao.getById(pointProblemId);
	}

}
