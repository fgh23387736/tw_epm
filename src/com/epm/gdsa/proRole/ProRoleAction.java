package com.epm.gdsa.proRole;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epm.beans.ResponseBean;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.project.ProjectService;
import com.epm.gdsa.user.User;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="proRoleAction")
@Scope(value="prototype")
public class ProRoleAction extends ActionSupport implements ModelDriven<ProRole>{
	
	@Autowired
	private ProRoleService proRoleService;
	
	@Autowired
	private ProjectService projectService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private ProRole proRole = new ProRole();
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		
		return NONE;
	}
	
	
	public ProjectService getProjectService() {
		return projectService;
	}


	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}


	public ProRoleService getProRoleService() {
		return proRoleService;
	}

	public void setProRoleService(ProRoleService proRoleService) {
		this.proRoleService = proRoleService;
	}


	@Override
	public ProRole getModel() {
		if(proRole == null){
			proRole = new ProRole();	
		}
		return proRole;
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


	public ProRole getProRole() {
		return proRole;
	}


	public void setProRole(ProRole proRole) {
		this.proRole = proRole;
	}

	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = proRoleService.getProRoleByIds(keys,page,pageSize,idsIntegers);
			responseBean.setObjMap(map);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getByProjectAndName(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(proRole.getProject() == null || proRole.getProject().getProjectId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				Map<String, Object> map = proRoleService.getByProjectAndName(keys,page,pageSize,proRole);
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
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(proRole.getProject() == null || proRole.getProject().getProjectId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				Project theProject = projectService.getById(proRole.getProject().getProjectId());
				if(theProject == null){
					responseBean.setStatus(404);
					responseBean.put("error", "项目不存在");
				}else if(!theProject.getUser().getUserId().equals(loginUser.getUserId())){
					responseBean.setStatus(401);
					responseBean.put("error", "您不具有权限");
				}else{
					proRole = proRoleService.add(proRole);
					if(proRole.getProRoleId() != null) {
						responseBean.setStatus(201);
						responseBean.put("proRoleId", proRole.getProRoleId());
					} else {
						responseBean.put("error", "添加失败，系统错误");
						responseBean.setStatus(500);
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
	
	public void updateByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = proRoleService.updateByIds(keys,idsIntegers,proRole,loginUser);
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
			System.out.println(ids);
			if(ids == null || ids.equals("")){
				responseBean.setStatus(400);
				responseBean.put("error", "无法确定删除哪条信息");
			}else{
				Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
				Map<String, Object> map = proRoleService.deleteByIds(idsIntegers,user2);
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
