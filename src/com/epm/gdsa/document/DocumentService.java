package com.epm.gdsa.document;

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
@Component(value="documentService")
public class DocumentService {
	
	@Autowired
	private DocumentDao documentDao;

	public DocumentDao getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}
	
	public List<Map<String, Object>> getDocumentByKeys(String keys,List<Document> documents){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"documentId",
					"content",
					"name",
					"unit",
					"date",
					"project",
					"user"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Document document : documents) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(document,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Document document,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "documentId":
				return document.getDocumentId();
			case "date":
				return document.getDate();
			case "content":
				return document.getContentUrl();
			case "unit":
				return document.getUnit();
			case "name":
				return document.getName();
			case "user":
				theMap = new HashMap<String, Object>();
				User theUser = document.getUser();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "project":
				theMap = new HashMap<String, Object>();
				Project theProject = document.getProject();
				theMap.put("projectId", theProject.getProjectId());
				theMap.put("name", theProject.getName());
				return theMap;
			default:
				return null;
		}
	}
	
	public Document getNewDocumentByKeys(Document document,Document newDocument,String keys){
		if(document == null || newDocument == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					document.setName(newDocument.getName());
					break;
				case "date":
					document.setDate(newDocument.getDate());
					break;
				case "unit":
					document.setUnit(newDocument.getUnit());
					break;
				default:
					
			}
		}
		
		return document;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<Document> documents = documentDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getDocumentByKeys(keys,documents);
		if(page != null && pageSize != null){
			total = documentDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Document add(Document document) {
		return documentDao.add(document);
		
	}
	

	public Map<String, Object> getDocumentByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = documentDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Document document, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			Document document2 = documentDao.getById(integer);
			if(document2 != null){
				Project theProject = document2.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(theProject.getUser().getUserId().equals(loginUser.getUserId()) || document2.getUser().getUserId().equals(loginUser.getUserId())){
					//具有权限时的业务逻辑
					document2 = getNewDocumentByKeys(document2,document,keys);
					documentDao.update(document2);
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
			Document document2 = documentDao.getById(integer);
			if(document2 != null){
				Project theProject = document2.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(theProject.getUser().getUserId().equals(loginUser.getUserId()) || document2.getUser().getUserId().equals(loginUser.getUserId())){
					//具有权限时的业务逻辑
					if(PublicUtils.deleteFileFromServer(document2.getContentUrl().replace("/tw_epm", ""))){
						documentDao.delete(document2);
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
			Integer pageSize, Document document) {
		DetachedCriteria criteria = documentDao.getCriteriaByProject(document);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getByProjectAndName(String keys, Integer page,
			Integer pageSize, Document document) {
		DetachedCriteria criteria = documentDao.getCriteriaByProjectAndName(document);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Document getById(Integer documentId) {
		// TODO 自动生成的方法存根
		return documentDao.getById(documentId);
	}	
	





}
