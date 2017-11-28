package com.epm.gdsa.pointProblem;

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

import com.alibaba.fastjson.annotation.JSONField;
import com.epm.gdsa.document.Document;
import com.epm.gdsa.log.Log;
import com.epm.gdsa.notice.Notice;
import com.epm.gdsa.point.Point;
import com.epm.gdsa.pointAnswer.PointAnswer;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.sign.Sign;
import com.epm.gdsa.specification.Specification;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.worksiteRecord.WorksiteRecord;

@Entity
@Table(name = "GDSA_EPMS_POINT_PROBLEM")
public class PointProblem {
	
	@Id
	@Column(name="GDSA_POINT_PROBLEM_ID")
	@SequenceGenerator(name="pointProblem_sequence",sequenceName="GDSA_POINT_PROBLEM_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pointProblem_sequence")  
	private Integer pointProblemId;
	
	@Column(name="GDSA_POINT_PROBLEM_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date date;//开始时间
	
	@Column(name="GDSA_POINT_PROBLEM_PROBLEM")
	private String problem;//项目内容
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_POINT_PROBLEM_USER")
	private User user; 	//上传者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_POINT_PROBLEM_POINT")
	private Point point; 	//上传者
	
	@OneToMany(mappedBy="pointProblem",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<PointAnswer> pointAnswers = new HashSet<PointAnswer>();//项目对应节点

	public Integer getPointProblemId() {
		return pointProblemId;
	}

	public void setPointProblemId(Integer pointProblemId) {
		this.pointProblemId = pointProblemId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Set<PointAnswer> getPointAnswers() {
		return pointAnswers;
	}

	public void setPointAnswers(Set<PointAnswer> pointAnswers) {
		this.pointAnswers = pointAnswers;
	}
	
	
	

	
	
}
