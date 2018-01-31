package com.epm.actions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.epm.beans.ResponseBean;
import com.epm.gdsa.project.ProjectService;
import com.epm.gdsa.user.User;
import com.epm.gdsa.user.UserService;
import com.opensymphony.xwork2.ActionSupport;

@Component(value="adminPublicAction")
@Scope(value="prototype")
public class AdminPublicAction extends ActionSupport{
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public String execute() throws Exception {
		
		return NONE;
	}
	
	public void getWebsiteData(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			int userNumber = userService.getAllUserNumber();
			responseBean.put("userNumber", userNumber);
			
			int projectNumber = projectService.getAllProjectNumber();
			responseBean.put("projectNumber", projectNumber);
			
			int adminNumber = userService.getAllAdminNumber();
			responseBean.put("adminNumber", adminNumber);
			
			int finishedProjectNumber = projectService.getAllFinishedProjectNumber();
			responseBean.put("finishedProjectNumber", finishedProjectNumber);
			Calendar c = Calendar.getInstance();
			// 这是已知的日期
			Date d = new Date();
			
			List<Map<String, Object>> startProject = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> endProject = new ArrayList<Map<String,Object>>();
			Map<String , Object> tempMapForStartProject;
			Map<String , Object> tempMapForEndProject;
			int year;
			int month;
			for (int i = 5; i >= 0; i--) {
				c.setTime(d);
				c.set(Calendar.DAY_OF_MONTH, 1);
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.add(Calendar.MONTH, -i);
				
				tempMapForStartProject = new HashMap<String, Object>();
				tempMapForEndProject = new HashMap<String, Object>();
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH)+1;
				String theDateString = "";
				if(month <= 9){
					theDateString = year +"-0"+month;
				}else{
					theDateString = year +"-"+month;
				}
				tempMapForStartProject.put("date",theDateString);
				tempMapForEndProject.put("date",theDateString);
				Date nowDate = c.getTime();
				c.add(Calendar.MONTH, 1);
				Date lastMonth = c.getTime();
				int numberForMonth = projectService.getProjectNumberByStartDateBetween(nowDate,lastMonth);
				tempMapForStartProject.put("number", numberForMonth);
				startProject.add(tempMapForStartProject);
				
				numberForMonth = projectService.getProjectNumberByEndDateBBetween(nowDate,lastMonth);
				tempMapForEndProject.put("number", numberForMonth);
				endProject.add(tempMapForEndProject);
				
			}
			responseBean.put("startProject", startProject);
			responseBean.put("endProject", endProject);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getMenu(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			BufferedReader reader = null;
			String laststr = "";
			try{
				String path;
				if(loginUser.getType() == 2){
					path = ServletActionContext.getServletContext().getRealPath("end/admin/menu.json");
				}else{
					path = ServletActionContext.getServletContext().getRealPath("end/admin/menuUser.json");
				}
				FileInputStream fileInputStream = new FileInputStream(path);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
				reader = new BufferedReader(inputStreamReader);
				String tempString = null;
				while((tempString = reader.readLine()) != null){
					laststr += tempString;
				}
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(reader != null){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} 
			responseBean.put("data", responseBean.jsonStringToJsonArray(laststr,"data") );
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
