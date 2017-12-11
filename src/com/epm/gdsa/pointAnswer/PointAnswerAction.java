package com.epm.gdsa.pointAnswer;

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
import com.epm.gdsa.point.Point;
import com.epm.gdsa.point.PointService;
import com.epm.gdsa.pointProblem.PointProblem;
import com.epm.gdsa.pointProblem.PointProblemService;
import com.epm.utils.PublicUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component(value="pointAnswerAction")
@Scope(value="prototype")
public class PointAnswerAction extends ActionSupport implements ModelDriven<PointAnswer>{
	
	@Autowired
	private PointAnswerService pointAnswerService;
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private PointProblemService pointProblemService;
	
	@Autowired
	private UserProService userProService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProRoleService proRoleService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private PointAnswer pointAnswer = new PointAnswer();
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		
		return NONE;
	}
	
	
	public PointService getPointService() {
		return pointService;
	}


	public void setPointService(PointService pointService) {
		this.pointService = pointService;
	}


	public PointProblemService getPointProblemService() {
		return pointProblemService;
	}


	public void setPointProblemService(PointProblemService pointProblemService) {
		this.pointProblemService = pointProblemService;
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


	public ProRoleService getProRoleService() {
		return proRoleService;
	}


	public void setProRoleService(ProRoleService proRoleService) {
		this.proRoleService = proRoleService;
	}


	public PointAnswerService getPointAnswerService() {
		return pointAnswerService;
	}

	public void setPointAnswerService(PointAnswerService pointAnswerService) {
		this.pointAnswerService = pointAnswerService;
	}


	@Override
	public PointAnswer getModel() {
		if(pointAnswer == null){
			pointAnswer = new PointAnswer();	
		}
		return pointAnswer;
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


	public PointAnswer getPointAnswer() {
		return pointAnswer;
	}


	public void setPointAnswer(PointAnswer pointAnswer) {
		this.pointAnswer = pointAnswer;
	}
	
	public void getByPointProblem(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(pointAnswer.getPointProblem() == null || pointAnswer.getPointProblem().getPointProblemId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "问题不存在");
			}else{
				Map<String, Object> map = pointAnswerService.getByPointProblem(keys,page,pageSize,pointAnswer);
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
			Map<String, Object> map = pointAnswerService.getPointAnswerByIds(keys,page,pageSize,idsIntegers);
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
			PointProblem thePointProblem = pointProblemService.getById(pointAnswer.getPointProblem().getPointProblemId());
			Point thePoint =pointService.getById(thePointProblem.getPoint().getPointId());
			Project theProject = projectService.getById(thePoint.getProject().getProjectId());
			if(theProject == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				boolean isHaveTheAuth = false;
				List<UserPro> theUserProList = userProService.getByProjectAndUser(theProject,loginUser);
				if(theUserProList.size() > 0 ){
					for (UserPro userPro : theUserProList) {
						if(proRoleService.isHaveTheAuth(userPro.getProRole(), ProRoleAuthEnum.POINT_ANSWER)){
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
					pointAnswer.setUser(loginUser);
					pointAnswer.setDate(new Date());
					pointAnswer = pointAnswerService.add(pointAnswer);
					if(pointAnswer.getPointAnswerId() != null) {
						responseBean.setStatus(200);
						responseBean.put("pointAnswerId", pointAnswer.getPointAnswerId());
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
			Map<String, Object> map = pointAnswerService.updateByIds(keys,idsIntegers,pointAnswer,loginUser);
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
			Map<String, Object> map = pointAnswerService.deleteByIds(idsIntegers,loginUser);
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
