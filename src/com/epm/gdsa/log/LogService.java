package com.epm.gdsa.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gdsa.log.Log;
import com.epm.gdsa.log.LogDao;
import com.epm.gdsa.user.User;

@Transactional
@Component(value="logService")
public class LogService {
	
	@Autowired
	private LogDao logDao;

	public LogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}
	
	public List<Map<String, Object>> getLogByKeys(String keys,List<Log> logs){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"logId",
					"content",
					"Date"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Log log : logs) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(log,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Log log,String str){
		switch (str) {
			case "logId":
				return log.getLogId();
			case "date":
				return log.getDate();
			case "content":
				return log.getContent();
			default:
				return null;
		}
	}
	
	public Log getNewLogByKeys(Log log,Log newLog,String keys){
		if(log == null || newLog == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "date":
					log.setDate(newLog.getDate());
					break;
				case "content":
					log.setContent(newLog.getContent());
					break;
				default:
					
			}
		}
		
		return log;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<Log> logs = logDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getLogByKeys(keys,logs);
		if(page != null && pageSize != null){
			total = logDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Log add(Log log) {
		return logDao.add(log);
		
	}
	

	public Map<String, Object> getLogByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = logDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Log log, User user2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			Log log2 = logDao.getById(integer);
			log2 = getNewLogByKeys(log2,log,keys);
			if(log2 != null && log2.getUser().getUserId().equals(user2.getUserId())){
				logDao.update(log2);
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
			Log log2 = logDao.getById(integer);
			if(log2 != null){
				logDao.delete(log2);
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
			Integer pageSize, Log log) {
		DetachedCriteria criteria = logDao.getCriteriaByProject(log);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}	
	





}
