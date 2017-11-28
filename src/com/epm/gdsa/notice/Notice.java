package com.epm.gdsa.notice;

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
import com.epm.gdsa.material.Material;
import com.epm.gdsa.noticeRole.NoticeRole;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;

@Entity
@Table(name = "GDSA_EPMS_NOTICE")
public class Notice {
	
	@Id
	@Column(name="GDSA_NOTICE_ID")
	@SequenceGenerator(name="notice_sequence",sequenceName="GDSA_NOTICE_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="notice_sequence") 
	private Integer noticeId;
	
	@Column(name="GDSA_NOTICE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;		//日期
	
	@Column(name="GDSA_NOTICE_CONTENT")
	private String content; 	//内容
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_NOTICE_USER")
	private User user; 	//发布者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_NOTICE_PROJECT")
	private Project project; 	//单位
	
	@OneToMany(mappedBy="notice",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<NoticeRole> noticeRoles = new HashSet<NoticeRole>();
	
	public Set<NoticeRole> getNoticeRoles() {
		return noticeRoles;
	}

	public void setNoticeRoles(Set<NoticeRole> noticeRoles) {
		this.noticeRoles = noticeRoles;
	}

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
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
