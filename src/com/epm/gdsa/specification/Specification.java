package com.epm.gdsa.specification;

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
@Table(name = "GDSA_EPMS_SPECIFICATION")
public class Specification {
	
	@Id
	@Column(name="GDSA_SPECIFICATION_ID")
	@SequenceGenerator(name="specification_sequence",sequenceName="GDSA_SPECIFICATION_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="specification_sequence") 
	private Integer specificationId;
	
	@Column(name="GDSA_SPECIFICATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;		//日期
	
	@Column(name="GDSA_SPECIFICATION_CONTENT")
	private String contentUrl; 	//内容
	
	@Column(name="GDSA_SPECIFICATION_NAME")
	private String name; 	//内容
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_SPECIFICATION_PROJECT")
	private Project project; 	//单位
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_SPECIFICATION_USER")
	private User user; 	//单位

	public Integer getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(Integer specificationId) {
		this.specificationId = specificationId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
