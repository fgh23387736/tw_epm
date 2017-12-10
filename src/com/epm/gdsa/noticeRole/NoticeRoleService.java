package com.epm.gdsa.noticeRole;

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
import com.epm.gdsa.notice.Notice;
import com.epm.gdsa.noticeRole.NoticeRole;
import com.epm.gdsa.noticeRole.NoticeRoleDao;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.gdsa.noticeRole.NoticeRole;

@Transactional
@Component(value="noticeRoleService")
public class NoticeRoleService {
	
	@Autowired
	private NoticeRoleDao noticeRoleDao;

	public NoticeRoleDao getNoticeRoleDao() {
		return noticeRoleDao;
	}

	public void setNoticeRoleDao(NoticeRoleDao noticeRoleDao) {
		this.noticeRoleDao = noticeRoleDao;
	}
	
	public List<Map<String, Object>> getNoticeRoleByKeys(String keys,List<NoticeRole> noticeRoles){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"noticeRoleId",
					"proRole",
					"notice"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (NoticeRole noticeRole : noticeRoles) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(noticeRole,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(NoticeRole noticeRole,String str){
		Map<String, Object> theMap;
		switch (str) {
			case "noticeRoleId":
				return noticeRole.getNoticeRoleId();
			case "notice":
				theMap = new HashMap<String, Object>();
				Notice theNotice = noticeRole.getNotice();
				if(theNotice == null){
					return theMap;
				}
				theMap.put("noticeId", theNotice.getNoticeId());
				theMap.put("name", theNotice.getName());
				theMap.put("content", theNotice.getContent());
				return theMap;
			case "proRole":
				theMap = new HashMap<String, Object>();
				ProRole theproRole = noticeRole.getProRole();
				if(theproRole == null){
					return theMap;
				}
				theMap.put("proRoleId", theproRole.getProRoleId());
				theMap.put("name", theproRole.getName());
				theMap.put("auth", theproRole.getAuth());
				return theMap;
			default:
				return null;
		}
	}
	
	public NoticeRole getNewNoticeRoleByKeys(NoticeRole noticeRole,NoticeRole newNoticeRole,String keys){
		if(noticeRole == null || newNoticeRole == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "proRole":
					noticeRole.setProRole(newNoticeRole.getProRole());
					break;
				default:
					
			}
		}
		
		return noticeRole;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<NoticeRole> noticeRoles = noticeRoleDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getNoticeRoleByKeys(keys,noticeRoles);
		if(page != null && pageSize != null){
			total = noticeRoleDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public NoticeRole add(NoticeRole noticeRole) {
		return noticeRoleDao.add(noticeRole);
		
	}
	

	public Map<String, Object> getNoticeRoleByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = noticeRoleDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			NoticeRole noticeRole, User user2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			NoticeRole noticeRole2 = noticeRoleDao.getById(integer);
			noticeRole2 = getNewNoticeRoleByKeys(noticeRole2,noticeRole,keys);
			if(noticeRole2 != null){
				noticeRoleDao.update(noticeRole2);
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
			NoticeRole noticeRole2 = noticeRoleDao.getById(integer);
			if(noticeRole2 != null){
				noticeRoleDao.delete(noticeRole2);
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





	public Map<String, Object> getByNotice(String keys, Integer page,
			Integer pageSize, NoticeRole noticeRole, String userName) {
		DetachedCriteria criteria = noticeRoleDao.getCriteriaByNotice(noticeRole);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getByNoticeAndProRole(String keys, Integer page,
			Integer pageSize, NoticeRole noticeRole) {
		DetachedCriteria criteria = noticeRoleDao.getCriteriaByNoticeAndProRole(noticeRole);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getByProRole(String keys, Integer page,
			Integer pageSize, NoticeRole noticeRole, String userName) {
		DetachedCriteria criteria = noticeRoleDao.getCriteriaByProRole(noticeRole);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}




}
