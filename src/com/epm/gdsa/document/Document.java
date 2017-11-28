package com.epm.gdsa.document;

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
@Table(name = "GDSA_EPMS_DOCUMENT")
public class Document {
	
	@Id
	@Column(name="GDSA_DOCUMENT_ID")
	@SequenceGenerator(name="document_sequence",sequenceName="GDSA_DOCUMENT_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="document_sequence")  
	private Integer documentId;
	
	@Column(name="GDSA_DOCUMENT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;		//日期
	
	@Column(name="GDSA_DOCUMENT_SIZE")
	private Float size;		//大小

	@Column(name="GDSA_DOCUMENT_NAME")
	private String name;	//名称
	
	@Column(name="GDSA_DOCUMENT_UNIT")
	private String unit; 	//单位
	
	@Column(name="GDSA_DOCUMENT_CONTENT")
	private String content; 	//内容
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_DOCUMENT_USER")
	private User user; 	//发布人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_DOCUMENT_PROJECT")
	private Project project; 	//项目

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getSize() {
		return size;
	}

	public void setSize(Float size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
