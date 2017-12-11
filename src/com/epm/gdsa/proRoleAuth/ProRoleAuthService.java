package com.epm.gdsa.proRoleAuth;

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
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.proRoleAuth.ProRoleAuth;
import com.epm.gdsa.proRoleAuth.ProRoleAuthDao;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;

@Transactional
@Component(value="proRoleAuthService")
public class ProRoleAuthService {
	
	@Autowired
	private ProRoleAuthDao proRoleAuthDao;

	public ProRoleAuthDao getProRoleAuthDao() {
		return proRoleAuthDao;
	}

	public void setProRoleAuthDao(ProRoleAuthDao proRoleAuthDao) {
		this.proRoleAuthDao = proRoleAuthDao;
	}
	
	public List<Map<String, Object>> getProRoleAuthByKeys(String keys,List<ProRoleAuth> proRoleAuths){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"proRoleAuthId",
					"proRole",
					"auth"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (ProRoleAuth proRoleAuth : proRoleAuths) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(proRoleAuth,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(ProRoleAuth proRoleAuth,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "proRoleAuthId":
				return proRoleAuth.getProRoleAuthId();
			case "auth":
				return proRoleAuth.getAuth();
			case "proRole":
				theMap = new HashMap<String, Object>();
				ProRole theProRole = proRoleAuth.getProRole();
				theMap.put("proRoleId", theProRole.getProRoleId());
				theMap.put("name", theProRole.getName());
				return theMap;
			default:
				return null;
		}
	}
	
	public ProRoleAuth getNewProRoleAuthByKeys(ProRoleAuth proRoleAuth,ProRoleAuth newProRoleAuth,String keys){
		if(proRoleAuth == null || newProRoleAuth == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "auth":
					proRoleAuth.setAuth(newProRoleAuth.getAuth());;
					break;
				default:
					
			}
		}
		
		return proRoleAuth;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<ProRoleAuth> proRoleAuths = proRoleAuthDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getProRoleAuthByKeys(keys,proRoleAuths);
		if(page != null && pageSize != null){
			total = proRoleAuthDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public ProRoleAuth add(ProRoleAuth proRoleAuth) {
		return proRoleAuthDao.add(proRoleAuth);
		
	}
	

	public Map<String, Object> getProRoleAuthByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = proRoleAuthDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			ProRoleAuth proRoleAuth, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			ProRoleAuth proRoleAuth2 = proRoleAuthDao.getById(integer);
			if(proRoleAuth2 != null){
				Project theProject = proRoleAuth2.getProRole().getProject();
				if(theProject == null){
					map.put("code", 400);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(!theProject.getUser().getUserId().equals(loginUser.getUserId())){
					map.put("code", 400);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
					}
				}else{
					proRoleAuth2 = getNewProRoleAuthByKeys(proRoleAuth2,proRoleAuth,keys);
					proRoleAuthDao.update(proRoleAuth2);
				}
				
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
			ProRoleAuth proRoleAuth2 = proRoleAuthDao.getById(integer);
			if(proRoleAuth2 != null){
				Project theProject = proRoleAuth2.getProRole().getProject();
				if(theProject == null){
					map.put("code", 400);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(!theProject.getUser().getUserId().equals(loginUser.getUserId())){
					map.put("code", 400);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
					}
				}else{
					proRoleAuthDao.delete(proRoleAuth2);
				}
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


	public Map<String, Object> getByProRole(String keys, Integer page,
			Integer pageSize, ProRoleAuth proRoleAuth) {
		DetachedCriteria criteria = proRoleAuthDao.getCriteriaByProRole(proRoleAuth);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}	
	





}
