package com.epm.enums;

public enum ProRoleAuthEnum {
	DOCUMENT("DOCUMENT","处理项目资料"),
	SPECIFICATION("SPECIFICATION","处理项目规范"),
	NOTICE("NOTICE","发布公告"),
	WORKSITE_RECORD("WORKSITE_RECORD","提交现场记录"),
	POINT("POINT","创建项目进度节点"),
	LOG("LOG","上传日志"),
	MATERIAL("MATERIAL","上传项目材料记录"),
	LEARN_DOC("LEARN_DOC","上传学习资料"),
	POINT_PROBLEM("POINT_PROBLEM","针对节点提问"),
	SIGN_CODE("SIGN_CODE","生成并获取签到码"),
	POINT_ANSWER("POINT_ANSWER","回答节点问题");
	
	private String describe;
	private String name;
	private ProRoleAuthEnum(String name,String describe){
		this.describe = describe;
		this.name = name;
	}
	public String getDescribe() {
		return describe;
	}
	public String getName() {
		return name;
	}
	
	
}
