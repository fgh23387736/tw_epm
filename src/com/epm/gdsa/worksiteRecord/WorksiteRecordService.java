package com.epm.gdsa.worksiteRecord;

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

@Transactional
@Component(value="worksiteRecordService")
public class WorksiteRecordService {
	
	@Autowired
	private WorksiteRecordDao worksiteRecordDao;

	public WorksiteRecordDao getWorksiteRecordDao() {
		return worksiteRecordDao;
	}

	public void setWorksiteRecordDao(WorksiteRecordDao worksiteRecordDao) {
		this.worksiteRecordDao = worksiteRecordDao;
	}
	
	public List<Map<String, Object>> getWorksiteRecordByKeys(String keys,List<WorksiteRecord> worksiteRecords){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"worksiteRecordId",
					"size",
					"date",
					"thumbnail",
					"position",
					"project",
					"user",
					"remarks"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (WorksiteRecord worksiteRecord : worksiteRecords) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(worksiteRecord,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(WorksiteRecord worksiteRecord,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "worksiteRecordId":
				return worksiteRecord.getWorksiteRecordId();
			case "date":
				return worksiteRecord.getDate();
			case "size":
				return worksiteRecord.getSize();
			case "thumbnail":
				return worksiteRecord.getThumbnail();
			case "position":
				return worksiteRecord.getPosition();
			case "remarks":
				return worksiteRecord.getRemarks();
			case "user":
				theMap = new HashMap<String, Object>();
				User theUser = worksiteRecord.getUser();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "project":
				theMap = new HashMap<String, Object>();
				Project theProject = worksiteRecord.getProject();
				theMap.put("projectId", theProject.getProjectId());
				theMap.put("name", theProject.getName());
				return theMap;
			default:
				return null;
		}
	}
	
	public WorksiteRecord getNewWorksiteRecordByKeys(WorksiteRecord worksiteRecord,WorksiteRecord newWorksiteRecord,String keys){
		if(worksiteRecord == null || newWorksiteRecord == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "remarks":
					worksiteRecord.setRemarks(newWorksiteRecord.getRemarks());
					break;
				default:
					
			}
		}
		
		return worksiteRecord;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<WorksiteRecord> worksiteRecords = worksiteRecordDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getWorksiteRecordByKeys(keys,worksiteRecords);
		if(page != null && pageSize != null){
			total = worksiteRecordDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public WorksiteRecord add(WorksiteRecord worksiteRecord) {
		return worksiteRecordDao.add(worksiteRecord);
		
	}
	

	public Map<String, Object> getWorksiteRecordByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		System.out.println("this 0");
		DetachedCriteria criteria = worksiteRecordDao.getCriteriaByIds(ids);
		System.out.println("this 1");
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		System.out.println(map.size());
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			WorksiteRecord worksiteRecord, User user2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			WorksiteRecord worksiteRecord2 = worksiteRecordDao.getById(integer);
			worksiteRecord2 = getNewWorksiteRecordByKeys(worksiteRecord2,worksiteRecord,keys);
			if(worksiteRecord2 != null){
				worksiteRecordDao.update(worksiteRecord2);
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
			WorksiteRecord worksiteRecord2 = worksiteRecordDao.getById(integer);
			if(worksiteRecord2 != null){
				worksiteRecordDao.delete(worksiteRecord2);
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
			Integer pageSize, WorksiteRecord worksiteRecord) {
		DetachedCriteria criteria = worksiteRecordDao.getCriteriaByProject(worksiteRecord);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}	
	





}
