package com.epm.gdsa.userPro;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epm.beans.ResponseBean;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.proRole.ProRoleService;
import com.epm.gdsa.proRoleAuth.ProRoleAuth;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.project.ProjectService;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.user.User;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="userProAction")
@Scope(value="prototype")
public class UserProAction extends ActionSupport implements ModelDriven<UserPro>{
	
	@Autowired
	private UserProService userProService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProRoleService proRoleService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private UserPro userPro = new UserPro();
	
	private String userName;
	
	
	
	public ProRoleService getProRoleService() {
		return proRoleService;
	}


	public void setProRoleService(ProRoleService proRoleService) {
		this.proRoleService = proRoleService;
	}


	public ProjectService getProjectService() {
		return projectService;
	}


	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		
		return NONE;
	}
	
	
	public UserProService getUserProService() {
		return userProService;
	}

	public void setUserProService(UserProService userProService) {
		this.userProService = userProService;
	}


	@Override
	public UserPro getModel() {
		if(userPro == null){
			userPro = new UserPro();	
		}
		return userPro;
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


	public String getKeys() {
		return keys;
	}


	public void setKeys(String keys) {
		this.keys = keys;
	}


	public UserPro getUserPro() {
		return userPro;
	}


	public void setUserPro(UserPro userPro) {
		this.userPro = userPro;
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = userProService.getUserProByIds(keys,page,pageSize,idsIntegers);
			responseBean.setObjMap(map);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getByProjectAndUserName(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(userPro.getProject() == null || userPro.getProject().getProjectId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				Map<String, Object> map = userProService.getByProjectAndUserName(keys,page,pageSize,userPro,userName);
				responseBean.setObjMap(map);
			}
			
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void add(){
		ResponseBean responseBean =new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(userPro.getProject() == null || userPro.getProject().getProjectId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				Project theProject = projectService.getById(userPro.getProject().getProjectId());
				if(theProject == null){
					responseBean.setStatus(404);
					responseBean.put("error", "项目不存在");
				}else if(!theProject.getUser().getUserId().equals(loginUser.getUserId())){
					responseBean.setStatus(401);
					responseBean.put("error", "您不具有权限");
				}else{
					Map<String, Object> map = userProService.getByProjectAndUser(keys, page, pageSize, userPro);
					if((int)map.get("total") != 0){
						responseBean.put("error", "该人员已存在，无法重复添加");
						responseBean.setStatus(403);
					}else{
						ProRole theProRole = proRoleService.getById(userPro.getProRole().getProRoleId());
						if(theProRole.getProject().getProjectId().equals(userPro.getProject().getProjectId())){
							userPro = userProService.add(userPro);
							if(userPro.getUserProId() != null) {
								responseBean.setStatus(201);
								responseBean.put("userProId",userPro.getUserProId());
							} else {
								responseBean.put("error", "添加失败，系统错误");
								responseBean.setStatus(500);
							}
						}else{
							responseBean.put("error", "该角色不属于该项目");
							responseBean.setStatus(403);
						}
						
					}
					
				}
			}
		}
		
		try {
			responseBean.write(responseBean.getJsonString());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void updateByIds(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = userProService.updateByIds(keys,idsIntegers,userPro,user2);
			responseBean.setStatus((int)map.get("code"));
			responseBean.setObjMap((Map<String, Object>)map.get("result"));
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void deleteByIds(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			System.out.println(ids);
			if(ids == null || ids.equals("")){
				responseBean.setStatus(400);
				responseBean.put("error", "无法确定删除哪条信息");
			}else{
				Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
				Map<String, Object> map = userProService.deleteByIds(idsIntegers,user2);
				responseBean.setStatus((int)map.get("code"));
				responseBean.setObjMap((Map<String, Object>)map.get("result"));
			}
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
