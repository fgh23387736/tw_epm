package com.epm.gdsa.worksiteRecord;

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

@Component(value="worksiteRecordAction")
@Scope(value="prototype")
public class WorksiteRecordAction extends ActionSupport implements ModelDriven<WorksiteRecord>{
	
	@Autowired
	private WorksiteRecordService worksiteRecordService;
	
	@Autowired
	private UserProService userProService;
	
	@Autowired
	private ProRoleService proRoleService;
	
	@Autowired
	private ProjectService projectService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private WorksiteRecord worksiteRecord = new WorksiteRecord();
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		
		return NONE;
	}
	
	
	public WorksiteRecordService getWorksiteRecordService() {
		return worksiteRecordService;
	}

	public void setWorksiteRecordService(WorksiteRecordService worksiteRecordService) {
		this.worksiteRecordService = worksiteRecordService;
	}


	@Override
	public WorksiteRecord getModel() {
		if(worksiteRecord == null){
			worksiteRecord = new WorksiteRecord();	
		}
		return worksiteRecord;
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

	
	public UserProService getUserProService() {
		return userProService;
	}


	public void setUserProService(UserProService userProService) {
		this.userProService = userProService;
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


	public String getKeys() {
		return keys;
	}


	public void setKeys(String keys) {
		this.keys = keys;
	}


	public WorksiteRecord getWorksiteRecord() {
		return worksiteRecord;
	}


	public void setWorksiteRecord(WorksiteRecord worksiteRecord) {
		this.worksiteRecord = worksiteRecord;
	}
	
	public void getByProject(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(worksiteRecord.getProject() == null || worksiteRecord.getProject().getProjectId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				Map<String, Object> map = worksiteRecordService.getByProject(keys,page,pageSize,worksiteRecord);
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
			System.out.println(idsIntegers.length);
			Map<String, Object> map = worksiteRecordService.getWorksiteRecordByIds(keys,page,pageSize,idsIntegers);
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
			Project theProject = projectService.getById(worksiteRecord.getProject().getProjectId());
			if(theProject == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				boolean isHaveTheAuth = false;
				List<UserPro> theUserProList = userProService.getByProjectAndUser(theProject,loginUser);
				if(theUserProList.size() > 0 ){
					for (UserPro userPro : theUserProList) {
						if(proRoleService.isHaveTheAuth(userPro.getProRole(), ProRoleAuthEnum.WORKSITE_RECORD)){
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
					Map<String, Object> thumMap = PublicUtils.generateImage(worksiteRecord.getThumbnail(), "/upload/photo");
					if(thumMap == null ){
						responseBean.put("error", "添加失败，系统错误");
						System.out.println("thumMap==null");
						responseBean.setStatus(500);
					}else{
						worksiteRecord.setThumbnail((String)thumMap.get("path"));
						worksiteRecord.setSize( new Float((float)(int)thumMap.get("size")));
						worksiteRecord.setUser(loginUser);
						worksiteRecord = worksiteRecordService.add(worksiteRecord);
						if(worksiteRecord.getWorksiteRecordId() != null) {
							responseBean.setStatus(201);
							responseBean.put("worksiteRecordId", worksiteRecord.getWorksiteRecordId());
						} else {
							responseBean.put("error", "添加失败，系统错误");
							System.out.println("id==null");
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
			Map<String, Object> map = worksiteRecordService.updateByIds(keys,idsIntegers,worksiteRecord,loginUser);
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
			Map<String, Object> map = worksiteRecordService.deleteByIds(idsIntegers,loginUser);
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
