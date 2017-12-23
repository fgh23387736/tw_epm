package com.epm.gdsa.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.epm.beans.ResponseBean;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="userAction")
@Scope(value="prototype")
public class UserAction extends ActionSupport implements ModelDriven<User>{
	
	@Autowired
	private UserService userService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private User user = new User();
	
	
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public User getModel() {
		if(user == null){
			user = new User();	
		}
		return user;
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			if(idsIntegers == null || idsIntegers.length == 0){
				idsIntegers = new Integer[1];
				idsIntegers[0] = user2.getUserId();
			}
			Map<String, Object> map = userService.getUserByIds(keys,page,pageSize,idsIntegers);
			
			responseBean.setObjMap(map);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	public void updateByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			if(idsIntegers == null || idsIntegers.length == 0){
				idsIntegers = new Integer[1];
				idsIntegers[0] = loginUser.getUserId();
			}
			Map<String, Object> map = userService.updateByIds(keys,idsIntegers,user,loginUser);
			responseBean.setStatus((int)map.get("code"));
			responseBean.setObjMap((Map<String, Object>)map.get("result"));
			if(idsIntegers[0] == loginUser.getUserId()){
				ServletActionContext.getRequest().getSession().setAttribute("user", userService.getById(loginUser.getUserId()));
			}
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void changeNameByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			if(idsIntegers == null || idsIntegers.length == 0){
				idsIntegers = new Integer[1];
				idsIntegers[0] = loginUser.getUserId();
			}
			userService.updateNameByIds(user,idsIntegers);
			if(idsIntegers[0] == loginUser.getUserId()){
				ServletActionContext.getRequest().getSession().setAttribute("user", userService.getById(loginUser.getUserId()));
			}
			responseBean.setStatus(200);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	}
	
	public void changePassword(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(!user.getRePassword().equals(user.getNewPassword())){
				responseBean.setStatus(400);
				responseBean.put("error", "两次新密码输入不一致");
			}else if(!PublicUtils.getMD5(user.getPassword()).equals(user2.getPassword())){
				responseBean.setStatus(400);
				responseBean.put("error", "原密码错误");
			}else{
				user.setPassword(PublicUtils.getMD5(user.getNewPassword()));
				userService.updatePassword(user,user2.getUserId());
				responseBean.setStatus(200);
				this.signOut();
			}
			
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	}
	
	public void login(){
		ResponseBean response =new ResponseBean();
		List<User> userList = userService.getUserByLoginName(user.getLoginName());
		if(userList != null && userList.size() > 0){
			User theuser = userList.get(0);
			if(theuser != null){
				if(PublicUtils.getMD5(user.getPassword()).equals(theuser.getPassword())){
					response.setStatus(200);
					response.put("success", "登陆成功");
					ServletActionContext.getRequest().getSession().setAttribute("user", theuser);
				}else{
					response.put("error", "密码错误");
					response.setStatus(401);
				}
			}else{
				response.put("error", "用户不存在");
				response.setStatus(401);
			}
		}else{
			response.put("error", "用户不存在");
			response.setStatus(401);
		}
		try {
			response.write(response.getJsonString());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public String signOut(){
		ServletActionContext.getRequest().getSession().setAttribute("user", null);	
		return "signOut";
	}
	
	public void register(){
		ResponseBean response =new ResponseBean();
		
		if(!user.getPassword().equals(user.getRePassword())){
			response.put("error", "两次输入密码不一致");
			response.setStatus(401);
		}else{
			List<User> userList = userService.getUserByLoginName(user.getLoginName());
			if(userList != null && userList.size() > 0){
				response.put("error", "用户名已存在");
				response.setStatus(401);
			}else{
				user.setPassword(PublicUtils.getMD5(user.getPassword()));
				user.setName("无名氏");
				user.setState(0);
				user = userService.add(user);
				if(user.getUserId() != null) {
					response.setStatus(200);
					response.put("success", "注册成功");
					//ActionContext.getContext().getSession().put("user ",user );
					ServletActionContext.getRequest().getSession().setAttribute("user", user);
				} else {
					response.put("error", "添加失败，系统错误");
					response.setStatus(500);
				}
				
			}
		}
		
		
		try {
			response.write(response.getJsonString());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	
	public void getByNameAndLoginName(){	
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Map<String, Object> map = userService.getUserByNameAndLoginName(keys,page,pageSize,user);
			responseBean.setObjMap(map);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
}
