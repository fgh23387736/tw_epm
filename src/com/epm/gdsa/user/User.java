package com.epm.gdsa.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.epm.gdsa.document.Document;
import com.epm.gdsa.log.Log;
import com.epm.gdsa.material.Material;
import com.epm.gdsa.notice.Notice;
import com.epm.gdsa.point.Point;
import com.epm.gdsa.pointProblem.PointProblem;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.sign.Sign;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.worksiteRecord.WorksiteRecord;

@Entity
@Table(name = "GDSA_EPMS_USER")
public class User {
	
	@Id
	@Column(name="GDSA_USER_ID")
	@SequenceGenerator(name="user_sequence",sequenceName="GDSA_USER_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="user_sequence")  
	private Integer userId;
	
	@Column(name="GDSA_USER_LOGINNAME")
	private String loginName;
	
	@Column(name="GDSA_USER_NAME")
	private String name;
	
	@Column(name="GDSA_USER_PASSWORD")
	private String password;
	
	@Column(name="GDSA_USER_TYPE")
	private Integer type;
	
	@Column(name="GDSA_USER_STATE")
	private Integer state;
	
	@Column(name="GDSA_USER_POINT")
	private Integer point;
	
	@Column(name="GDSA_USER_TEL")
	private String tel;
	
	@Column(name="GDSA_USER_EMAIL")
	private String email;
	
	@Column(name="GDSA_USER_WECHAT")
	private String wechat;

	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Document> documents = new HashSet<Document>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Notice> notices = new HashSet<Notice>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Sign> signs = new HashSet<Sign>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<WorksiteRecord> worksiteRecords = new HashSet<WorksiteRecord>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Log> logs = new HashSet<Log>();
	
	@OneToMany(mappedBy="buyer",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Material> buyers = new HashSet<Material>();
	
	@OneToMany(mappedBy="seller",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Material> sellers = new HashSet<Material>();
	
	@OneToMany(mappedBy="checker",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Material> checkers = new HashSet<Material>();
	
	@OneToMany(mappedBy="submitter",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Material> submitters = new HashSet<Material>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<UserPro> userPros = new HashSet<UserPro>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Point> points = new HashSet<Point>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Project> projects = new HashSet<Project>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<PointProblem> pointProblems = new HashSet<PointProblem>();
	
	@Transient
	private String rePassword;
	
	@Transient
	private String newPassword;
	
	
	
	public Set<PointProblem> getPointProblems() {
		return pointProblems;
	}


	public void setPointProblems(Set<PointProblem> pointProblems) {
		this.pointProblems = pointProblems;
	}


	public Set<Project> getProjects() {
		return projects;
	}


	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}


	public Set<Point> getPoints() {
		return points;
	}


	public void setPoints(Set<Point> points) {
		this.points = points;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getRePassword() {
		return rePassword;
	}


	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}


	public Set<UserPro> getUserPros() {
		return userPros;
	}


	public void setUserPros(Set<UserPro> userPros) {
		this.userPros = userPros;
	}


	public Set<Material> getBuyers() {
		return buyers;
	}


	public void setBuyers(Set<Material> buyers) {
		this.buyers = buyers;
	}


	public Set<Material> getSellers() {
		return sellers;
	}

	public void setSellers(Set<Material> sellers) {
		this.sellers = sellers;
	}


	public Set<Material> getCheckers() {
		return checkers;
	}


	public void setCheckers(Set<Material> checkers) {
		this.checkers = checkers;
	}


	public Set<Material> getSubmitters() {
		return submitters;
	}


	public void setSubmitters(Set<Material> submitters) {
		this.submitters = submitters;
	}


	public Set<Log> getLogs() {
		return logs;
	}


	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}


	public Set<WorksiteRecord> getWorksiteRecords() {
		return worksiteRecords;
	}


	public void setWorksiteRecords(Set<WorksiteRecord> worksiteRecords) {
		this.worksiteRecords = worksiteRecords;
	}


	public Set<Sign> getSigns() {
		return signs;
	}


	public void setSigns(Set<Sign> signs) {
		this.signs = signs;
	}


	public Set<Notice> getNotices() {
		return notices;
	}


	public void setNotices(Set<Notice> notices) {
		this.notices = notices;
	}


	public Set<Document> getDocuments() {
		return documents;
	}


	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	
	
	
	
}
