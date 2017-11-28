package com.epm.gdsa.worksiteRecord;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epm.beans.ResponseBean;
import com.epm.gdsa.user.User;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="worksiteRecordAction")
@Scope(value="prototype")
public class WorksiteRecordAction extends ActionSupport implements ModelDriven<WorksiteRecord>{
	
	@Autowired
	private WorksiteRecordService worksiteRecordService;
	
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

	public void testWorksiteRecord(){
		String imgStr = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wgARCAAQABEDASIAAhEBAxEB/8QAFQABAQAAAAAAAAAAAAAAAAAAAAb/xAAUAQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIQAxAAAAG/AB//xAAUEAEAAAAAAAAAAAAAAAAAAAAg/9oACAEBAAEFAl//xAAUEQEAAAAAAAAAAAAAAAAAAAAQ/9oACAEDAQE/AT//xAAUEQEAAAAAAAAAAAAAAAAAAAAQ/9oACAECAQE/AT//xAAUEAEAAAAAAAAAAAAAAAAAAAAg/9oACAEBAAY/Al//xAAUEAEAAAAAAAAAAAAAAAAAAAAg/9oACAEBAAE/IV//2gAMAwEAAgADAAAAEPPP/8QAFBEBAAAAAAAAAAAAAAAAAAAAEP/aAAgBAwEBPxA//8QAFBEBAAAAAAAAAAAAAAAAAAAAEP/aAAgBAgEBPxA//8QAFBABAAAAAAAAAAAAAAAAAAAAIP/aAAgBAQABPxBf/9k=";
		String path = "/upload/photo";
		System.out.println(path);
		System.out.println(PublicUtils.generateImage(imgStr, path));
	}
	
	public void getByProject(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
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
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		System.out.println(user2);
		if (user2 == null) {
			responseBean.setStatus(400);
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
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		String method = ServletActionContext.getRequest().getMethod();
		System.out.println(method);
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			
			Map<String, Object> thumMap = PublicUtils.generateImage(worksiteRecord.getThumbnail(), "/upload/photo");
			if(thumMap == null ){
				responseBean.put("error", "添加失败，系统错误");
				System.out.println("thumMap==null");
				responseBean.setStatus(500);
			}else{
				worksiteRecord.setThumbnail((String)thumMap.get("path"));
				worksiteRecord.setSize( new Float((float)(int)thumMap.get("size")));
				worksiteRecord.setUser(user2);
				worksiteRecord = worksiteRecordService.add(worksiteRecord);
				if(worksiteRecord.getWorksiteRecordId() != null) {
					responseBean.setStatus(200);
					responseBean.put("worksiteRecordId", worksiteRecord.getWorksiteRecordId());
				} else {
					responseBean.put("error", "添加失败，系统错误");
					System.out.println("id==null");
					responseBean.setStatus(500);
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
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = worksiteRecordService.updateByIds(keys,idsIntegers,worksiteRecord,user2);
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
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = worksiteRecordService.deleteByIds(idsIntegers,user2);
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
