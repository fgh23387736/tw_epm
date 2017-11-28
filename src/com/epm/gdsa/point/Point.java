package com.epm.gdsa.point;

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

import oracle.sql.BFILE;











import com.alibaba.fastjson.annotation.JSONField;
import com.epm.gdsa.pointProblem.PointProblem;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;

@Entity
@Table(name = "GDSA_EPMS_POINT")
public class Point {
	
	@Id
	@Column(name="GDSA_POINT_ID")
	@SequenceGenerator(name="point_sequence",sequenceName="GDSA_POINT_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="point_sequence") 
	private Integer pointId;
	
	@Column(name="GDSA_POINT_STATE")
	private Integer state; //工程进度百分比
		
	
	@Column(name="GDSA_POINT_DESCRIBE")
	private String describe; 	//节点描述
	
	@Column(name="GDSA_POINT_NAME")
	private String name; 	//名称
	
	@Column(name="GDSA_POINT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;	//开始时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_POINT_PROJECT")
	private Project project; 	//项目
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_POINT_USER")
	private User user; 	//上传人员

	@OneToMany(mappedBy="point",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<PointProblem> pointProblems = new HashSet<PointProblem>();
	
	public Set<PointProblem> getPointProblems() {
		return pointProblems;
	}

	public void setPointProblems(Set<PointProblem> pointProblems) {
		this.pointProblems = pointProblems;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
	
}
