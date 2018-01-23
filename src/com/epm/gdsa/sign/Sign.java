package com.epm.gdsa.sign;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import oracle.sql.BFILE;

import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;

@Entity
@Table(name = "GDSA_EPMS_SIGN")
public class Sign {
	
	@Id
	@Column(name="GDSA_SIGN_ID")
	@SequenceGenerator(name="sign_sequence",sequenceName="GDSA_SIGN_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sign_sequence") 
	private Integer signId;
	
	@Column(name="GDSA_SIGN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;		//日期
	
	@Column(name="GDSA_SIGN_LONGITUDE")
	private Double longitude;//经度
	
	@Column(name="GDSA_SIGN_LATITUDE")
	private Double latitude;//纬度
	
	@Column(name="GDSA_SIGN_SIGNCODE")
	private String signCode;//签到码
	
	@Column(name="GDSA_SIGN_TYPE")
	private Integer type;//签到方式
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_SIGN_USER")
	private User user; 	//单位
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_SIGN_PROJECT")
	private Project project; 	//项目

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSignCode() {
		return signCode;
	}

	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getSignId() {
		return signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
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
	
	
	
}
