package com.epm.gdsa.proRoleAuth;

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
import com.epm.gdsa.user.User;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="proRoleAuthAction")
@Scope(value="prototype")
public class ProRoleAuthAction extends ActionSupport implements ModelDriven<ProRoleAuth>{
	
	@Autowired
	private ProRoleAuthService proRoleAuthService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProRoleService proRoleService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private ProRoleAuth proRoleAuth = new ProRoleAuth();
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		
		return NONE;
	}
	
	
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


	public ProRoleAuthService getProRoleAuthService() {
		return proRoleAuthService;
	}

	public void setProRoleAuthService(ProRoleAuthService proRoleAuthService) {
		this.proRoleAuthService = proRoleAuthService;
	}


	@Override
	public ProRoleAuth getModel() {
		if(proRoleAuth == null){
			proRoleAuth = new ProRoleAuth();	
		}
		return proRoleAuth;
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


	public ProRoleAuth getProRoleAuth() {
		return proRoleAuth;
	}


	public void setProRoleAuth(ProRoleAuth proRoleAuth) {
		this.proRoleAuth = proRoleAuth;
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = proRoleAuthService.getProRoleAuthByIds(keys,page,pageSize,idsIntegers);
			responseBean.setObjMap(map);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getByProRole(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(proRoleAuth.getProRole() == null || proRoleAuth.getProRole().getProRoleId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "角色不存在");
			}else{
				Map<String, Object> map = proRoleAuthService.getByProRole(keys,page,pageSize,proRoleAuth);
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
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(proRoleAuth.getProRole() == null || proRoleAuth.getProRole().getProRoleId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "角色不存在");
			}else{
				ProRole theProRole = proRoleService.getById(proRoleAuth.getProRole().getProRoleId());
				Project theProject = projectService.getById(theProRole.getProject().getProjectId());
				if(theProject == null){
					responseBean.setStatus(400);
					responseBean.put("error", "项目不存在");
				}else if(!theProject.getUser().getUserId().equals(loginUser.getUserId())){
					responseBean.setStatus(400);
					responseBean.put("error", "您不具有权限");
				}else{
					proRoleAuth = proRoleAuthService.add(proRoleAuth);
					if(proRoleAuth.getProRoleAuthId() != null) {
						responseBean.setStatus(200);
						responseBean.put("proRoleAuthId", proRoleAuth.getProRoleAuthId());
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
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = proRoleAuthService.updateByIds(keys,idsIntegers,proRoleAuth,loginUser);
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
				Map<String, Object> map = proRoleAuthService.deleteByIds(idsIntegers,user2);
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
