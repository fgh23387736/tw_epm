package com.epm.gdsa.pointAnswer;

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
import com.epm.gdsa.pointAnswer.PointAnswer;
import com.epm.gdsa.pointProblem.PointProblem;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;

@Transactional
@Component(value="pointAnswerService")
public class PointAnswerService {
	
	@Autowired
	private PointAnswerDao pointAnswerDao;

	public PointAnswerDao getPointAnswerDao() {
		return pointAnswerDao;
	}

	public void setPointAnswerDao(PointAnswerDao pointAnswerDao) {
		this.pointAnswerDao = pointAnswerDao;
	}
	
	public List<Map<String, Object>> getPointAnswerByKeys(String keys,List<PointAnswer> pointAnswers){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"pointAnswerId",
					"answer",
					"pointProblem",
					"user",
					"date"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (PointAnswer pointAnswer : pointAnswers) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(pointAnswer,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(PointAnswer pointAnswer,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		Map<String,Object> tempMap;
		switch (str) {
			case "pointAnswerId":
				return pointAnswer.getPointAnswerId();
			case "date":
				return pointAnswer.getDate();
			case "answer":
				return pointAnswer.getAnswer();
			case "user":
				theMap = new HashMap<String, Object>();
				User theUser = pointAnswer.getUser();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "pointProblem":
				theMap = new HashMap<String, Object>();
				PointProblem thePointProblem = pointAnswer.getPointProblem();
				theMap.put("pointProblemId", thePointProblem.getPointProblemId());
				theMap.put("problem", thePointProblem.getProblem());
				tempMap = new HashMap<String, Object>();
				tempMap.put("userId", thePointProblem.getUser().getUserId());
				tempMap.put("name", thePointProblem.getUser().getName());
				theMap.put("user", tempMap);
				return theMap;
			default:
				return null;
		}
	}
	
	public PointAnswer getNewPointAnswerByKeys(PointAnswer pointAnswer,PointAnswer newPointAnswer,String keys){
		if(pointAnswer == null || newPointAnswer == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "answer":
					pointAnswer.setAnswer(newPointAnswer.getAnswer());
					break;
				default:
					
			}
		}
		
		return pointAnswer;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<PointAnswer> pointAnswers = pointAnswerDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getPointAnswerByKeys(keys,pointAnswers);
		if(page != null && pageSize != null){
			total = pointAnswerDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public PointAnswer add(PointAnswer pointAnswer) {
		return pointAnswerDao.add(pointAnswer);
		
	}
	

	public Map<String, Object> getPointAnswerByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		System.out.println("this 0");
		DetachedCriteria criteria = pointAnswerDao.getCriteriaByIds(ids);
		System.out.println("this 1");
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		System.out.println(map.size());
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			PointAnswer pointAnswer, User user2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			PointAnswer pointAnswer2 = pointAnswerDao.getById(integer);
			pointAnswer2 = getNewPointAnswerByKeys(pointAnswer2,pointAnswer,keys);
			if(pointAnswer2 != null){
				pointAnswerDao.update(pointAnswer2);
			}else{
				map.put("code", 400);
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

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User user2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			PointAnswer pointAnswer2 = pointAnswerDao.getById(integer);
			if(pointAnswer2 != null){
				pointAnswerDao.delete(pointAnswer2);
			}else{
				map.put("code", 400);
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

	public Map<String, Object> getByPointProblem(String keys, Integer page,
			Integer pageSize, PointAnswer pointAnswer) {
		DetachedCriteria criteria = pointAnswerDao.getCriteriaByPointProblem(pointAnswer);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
