package com.epm.gdsa.noticeRole;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epm.beans.ResponseBean;
import com.epm.gdsa.notice.Notice;
import com.epm.gdsa.notice.NoticeService;
import com.epm.gdsa.noticeRole.NoticeRole;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.user.User;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="noticeRoleAction")
@Scope(value="prototype")
public class NoticeRoleAction extends ActionSupport implements ModelDriven<NoticeRole>{
	
	@Autowired
	private NoticeRoleService noticeRoleService;
	
	@Autowired
	private NoticeService noticeService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private NoticeRole noticeRole = new NoticeRole();
	
	private String userName;
	
	
	
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
	
	
	public NoticeRoleService getNoticeRoleService() {
		return noticeRoleService;
	}

	public void setNoticeRoleService(NoticeRoleService noticeRoleService) {
		this.noticeRoleService = noticeRoleService;
	}


	@Override
	public NoticeRole getModel() {
		if(noticeRole == null){
			noticeRole = new NoticeRole();	
		}
		return noticeRole;
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


	public NoticeRole getNoticeRole() {
		return noticeRole;
	}


	public void setNoticeRole(NoticeRole noticeRole) {
		this.noticeRole = noticeRole;
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = noticeRoleService.getNoticeRoleByIds(keys,page,pageSize,idsIntegers);
			responseBean.setObjMap(map);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getByNotice(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(noticeRole.getNotice() == null || noticeRole.getNotice().getNoticeId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "公告不存在");
			}else{
				Map<String, Object> map = noticeRoleService.getByNotice(keys,page,pageSize,noticeRole,userName);
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
	
	public void getByProRole(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(noticeRole.getProRole() == null || noticeRole.getProRole().getProRoleId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "角色不存在");
			}else{
				Map<String, Object> map = noticeRoleService.getByProRole(keys,page,pageSize,noticeRole,userName);
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
		ResponseBean response =new ResponseBean();
		
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			response.setStatus(400);
			response.put("error", "您还未登录，无权获取本信息");
		}else{
			Map<String, Object> map = noticeRoleService.getByNoticeAndProRole(keys, page, pageSize, noticeRole);
			if((int)map.get("total") != 0){
				response.put("error", "该记录已存在，无法重复添加");
				response.setStatus(400);
			}else{
				Notice noticeTemp = noticeService.getById(noticeRole.getNotice().getNoticeId());
				if(noticeTemp.getUser().getUserId() == loginUser.getUserId()){
					Set<ProRole> setProRoles = noticeTemp.getProject().getProRoles();
					response.put("error", "添加失败，该角色不属于公告所在项目");
					response.setStatus(400);
					for (ProRole proRole : setProRoles) {
						System.out.println(proRole.getProRoleId()+":"+noticeRole.getProRole().getProRoleId());
						if(proRole.getProRoleId().equals(noticeRole.getProRole().getProRoleId())){
							noticeRole = noticeRoleService.add(noticeRole);
							if(noticeRole.getNoticeRoleId() != null) {
								response.setStatus(200);
								response.put("noticeRoleId", noticeRole.getNoticeRoleId());
								response.remove("error");
							} else {
								response.put("error", "添加失败，系统错误");
								response.setStatus(500);
							}
							break;
						}
					}
				}else{
					response.put("error", "您不具有权限");
					response.setStatus(400);
				}
				
			}
		}
		
		
		
		
		try {
			response.write(response.getJsonString());
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
			Map<String, Object> map = noticeRoleService.updateByIds(keys,idsIntegers,noticeRole,user2);
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
				Map<String, Object> map = noticeRoleService.deleteByIds(idsIntegers,user2);
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
