package com.epm.gdsa.sign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.utils.PublicUtils;

@Transactional
@Component(value="signService")
public class SignService {
	
	@Autowired
	private SignDao signDao;

	public SignDao getSignDao() {
		return signDao;
	}

	public void setSignDao(SignDao signDao) {
		this.signDao = signDao;
	}
	
	public List<Map<String, Object>> getSignByKeys(String keys,List<Sign> signs){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"signId",
					"date",
					"project",
					"user",
					"longitude",
					"latitude"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Sign sign : signs) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(sign,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Sign sign,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "signId":
				return sign.getSignId();
			case "date":
				return sign.getDate();
			case "longitude":
				return sign.getLongitude();
			case "latitude":
				return sign.getLatitude();
			case "user":
				theMap = new HashMap<String, Object>();
				User theUser = sign.getUser();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "project":
				theMap = new HashMap<String, Object>();
				Project theProject = sign.getProject();
				theMap.put("projectId", theProject.getProjectId());
				theMap.put("name", theProject.getName());
				return theMap;
			default:
				return null;
		}
	}
	
	public Sign getNewSignByKeys(Sign sign,Sign newSign,String keys){
		if(sign == null || newSign == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "date":
					sign.setDate(newSign.getDate());
					break;
				case "project":
					sign.setProject(newSign.getProject());
					break;
				default:
					
			}
		}
		
		return sign;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<Sign> signs = signDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getSignByKeys(keys,signs);
		if(page != null && pageSize != null){
			total = signDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Sign add(Sign sign) {
		return signDao.add(sign);
		
	}
	

	public Map<String, Object> getSignByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		System.out.println("this 0");
		DetachedCriteria criteria = signDao.getCriteriaByIds(ids);
		System.out.println("this 1");
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		System.out.println(map.size());
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Sign sign, User user2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			Sign sign2 = signDao.getById(integer);
			sign2 = getNewSignByKeys(sign2,sign,keys);
			if(sign2 != null){
				signDao.update(sign2);
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

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			Sign sign2 = signDao.getById(integer);
			if(sign2 != null){
				signDao.delete(sign2);
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

	public Map<String, Object> getByProject(String keys, Integer page,
			Integer pageSize, Sign sign) {
		DetachedCriteria criteria = signDao.getCriteriaByProject(sign);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}


	public Sign getById(Integer signId) {
		// TODO 自动生成的方法存根
		return signDao.getById(signId);
	}

	public Map<String, Object> getByUser(String keys, Integer page,
			Integer pageSize, Sign sign) {
		DetachedCriteria criteria = signDao.getCriteriaByUser(sign);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}	
	





}
