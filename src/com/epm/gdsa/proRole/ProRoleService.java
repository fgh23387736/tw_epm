package com.epm.gdsa.proRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.enums.ProRoleAuthEnum;
import com.epm.gdsa.point.Point;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.proRole.ProRoleDao;
import com.epm.gdsa.proRoleAuth.ProRoleAuth;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;

@Transactional
@Component(value="proRoleService")
public class ProRoleService {
	
	@Autowired
	private ProRoleDao proRoleDao;

	public ProRoleDao getProRoleDao() {
		return proRoleDao;
	}

	public void setProRoleDao(ProRoleDao proRoleDao) {
		this.proRoleDao = proRoleDao;
	}
	
	public List<Map<String, Object>> getProRoleByKeys(String keys,List<ProRole> proRoles){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"proRoleId",
					"name",
					"auth",
					"project",
					"users"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (ProRole proRole : proRoles) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(proRole,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(ProRole proRole,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "proRoleId":
				return proRole.getProRoleId();
			case "name":
				return proRole.getName();
			case "project":
				theMap = new HashMap<String, Object>();
				Project theProject = proRole.getProject();
				theMap.put("projectId", theProject.getProjectId());
				theMap.put("name", theProject.getName());
				return theMap;
			case "users":
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Set<UserPro> userPros = proRole.getUserPros();
				User tempUser;
				for (UserPro userPro : userPros) {
					tempUser = userPro.getUser();
					theMap = new HashMap<String, Object>();
					theMap.put("userId", tempUser.getUserId());
					theMap.put("name", tempUser.getName());
					theMap.put("tel", tempUser.getTel());
					list.add(theMap);
				}
				return list;
			default:
				return null;
		}
	}
	
	public ProRole getNewProRoleByKeys(ProRole proRole,ProRole newProRole,String keys){
		if(proRole == null || newProRole == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					proRole.setName(newProRole.getName());
					break;
				default:
					
			}
		}
		
		return proRole;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<ProRole> proRoles = proRoleDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getProRoleByKeys(keys,proRoles);
		if(page != null && pageSize != null){
			total = proRoleDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public ProRole add(ProRole proRole) {
		return proRoleDao.add(proRole);
		
	}
	

	public Map<String, Object> getProRoleByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = proRoleDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			ProRole proRole, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			ProRole proRole2 = proRoleDao.getById(integer);
			if(proRole2 != null){
				Project theProject = proRole2.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(!theProject.getUser().getUserId().equals(loginUser.getUserId())){
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
					}
				}else{
					proRole2 = getNewProRoleByKeys(proRole2,proRole,keys);
					proRoleDao.update(proRole2);
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
			ProRole proRole2 = proRoleDao.getById(integer);
			if(proRole2 != null){
				Project theProject = proRole2.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(!theProject.getUser().getUserId().equals(loginUser.getUserId())){
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
					}
				}else{
					proRoleDao.delete(proRole2);
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


	public Map<String, Object> getByProjectAndName(String keys, Integer page,
			Integer pageSize, ProRole proRole) {
		DetachedCriteria criteria = proRoleDao.getCriteriaByProjectAndName(proRole);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public ProRole getById(Integer proRoleId) {
		return proRoleDao.getById(proRoleId);
	}	
	

	public boolean isHaveTheAuth(ProRole proRole,ProRoleAuthEnum proRoleAuthEnum){
		if(proRole == null){
			return false;
		}else if(proRole.getProRoleId() == null){
			return false;
		}else if(proRoleAuthEnum == null){
			return false;
		}else{
			proRole = proRoleDao.getById(proRole.getProRoleId());
			Set<ProRoleAuth> proRoleAuths = proRole.getProRoleAuths();
			for (ProRoleAuth proRoleAuth : proRoleAuths) {
				if(proRoleAuth.getAuth() == proRoleAuthEnum){
					return true;
				}
			}
			return false;
		}
		
	}



}
