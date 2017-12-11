package com.epm.gdsa.project;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.epm.gdsa.document.Document;
import com.epm.gdsa.learnDoc.LearnDoc;
import com.epm.gdsa.log.Log;
import com.epm.gdsa.material.Material;
import com.epm.gdsa.notice.Notice;
import com.epm.gdsa.point.Point;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.sign.Sign;
import com.epm.gdsa.specification.Specification;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.worksiteRecord.WorksiteRecord;

@Entity
@Table(name = "GDSA_EPMS_PROJECT")
public class Project {
	
	@Id
	@Column(name="GDSA_PROJECT_ID")
	@SequenceGenerator(name="project_sequence",sequenceName="GDSA_PROJECT_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="project_sequence")  
	private Integer projectId;
	
	@Column(name="GDSA_PROJECT_STARTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date startDate;//开始时间
	
	@Column(name="GDSA_PROJECT_ENDDATEA")
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date endDateA;//预计结束时间
	
	@Column(name="GDSA_PROJECT_ENDDATEB")
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date endDateB;//实际结束时间
	
	@Column(name="GDSA_PROJECT_CONTENT")
	private String content;//项目内容
	
	@Column(name="GDSA_PROJECT_NAME")
	private String name;//项目名称
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_PROJECT_USER")
	private User user; 	//上传者
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Point> points = new HashSet<Point>();//项目对应节点

	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Document> documents = new HashSet<Document>(); //项目资料
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Notice> notices = new HashSet<Notice>(); //项目消息
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Sign> signs = new HashSet<Sign>(); //项目签到
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Specification> specifications = new HashSet<Specification>(); //项目规范
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<WorksiteRecord> worksiteRecords = new HashSet<WorksiteRecord>();//项目现场记录
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Log> logs = new HashSet<Log>(); //项目日志
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<UserPro> userPros = new HashSet<UserPro>();
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<ProRole> proRoles = new HashSet<ProRole>();
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Material> materials = new HashSet<Material>();
	
	@OneToMany(mappedBy="project",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<LearnDoc> learnDocs = new HashSet<LearnDoc>();
	
	public Set<Material> getMaterials() {
		return materials;
	}


	public void setMaterials(Set<Material> materials) {
		this.materials = materials;
	}


	public Set<ProRole> getProRoles() {
		return proRoles;
	}


	public Set<LearnDoc> getLearnDocs() {
		return learnDocs;
	}


	public void setLearnDocs(Set<LearnDoc> learnDocs) {
		this.learnDocs = learnDocs;
	}


	public void setProRoles(Set<ProRole> proRoles) {
		this.proRoles = proRoles;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Set<UserPro> getUserPros() {
		return userPros;
	}


	public void setUserPros(Set<UserPro> userPros) {
		this.userPros = userPros;
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
	
	public Set<Specification> getSpecifications() {
		return specifications;
	}


	public void setSpecifications(Set<Specification> specifications) {
		this.specifications = specifications;
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


	public Set<Point> getPoints() {
		return points;
	}


	public void setPoints(Set<Point> points) {
		this.points = points;
	}


	public Integer getProjectId() {
		return projectId;
	}


	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDateA() {
		return endDateA;
	}


	public void setEndDateA(Date endDateA) {
		this.endDateA = endDateA;
	}


	public Date getEndDateB() {
		return endDateB;
	}


	public void setEndDateB(Date endDateB) {
		this.endDateB = endDateB;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	

	
	
}
