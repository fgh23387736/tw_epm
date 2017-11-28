package com.epm.gdsa.pointProblem;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
import com.epm.gdsa.point.Point;
import com.epm.gdsa.point.PointService;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="pointProblemAction")
@Scope(value="prototype")
public class PointProblemAction extends ActionSupport implements ModelDriven<PointProblem>{
	
	@Autowired
	private PointProblemService pointProblemService;
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private UserProService userProService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private PointProblem pointProblem = new PointProblem();
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		
		return NONE;
	}
	
	
	public PointProblemService getPointProblemService() {
		return pointProblemService;
	}

	public void setPointProblemService(PointProblemService pointProblemService) {
		this.pointProblemService = pointProblemService;
	}


	@Override
	public PointProblem getModel() {
		if(pointProblem == null){
			pointProblem = new PointProblem();	
		}
		return pointProblem;
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


	public PointProblem getPointProblem() {
		return pointProblem;
	}


	public void setPointProblem(PointProblem pointProblem) {
		this.pointProblem = pointProblem;
	}
	
	public void getByPoint(){
		ResponseBean responseBean = new ResponseBean();
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(pointProblem.getPoint() == null || pointProblem.getPoint().getPointId() == null){
				responseBean.setStatus(400);
				responseBean.put("error", "节点不存在");
			}else{
				Map<String, Object> map = pointProblemService.getByPoint(keys,page,pageSize,pointProblem);
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
			Map<String, Object> map = pointProblemService.getPointProblemByIds(keys,page,pageSize,idsIntegers);
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
			Point thePoint = pointService.getById(pointProblem.getPoint().getPointId());
			if(thePoint == null){
				responseBean.setStatus(400);
				responseBean.put("error", "节点不存在");
			}else{
				UserPro theUserPro = new UserPro();
				theUserPro.setUser(user2);
				theUserPro.setProject(thePoint.getProject());
				Map<String,Object> theUserProMap = userProService.getByProjectAndUser("proRole", null, null, theUserPro);
				Boolean isOk = false;
				List<Map<String, Object>> mapList = (List<Map<String, Object>>) theUserProMap.get("resultList");
				for (Map<String, Object> map : mapList) {
					if((Integer)((Map<String, Object>)map.get("proRole")).get("auth") >= 2){
						isOk = true;
						break;
					}
				}
				if(!isOk){
					responseBean.setStatus(400);
					responseBean.put("error", "您不具有权限提问");
				}else{
					pointProblem.setUser(user2);
					pointProblem.setDate(new Date());
					pointProblem = pointProblemService.add(pointProblem);
					if(pointProblem.getPointProblemId() != null) {
						responseBean.setStatus(200);
						responseBean.put("pointProblemId", pointProblem.getPointProblemId());
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
		User user2 = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (user2 == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = pointProblemService.updateByIds(keys,idsIntegers,pointProblem,user2);
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
			Map<String, Object> map = pointProblemService.deleteByIds(idsIntegers,user2);
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
