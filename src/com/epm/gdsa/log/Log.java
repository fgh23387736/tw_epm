package com.epm.gdsa.log;

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
@Table(name = "GDSA_EPMS_LOG")
public class Log {
	
	@Id
	@Column(name="GDSA_LOG_ID")
	@SequenceGenerator(name="log_sequence",sequenceName="GDSA_LOG_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="log_sequence") 
	private Integer logId;
	
	@Column(name="GDSA_LOG_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;		//日期
	
	@Column(name="GDSA_LOG_CONTENT")
	private String content; 	//内容
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_LOG_USER")
	private User user; 	//发布人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_LOG_PROJECT")
	private Project project; 	//项目

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
