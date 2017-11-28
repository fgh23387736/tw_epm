package com.epm.gdsa.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gdsa.point.Point;




@Transactional
@Component(value="userService")
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public List<Map<String, Object>> getUserByKeys(String keys,List<User> users){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"userId",
					"loginName",
					"name",
					"type",
					"state",
					"point",
					"tel",
					"email",
					"wechat"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (User user : users) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(user,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(User user,String str){
		switch (str) {
		case "userId":
			return user.getUserId();
		case "loginName":
			return user.getLoginName();
		case "name":
			return user.getName();
		case "type":
			return user.getType();
		case "state":
			return user.getState();
		case "point":
			return user.getPoint();
		case "tel":
			return user.getTel();
		case "email":
			return user.getEmail();
		case "wechat":
			return user.getWechat();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = userDao.getAllCountByCriteria(criteria);
		List<User> users = userDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getUserByKeys(keys,users);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public User add(User user) {
		System.out.println("service....");
		return userDao.add(user);
		
	}
	
	public void updateNameByIds(User user,Integer[] idsIntegers) {
		for (Integer integer : idsIntegers) {
			User user2 = userDao.getById(integer);
			if(user2 != null){
				user2.setName(user.getName());
				userDao.update(user2);
			}
		}
	}

	public Map<String, Object> getUserByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = userDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}	
	
	public List<User> getUserByLoginName(String loginName) {
		List<User> users = userDao.getDataByCriteria(null, null, userDao.getCriteriaByLoginName(loginName));
		//System.out.println(users.get(0).getNotices().size());
		return users;
	}
	
	public Map<String, Object> getUserByNameAndLoginName(String keys,
			Integer page, Integer pageSize, User user) {
		DetachedCriteria criteria = userDao.getCriteriaByNameAndLoginName(user);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public void updatePassword(User user, Integer userId) {
		System.out.println("service");
		User user2 = userDao.getById(userId);
		if(user2 != null){
			System.out.println(user2.getPassword());
			System.out.println(user.getPassword());
			user2.setPassword(user.getPassword());
			userDao.update(user2);
		}else{
			System.out.println("不存在");
		}
		
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			User user, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 200);
		for (Integer integer : idsIntegers) {
			User user3 = userDao.getById(integer);
			user3 = getNewUserByKeys(user3,user,keys);
			if(user3 != null){
				userDao.update(user3);
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

	private User getNewUserByKeys(User user, User newUser, String keys) {
		if(user == null || newUser == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					user.setName(newUser.getName());
					break;
				case "tel":
					user.setTel(newUser.getTel());
					break;
				case "email":
					user.setEmail(newUser.getEmail());
					break;
				case "wechat":
					user.setWechat(newUser.getWechat());
					break;
				case "type":
					user.setType(newUser.getType());
					break;
				case "state":
					user.setState(newUser.getState());
					break;
				case "point":
					user.setPoint(newUser.getPoint());
					break;
				default:
					
			}
		}
		
		return user;
	}
	
}
