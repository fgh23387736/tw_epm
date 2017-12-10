package com.epm.gdsa.document;

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

@Component(value="documentAction")
@Scope(value="prototype")
public class DocumentAction extends ActionSupport implements ModelDriven<Document>{
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private ProjectService projectService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private Document document = new Document();
	
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
	
	
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}


	@Override
	public Document getModel() {
		if(document == null){
			document = new Document();	
		}
		return document;
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


	public Document getDocument() {
		return document;
	}


	public void setDocument(Document document) {
		this.document = document;
	}

	public void getByProject(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(document.getProject() == null || document.getProject().getProjectId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				Map<String, Object> map = documentService.getByProject(keys,page,pageSize,document);
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
			if(document.getProject() == null || document.getProject().getProjectId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				
				Map<String, Object> map = documentService.getByProjectAndName(keys,page,pageSize,document);
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
			Map<String, Object> map = documentService.getDocumentByIds(keys,page,pageSize,idsIntegers);
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
			Project theProject = projectService.getById(document.getProject().getProjectId());
			if(theProject == null){
				responseBean.setStatus(400);
				responseBean.put("error", "项目不存在");
			}else{
				if(loginUser.getUserId() != theProject.getUser().getUserId()){
					responseBean.setStatus(400);
					responseBean.put("error", "只有项目发起人有权限创建项目节点");
				}else{
					document.setUser(loginUser);
					String filePathString = PublicUtils.uploadFile(content, contentFileName, "/upload/document", true);
					if(filePathString == null){
						responseBean.put("error", "文件上传失败失败，系统错误");
						responseBean.setStatus(500);
					}else{
						document.setContentUrl(filePathString);
						document.setSize((float) content.length());
						document.setDate(new Date());
						document = documentService.add(document);
						if(document.getDocumentId() != null) {
							responseBean.setStatus(200);
							responseBean.put("documentId", document.getDocumentId());
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
			Map<String, Object> map = documentService.updateByIds(keys,idsIntegers,document,loginUser);
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
			Map<String, Object> map = documentService.deleteByIds(idsIntegers,loginUser);
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
