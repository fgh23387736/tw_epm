package com.epm.gdsa.notice;

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
@Component(value="noticeService")
public class NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;

	public NoticeDao getNoticeDao() {
		return noticeDao;
	}

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	
	public List<Map<String, Object>> getNoticeByKeys(String keys,List<Notice> notices){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"noticeId",
					"date",
					"project",
					"content",
					"name",
					"user"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Notice notice : notices) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(notice,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Notice notice,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "noticeId":
				return notice.getNoticeId();
			case "date":
				return notice.getDate();
			case "content":
				return notice.getContent();
			case "name":
				return notice.getName();
			case "user":
				theMap = new HashMap<String, Object>();
				User theUser = notice.getUser();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "project":
				theMap = new HashMap<String, Object>();
				Project theProject = notice.getProject();
				theMap.put("projectId", theProject.getProjectId());
				theMap.put("name", theProject.getName());
				return theMap;
			default:
				return null;
		}
	}
	
	public Notice getNewNoticeByKeys(Notice notice,Notice newNotice,String keys){
		if(notice == null || newNotice == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "content":
					notice.setContent(newNotice.getContent());
					break;
				case "name":
					notice.setName(newNotice.getName());
					break;
				default:
					
			}
		}
		
		return notice;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<Notice> notices = noticeDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getNoticeByKeys(keys,notices);
		if(page != null && pageSize != null){
			total = noticeDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Notice add(Notice notice) {
		return noticeDao.add(notice);
		
	}
	

	public Map<String, Object> getNoticeByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = noticeDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Notice notice, User user2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			Notice notice2 = noticeDao.getById(integer);
			notice2 = getNewNoticeByKeys(notice2,notice,keys);
			if(notice2 != null && notice2.getUser().getUserId() == user2.getUserId()){
				noticeDao.update(notice2);
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
			Notice notice2 = noticeDao.getById(integer);
			if(notice2 != null){
				noticeDao.delete(notice2);
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
			Integer pageSize, Notice notice) {
		DetachedCriteria criteria = noticeDao.getCriteriaByProject(notice);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}


	public Notice getById(Integer noticeId) {
		// TODO 自动生成的方法存根
		return noticeDao.getById(noticeId);
	}

	public Map<String, Object> getByUser(String keys, Integer page,
			Integer pageSize, Notice notice) {
		DetachedCriteria criteria = noticeDao.getCriteriaByUser(notice);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getByProjectAndName(String keys, Integer page,
			Integer pageSize, Notice notice) {
		DetachedCriteria criteria = noticeDao.getCriteriaByProjectAndName(notice);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}	
	





}
