package com.epm.gdsa.pointAnswer;

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
import com.epm.gdsa.pointProblem.PointProblem;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.sign.Sign;
import com.epm.gdsa.specification.Specification;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.worksiteRecord.WorksiteRecord;

@Entity
@Table(name = "GDSA_EPMS_POINT_ANSWER")
public class PointAnswer {
	
	@Id
	@Column(name="GDSA_POINT_ANSWER_ID")
	@SequenceGenerator(name="pointAnswer_sequence",sequenceName="GDSA_POINT_ANSWER_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pointAnswer_sequence")  
	private Integer pointAnswerId;
	
	@Column(name="GDSA_POINT_ANSWER_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date date;//开始时间
	
	@Column(name="GDSA_POINT_ANSWER_ANSWER")
	private String answer;//项目内容
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_POINT_ANSWER_USER")
	private User user; 	//上传者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_POINT_ANSWER_PROBLEM")
	private PointProblem pointProblem; 	//上传者
	

	public Integer getPointAnswerId() {
		return pointAnswerId;
	}

	public void setPointAnswerId(Integer pointAnswerId) {
		this.pointAnswerId = pointAnswerId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PointProblem getPointProblem() {
		return pointProblem;
	}

	public void setPointProblem(PointProblem pointProblem) {
		this.pointProblem = pointProblem;
	}

	
	
	

	
	
}
