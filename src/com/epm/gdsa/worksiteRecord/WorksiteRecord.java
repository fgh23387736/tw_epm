package com.epm.gdsa.worksiteRecord;

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

import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;

@Entity
@Table(name = "GDSA_EPMS_WORKSITERECORD")
public class WorksiteRecord {
	
	@Id
	@Column(name="GDSA_WORKSITERECORD_ID")
	@SequenceGenerator(name="worksiteRecord_sequence",sequenceName="GDSA_WORKSITERECORD_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="worksiteRecord_sequence") 
	private Integer worksiteRecordId;
	
	@Column(name="GDSA_WORKSITERECORD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;		//日期
	
	@Column(name="GDSA_WORKSITERECORD_SIZE")
	private Float size;		//大小

	@Column(name="GDSA_WORKSITERECORD_POSITION")
	private String position;	//位置
	
	@Column(name="GDSA_WORKSITERECORD_THUMBNAIL")
	private String thumbnail; 	//缩略图
	
	@Column(name="GDSA_WORKSITERECORD_REMARKS")
	private String remarks; 	//缩略图
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_WORKSITERECORD_USER")
	private User user; 	//上传者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_WORKSITERECORD_PROJECT")
	private Project project; 	//项目

	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getWorksiteRecordId() {
		return worksiteRecordId;
	}

	public void setWorksiteRecordId(Integer worksiteRecordId) {
		this.worksiteRecordId = worksiteRecordId;
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

	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
