package com.epm.gdsa.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.enums.ProRoleAuthEnum;
import com.epm.gdsa.point.Point;
import com.epm.gdsa.proRole.ProRoleService;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.project.ProjectDao;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.userPro.UserProService;
import com.epm.utils.PublicUtils;




@Transactional
@Component(value="userService")
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private UserProService userProService;
	
	@Autowired
	private ProRoleService proRoleService;
	
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

	public User getById(Integer userId) {
		// TODO 自动生成的方法存根
		return userDao.getById(userId);
	}

	public int getAllUserNumber() {
		// TODO 自动生成的方法存根
		User user = new User();
		user.setLoginName("");
		user.setName("");
		return userDao.getAllCountByCriteria(userDao.getCriteriaByNameAndLoginName(user));
	}

	public int getAllAdminNumber() {
		// TODO 自动生成的方法存根
		User user = new User();
		user.setType(1);
		return userDao.getAllCountByCriteria(userDao.getCriteriaByMinType(user));
	}
	
	public boolean isHaveTheAuthInTheProject(Project project,ProRoleAuthEnum proRoleAuthEnum,User user){
		if(project == null){
			return false;
		}else{
			project = projectDao.getById(project.getProjectId());
			if(project == null){
				return false;
			}
			boolean isHaveTheAuth = false;
			List<UserPro> theUserProList = userProService.getByProjectAndUser(project,user);
			if(theUserProList.size() > 0 ){
				for (UserPro userPro : theUserProList) {
					if(proRoleService.isHaveTheAuth(userPro.getProRole(), ProRoleAuthEnum.SPECIFICATION)){
						isHaveTheAuth = true;
						break;
					}
				}
			}
			if(user.getUserId() == project.getUser().getUserId()){
				isHaveTheAuth = true;
			}
			return isHaveTheAuth;
		}
		
	}
}
