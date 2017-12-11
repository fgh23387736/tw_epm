package com.epm.gdsa.userPro;

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
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.userPro.UserProDao;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;

@Transactional
@Component(value="userProService")
public class UserProService {
	
	@Autowired
	private UserProDao userProDao;

	public UserProDao getUserProDao() {
		return userProDao;
	}

	public void setUserProDao(UserProDao userProDao) {
		this.userProDao = userProDao;
	}
	
	public List<Map<String, Object>> getUserProByKeys(String keys,List<UserPro> userPros){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"userProId",
					"proRole",
					"project",
					"user"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (UserPro userPro : userPros) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(userPro,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(UserPro userPro,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "userProId":
				return userPro.getUserProId();
			case "project":
				theMap = new HashMap<String, Object>();
				Project theProject = userPro.getProject();
				if(theProject == null){
					return theMap;
				}
				theMap.put("projectId", theProject.getProjectId());
				theMap.put("name", theProject.getName());
				return theMap;
			case "proRole":
				theMap = new HashMap<String, Object>();
				ProRole theproRole = userPro.getProRole();
				if(theproRole == null){
					return theMap;
				}
				theMap.put("proRoleId", theproRole.getProRoleId());
				theMap.put("name", theproRole.getName());
				return theMap;
			case "user":
				theMap = new HashMap<String, Object>();
				User theUser = userPro.getUser();
				if(theUser == null){
					return theMap;
				}
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				theMap.put("tel", theUser.getTel());
				return theMap;
			default:
				return null;
		}
	}
	
	public UserPro getNewUserProByKeys(UserPro userPro,UserPro newUserPro,String keys){
		if(userPro == null || newUserPro == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "proRole":
					userPro.setProRole(newUserPro.getProRole());
					break;
				default:
					
			}
		}
		
		return userPro;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<UserPro> userPros = userProDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getUserProByKeys(keys,userPros);
		if(page != null && pageSize != null){
			total = userProDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public UserPro add(UserPro userPro) {
		return userProDao.add(userPro);
		
	}
	

	public Map<String, Object> getUserProByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = userProDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			UserPro userPro, User user2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			UserPro userPro2 = userProDao.getById(integer);
			userPro2 = getNewUserProByKeys(userPro2,userPro,keys);
			if(userPro2 != null){
				userProDao.update(userPro2);
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
			UserPro userPro2 = userProDao.getById(integer);
			if(userPro2 != null){
				userProDao.delete(userPro2);
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



	public Map<String, Object> getByProjectAndUserName(String keys,
			Integer page, Integer pageSize, UserPro userPro, String userName) {
		DetachedCriteria criteria = userProDao.getCriteriaByProjectAndUserName(userPro,userName);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}	
	
	public Map<String, Object> getByProjectAndUser(String keys,
			Integer page, Integer pageSize, UserPro userPro) {
		DetachedCriteria criteria = userProDao.getCriteriaByProjectAndUser(userPro);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}
	
	//直接获取所有的实体
	public List<UserPro> getByProjectAndUser(Project project,User user) {
		UserPro userPro = new UserPro();
		userPro.setProject(project);
		userPro.setUser(user);
		DetachedCriteria criteria = userProDao.getCriteriaByProjectAndUser(userPro);
		return userProDao.getDataByCriteria(null, null, criteria);
	}


}
