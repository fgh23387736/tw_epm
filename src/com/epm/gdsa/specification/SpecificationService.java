package com.epm.gdsa.specification;

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
@Component(value="specificationService")
public class SpecificationService {
	
	@Autowired
	private SpecificationDao specificationDao;

	public SpecificationDao getSpecificationDao() {
		return specificationDao;
	}

	public void setSpecificationDao(SpecificationDao specificationDao) {
		this.specificationDao = specificationDao;
	}
	
	public List<Map<String, Object>> getSpecificationByKeys(String keys,List<Specification> specifications){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"specificationId",
					"content",
					"name",
					"date",
					"project"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Specification specification : specifications) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(specification,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Specification specification,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "specificationId":
				return specification.getSpecificationId();
			case "date":
				return specification.getDate();
			case "content":
				return specification.getContentUrl();
			case "name":
				return specification.getName();
			case "project":
				theMap = new HashMap<String, Object>();
				Project theProject = specification.getProject();
				theMap.put("projectId", theProject.getProjectId());
				theMap.put("name", theProject.getName());
				return theMap;
			default:
				return null;
		}
	}
	
	public Specification getNewSpecificationByKeys(Specification specification,Specification newSpecification,String keys){
		if(specification == null || newSpecification == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					specification.setName(newSpecification.getName());
					break;
				default:
					
			}
		}
		
		return specification;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<Specification> specifications = specificationDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getSpecificationByKeys(keys,specifications);
		if(page != null && pageSize != null){
			total = specificationDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Specification add(Specification specification) {
		return specificationDao.add(specification);
		
	}
	

	public Map<String, Object> getSpecificationByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = specificationDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		System.out.println(map.size());
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Specification specification, User user2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			Specification specification2 = specificationDao.getById(integer);
			specification2 = getNewSpecificationByKeys(specification2,specification,keys);
			if(specification2 != null){
				specificationDao.update(specification2);
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
			Specification specification2 = specificationDao.getById(integer);
			if(specification2 != null){
				if(PublicUtils.deleteFileFromServer(specification2.getContentUrl().replace("/tw_epm", ""))){
					specificationDao.delete(specification2);
				}else{
					map.put("code", 400);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的文件删除失败;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的文件删除失败;");
					}
					
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

	public Map<String, Object> getByProject(String keys, Integer page,
			Integer pageSize, Specification specification) {
		DetachedCriteria criteria = specificationDao.getCriteriaByProject(specification);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getByProjectAndName(String keys, Integer page,
			Integer pageSize, Specification specification) {
		DetachedCriteria criteria = specificationDao.getCriteriaByProjectAndName(specification);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Specification getById(Integer specificationId) {
		// TODO 自动生成的方法存根
		return specificationDao.getById(specificationId);
	}	
	





}
