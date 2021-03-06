package com.epm.gdsa.project;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epm.beans.ResponseBean;
import com.epm.enums.ProRoleAuthEnum;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.gdsa.user.UserService;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="projectAction")
@Scope(value="prototype")
public class ProjectAction extends ActionSupport implements ModelDriven<Project>{
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private Project project = new Project();
	
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

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}


	@Override
	public Project getModel() {
		if(project == null){
			project = new Project();	
		}
		return project;
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


	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}
	
	public void add(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			loginUser = userService.getById(loginUser.getUserId());
			if(loginUser.getType() < 1){
				responseBean.setStatus(401);
				responseBean.put("error", "您不具有权限");
			}else{
				project.setUser(loginUser);
				project = projectService.add(project);
				if(project.getProjectId() != null) {
					responseBean.setStatus(201);
					responseBean.put("projectId", project.getProjectId());
				} else {
					responseBean.put("error", "添加失败，系统错误");
					responseBean.setStatus(500);
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
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			if(idsIntegers == null || idsIntegers.length == 0){
				idsIntegers = new Integer[1];
				idsIntegers[0] = loginUser.getUserId();
			}
			Map<String, Object> map = projectService.getProjectByIds(keys,page,pageSize,idsIntegers,loginUser);
			responseBean.setObjMap(map);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getByUserAndName(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(project.getUser().getUserId() == 0 || project.getUser().getUserId() == null){
				project.setUser(loginUser);
			}
			Map<String, Object> map = projectService.getProjectByUserAndName(keys,page,pageSize,project,loginUser);
			responseBean.setObjMap(map);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getByJoinUserAndName(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(project.getUser().getUserId() == 0 || project.getUser().getUserId() == null){
				project.setUser(loginUser);
			}
			Map<String, Object> map = projectService.getProjectByJoinUserAndName(keys,page,pageSize,project,loginUser);
			responseBean.setObjMap(map);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getByJoinUser(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(project.getUser().getUserId() == 0 || project.getUser().getUserId() == null){
				project.setUser(loginUser);
			}
			Map<String, Object> map = projectService.getProjectByJoinUser(keys,page,pageSize,project,loginUser);
			responseBean.setObjMap(map);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void updateByIds(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = projectService.updateByIds(keys,idsIntegers,project,user2);
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
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = projectService.deleteByIds(idsIntegers,user2);
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
	
	public void changeSignCode(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			if(project.getProjectId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "无项目");
			}else{
				Project theProject = projectService.getById(project.getProjectId());
				if(theProject == null){
					responseBean.setStatus(404);
					responseBean.put("error", "项目不存在");
				}else{
					if(userService.isHaveTheAuthInTheProject(theProject, ProRoleAuthEnum.SIGN_CODE, loginUser)){
						String signCode = System.currentTimeMillis() + "_" 
								+ theProject.getProjectId() + "_"
								+loginUser.getUserId() + "_"
								+PublicUtils.getRandom(0, 1000);
						theProject.setSignCode(signCode);
						projectService.update(theProject);
						responseBean.setStatus(201);
						responseBean.put("signCode",signCode);
					}else{
						responseBean.setStatus(401);
						responseBean.put("error", "您不具有权限");
					}
				}
				
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
