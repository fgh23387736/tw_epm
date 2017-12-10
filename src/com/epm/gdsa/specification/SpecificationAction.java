package com.epm.gdsa.specification;

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
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="specificationAction")
@Scope(value="prototype")
public class SpecificationAction extends ActionSupport implements ModelDriven<Specification>{
	
	@Autowired
	private SpecificationService specificationService;
	
	@Autowired
	private ProjectService projectService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private Specification specification = new Specification();
	
	private File content;
	
	private String contentFileName;
	
	
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
	
	
	public SpecificationService getSpecificationService() {
		return specificationService;
	}

	public void setSpecificationService(SpecificationService specificationService) {
		this.specificationService = specificationService;
	}


	@Override
	public Specification getModel() {
		if(specification == null){
			specification = new Specification();	
		}
		return specification;
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


	public Specification getSpecification() {
		return specification;
	}


	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public void getByProject(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(specification.getProject() == null || specification.getProject().getProjectId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				Map<String, Object> map = specificationService.getByProject(keys,page,pageSize,specification);
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
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(specification.getProject() == null || specification.getProject().getProjectId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				
				Map<String, Object> map = specificationService.getByProjectAndName(keys,page,pageSize,specification);
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
			Map<String, Object> map = specificationService.getSpecificationByIds(keys,page,pageSize,idsIntegers);
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
			Project theProject = projectService.getById(specification.getProject().getProjectId());
			if(theProject == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				if(loginUser.getUserId() != theProject.getUser().getUserId()){
					responseBean.setStatus(400);
					responseBean.put("error", "只有项目发起人有权限上传规范");
				}else{
					String filePathString = PublicUtils.uploadFile(content, contentFileName, "/upload/specification", true);
					if(filePathString == null){
						responseBean.put("error", "文件上传失败失败，系统错误");
						responseBean.setStatus(500);
					}else{
						specification.setContentUrl(filePathString);
						specification.setDate(new Date());
						specification = specificationService.add(specification);
						if(specification.getSpecificationId() != null) {
							responseBean.setStatus(200);
							responseBean.put("specificationId", specification.getSpecificationId());
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
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = specificationService.updateByIds(keys,idsIntegers,specification,loginUser);
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
			Map<String, Object> map = specificationService.deleteByIds(idsIntegers,loginUser);
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
