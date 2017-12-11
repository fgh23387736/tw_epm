package com.epm.gdsa.material;

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
@Table(name = "GDSA_EPMS_MATERIAL")
public class Material {
	
	@Id
	@Column(name="GDSA_MATERIAL_ID")
	@SequenceGenerator(name="material_sequence",sequenceName="GDSA_MATERIAL_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="material_sequence") 
	private Integer materialId;
	
	@Column(name="GDSA_MATERIAL_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;		//日期
	
	@Column(name="GDSA_MATERIAL_PRICE")
	private Float price;		//价格

	@Column(name="GDSA_MATERIAL_NAME")
	private String name;	//名称
	
	@Column(name="GDSA_MATERIAL_TYPE")
	private String type;	//类型
	
	@Column(name="GDSA_MATERIAL_NUMBER")
	private Integer number;	//数量
	
	@Column(name="GDSA_MATERIAL_UNIT")
	private String unit; 	//单位
	
	@Column(name="GDSA_MATERIAL_PHOTO")
	private String photo; 	//照片
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_MATERIAL_BUYER")
	private User buyer; 	//购入者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_MATERIAL_SUBMITTER")
	private User submitter; 	//报销者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_MATERIAL_CHECKER")
	private User checker; 	//核实者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_MATERIAL_SELLER")
	private User seller; 	//销售者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_MATERIAL_USER")
	private User user; 	//上传者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_MATERIAL_PROJECT")
	private Project project; 	//上传者

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getSubmitter() {
		return submitter;
	}

	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}

	public User getChecker() {
		return checker;
	}

	public void setChecker(User checker) {
		this.checker = checker;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
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
