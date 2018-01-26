package com.epm.gdsa.learnDoc;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epm.beans.ResponseBean;
import com.epm.enums.ProRoleAuthEnum;
import com.epm.gdsa.proRole.ProRoleService;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.project.ProjectService;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.userPro.UserProService;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="learnDocAction")
@Scope(value="prototype")
public class LearnDocAction extends ActionSupport implements ModelDriven<LearnDoc>{
	
	@Autowired
	private LearnDocService learnDocService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserProService userProService;
	
	@Autowired
	private ProRoleService proRoleService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private LearnDoc learnDoc = new LearnDoc();
	
	private File content;
	
	private String contentFileName;
	
	
	public ProRoleService getProRoleService() {
		return proRoleService;
	}


	public void setProRoleService(ProRoleService proRoleService) {
		this.proRoleService = proRoleService;
	}


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
	
	
	public LearnDocService getLearnDocService() {
		return learnDocService;
	}

	public void setLearnDocService(LearnDocService learnDocService) {
		this.learnDocService = learnDocService;
	}


	@Override
	public LearnDoc getModel() {
		if(learnDoc == null){
			learnDoc = new LearnDoc();	
		}
		return learnDoc;
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


	public LearnDoc getLearnDoc() {
		return learnDoc;
	}


	public void setLearnDoc(LearnDoc learnDoc) {
		this.learnDoc = learnDoc;
	}

	public void getByProject(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(learnDoc.getProject() == null || learnDoc.getProject().getProjectId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				Map<String, Object> map = learnDocService.getByProject(keys,page,pageSize,learnDoc);
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
	
	public void getByProjectAndName(){
		if(learnDoc.getName() == null){
			learnDoc.setName("");
		}
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(learnDoc.getProject() == null || learnDoc.getProject().getProjectId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				
				Map<String, Object> map = learnDocService.getByProjectAndName(keys,page,pageSize,learnDoc);
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
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = learnDocService.getLearnDocByIds(keys,page,pageSize,idsIntegers);
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
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Project theProject = projectService.getById(learnDoc.getProject().getProjectId());
			if(theProject == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				boolean isHaveTheAuth = false;
				List<UserPro> theUserProList = userProService.getByProjectAndUser(theProject,loginUser);
				if(theUserProList.size() > 0 ){
					for (UserPro userPro : theUserProList) {
						if(proRoleService.isHaveTheAuth(userPro.getProRole(), ProRoleAuthEnum.LEARN_DOC)){
							isHaveTheAuth = true;
							break;
						}
					}
				}
				if(loginUser.getUserId() == theProject.getUser().getUserId()){
					isHaveTheAuth = true;
				}
				if(!isHaveTheAuth){
					responseBean.setStatus(401);
					responseBean.put("error", "您不具有权限");
				}else{
					//获得权限时业务逻辑
					learnDoc.setUser(loginUser);
					String filePathString = PublicUtils.uploadFile(content, contentFileName, "/upload/learnDoc", true);
					if(filePathString == null){
						responseBean.put("error", "文件上传失败失败，系统错误");
						responseBean.setStatus(500);
					}else{
						learnDoc.setContentUrl(filePathString);
						learnDoc = learnDocService.add(learnDoc);
						if(learnDoc.getLearnDocId() != null) {
							responseBean.setStatus(201);
							responseBean.put("learnDocId", learnDoc.getLearnDocId());
						} else {
							responseBean.put("error", "添加失败，系统错误");
							responseBean.setStatus(500);
						}
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
			Map<String, Object> map = learnDocService.updateByIds(keys,idsIntegers,learnDoc,loginUser);
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
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = learnDocService.deleteByIds(idsIntegers,loginUser);
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
