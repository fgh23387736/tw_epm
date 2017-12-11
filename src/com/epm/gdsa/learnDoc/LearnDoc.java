package com.epm.gdsa.learnDoc;

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

import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;

@Entity
@Table(name = "GDSA_EPMS_LEARNDOC")
public class LearnDoc {
	
	@Id
	@Column(name="GDSA_LEARNDOC_ID")
	@SequenceGenerator(name="learnDoc_sequence",sequenceName="GDSA_LEARNDOC_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="learnDoc_sequence") 
	private Integer learnDocId;
	
	@Column(name="GDSA_LEARNDOC_CONTENT")
	private String contentUrl;
	
	@Column(name="GDSA_LEARNDOC_TYPE")
	private Integer type;
	
	@Column(name="GDSA_LEARNDOC_NAME")
	private String name;	//名称
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_LEARNDOC_USER")
	private User user; 	//发布人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_LEARNDOC_PROJECT")
	private Project project; 	//项目
	
	public Integer getLearnDocId() {
		return learnDocId;
	}

	public void setLearnDocId(Integer learnDocId) {
		this.learnDocId = learnDocId;
	}


	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
	
	
}
