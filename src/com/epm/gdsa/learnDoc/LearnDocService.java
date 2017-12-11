package com.epm.gdsa.learnDoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.utils.PublicUtils;

@Transactional
@Component(value="learnDocService")
public class LearnDocService {
	
	@Autowired
	private LearnDocDao learnDocDao;

	public LearnDocDao getLearnDocDao() {
		return learnDocDao;
	}

	public void setLearnDocDao(LearnDocDao learnDocDao) {
		this.learnDocDao = learnDocDao;
	}
	
	public List<Map<String, Object>> getLearnDocByKeys(String keys,List<LearnDoc> learnDocs){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"learnDocId",
					"content",
					"name",
					"type",
					"project",
					"user"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (LearnDoc learnDoc : learnDocs) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(learnDoc,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(LearnDoc learnDoc,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "learnDocId":
				return learnDoc.getLearnDocId();
			case "content":
				return learnDoc.getContentUrl();
			case "typw":
				return learnDoc.getType();
			case "name":
				return learnDoc.getName();
			case "user":
				theMap = new HashMap<String, Object>();
				User theUser = learnDoc.getUser();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "project":
				theMap = new HashMap<String, Object>();
				Project theProject = learnDoc.getProject();
				theMap.put("projectId", theProject.getProjectId());
				theMap.put("name", theProject.getName());
				return theMap;
			default:
				return null;
		}
	}
	
	public LearnDoc getNewLearnDocByKeys(LearnDoc learnDoc,LearnDoc newLearnDoc,String keys){
		if(learnDoc == null || newLearnDoc == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					learnDoc.setName(newLearnDoc.getName());
					break;
				case "type":
					learnDoc.setType(newLearnDoc.getType());
					break;
				default:
					
			}
		}
		
		return learnDoc;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<LearnDoc> learnDocs = learnDocDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getLearnDocByKeys(keys,learnDocs);
		if(page != null && pageSize != null){
			total = learnDocDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public LearnDoc add(LearnDoc learnDoc) {
		return learnDocDao.add(learnDoc);
		
	}
	

	public Map<String, Object> getLearnDocByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = learnDocDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			LearnDoc learnDoc, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			LearnDoc learnDoc2 = learnDocDao.getById(integer);
			if(learnDoc2 != null){
				Project theProject = learnDoc2.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(theProject.getUser().getUserId().equals(loginUser.getUserId()) || learnDoc2.getUser().getUserId().equals(loginUser.getUserId())){
					//具有权限时的业务逻辑
					learnDoc2 = getNewLearnDocByKeys(learnDoc2,learnDoc,keys);
					learnDocDao.update(learnDoc2);
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
			LearnDoc learnDoc2 = learnDocDao.getById(integer);
			if(learnDoc2 != null){
				Project theProject = learnDoc2.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(theProject.getUser().getUserId().equals(loginUser.getUserId()) || learnDoc2.getUser().getUserId().equals(loginUser.getUserId())){
					//具有权限时的业务逻辑
					if(PublicUtils.deleteFileFromServer(learnDoc2.getContentUrl().replace("/tw_epm", ""))){
						learnDocDao.delete(learnDoc2);
					}else{
						map.put("code", 500);
						if(theMap == null){
							theMap = new HashMap<String, Object>();
							theMap.put("error", "id为"+integer+"的文件删除失败;");
						}else{
							theMap.put("error",theMap.get("error")+"id为"+integer+"的文件删除失败;");
						}	
					}
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

	public Map<String, Object> getByProject(String keys, Integer page,
			Integer pageSize, LearnDoc learnDoc) {
		DetachedCriteria criteria = learnDocDao.getCriteriaByProject(learnDoc);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getByProjectAndName(String keys, Integer page,
			Integer pageSize, LearnDoc learnDoc) {
		DetachedCriteria criteria = learnDocDao.getCriteriaByProjectAndName(learnDoc);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public LearnDoc getById(Integer learnDocId) {
		// TODO 自动生成的方法存根
		return learnDocDao.getById(learnDocId);
	}	
	





}
