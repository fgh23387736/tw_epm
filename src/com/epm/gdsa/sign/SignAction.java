package com.epm.gdsa.sign;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epm.beans.ResponseBean;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.project.ProjectService;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.userPro.UserProService;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="signAction")
@Scope(value="prototype")
public class SignAction extends ActionSupport implements ModelDriven<Sign>{
	
	@Autowired
	private SignService signService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserProService userProService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private Sign sign = new Sign();
	
	private File content;
	
	private String contentFileName;
	
	
	public UserProService getUserProService() {
		return userProService;
	}


	public void setUserProService(UserProService userProService) {
		this.userProService = userProService;
	}


	public ProjectService getProjectService() {
		return projectService;
	}


	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}


	public File getContent() {
		return content;
	}


	public void setContent(File content) {
		this.content = content;
	}


	public String getContentFileName() {
		return contentFileName;
	}


	public void setContentFileName(String contentFileName) {
		this.contentFileName = contentFileName;
	}


	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		
		return NONE;
	}
	
	
	public SignService getSignService() {
		return signService;
	}

	public void setSignService(SignService signService) {
		this.signService = signService;
	}


	@Override
	public Sign getModel() {
		if(sign == null){
			sign = new Sign();	
		}
		return sign;
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


	public Sign getSign() {
		return sign;
	}


	public void setSign(Sign sign) {
		this.sign = sign;
	}

	public void getByProject(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(sign.getProject() == null || sign.getProject().getProjectId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				Map<String, Object> map = signService.getByProject(keys,page,pageSize,sign);
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
	
	public void getByUser(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(sign.getUser() == null || sign.getUser().getUserId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "用户不存在");
			}else{
				Map<String, Object> map = signService.getByUser(keys,page,pageSize,sign);
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
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			System.out.println(idsIntegers.length);
			Map<String, Object> map = signService.getSignByIds(keys,page,pageSize,idsIntegers);
			responseBean.setObjMap(map);
			
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
		String method = ServletActionContext.getRequest().getMethod();
		System.out.println(method);
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{			
			Project theProject = projectService.getById(sign.getProject().getProjectId());
			if(theProject == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				UserPro userProTemp = new UserPro();
				userProTemp.setProject(theProject);
				userProTemp.setUser(loginUser);
				Map<String, Object> userProMap = userProService.getByProjectAndUser("userProId", null, null, userProTemp);
				if((int)userProMap.get("total") != 0 || loginUser.getUserId() == theProject.getUser().getUserId()){
					sign.setUser(loginUser);
					sign.setDate(new Date());
					sign = signService.add(sign);
					if(sign.getSignId() != null) {
						responseBean.setStatus(200);
						responseBean.put("signId", sign.getSignId());
					} else {
						responseBean.put("error", "添加失败，系统错误");
						responseBean.setStatus(500);
					}
				}else{
					responseBean.setStatus(400);
					responseBean.put("error", "您未参与该项目，无法签到");
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
			Map<String, Object> map = signService.updateByIds(keys,idsIntegers,sign,loginUser);
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
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = signService.deleteByIds(idsIntegers,loginUser);
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

}
