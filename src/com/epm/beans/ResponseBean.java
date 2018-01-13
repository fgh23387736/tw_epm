package com.epm.beans;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResponseBean {
	private HttpServletResponse response = ServletActionContext.getResponse();
	private Map<String, Object> objMap =new HashMap<String, Object>();
	public ResponseBean() {
		// TODO 自动生成的构造函数存根
		response.setContentType("application/json;charset=utf-8");
		objMap.put("msg", "");
		objMap.put("status", 200);
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Map<String, Object> getObjMap() {
		return objMap;
	}

	public void setObjMap(Map<String, Object> objMap) {
		this.objMap = objMap;
		if(this.objMap == null){
			this.objMap = new HashMap<String, Object>();
		}
		this.objMap.put("msg", "");
		this.objMap.put("status", response.getStatus());
	}

	public void put(String key,Object value){
		this.objMap.put(key, value);
	}
	
	public void remove(String key){
		this.objMap.remove(key);
	}
	
	
	public void setStatus(int arg0) {
		this.response.setStatus(arg0);
		this.objMap.put("status", arg0);
	}
	
	public void write(String s) throws IOException {
		this.response.getWriter().write(s);
	}
	
	public String getJsonString() {
		return JSON.toJSONString(this.objMap, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
	}
	
	
	public void writeTheMap() throws IOException{
		this.write(this.getJsonString());
	}
	
	public Object jsonStringToJsonObject(String json){
		return JSON.parseObject(json);
	}
	
	public Object jsonStringToJsonArray(String json,String key){
		return JSON.parseObject(json).getJSONArray(key);
	}
}
