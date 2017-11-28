package com.epm.gdsa.userPro;


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

import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;

@Entity
@Table(name = "GDSA_USER_PRO")
public class UserPro {
	
	@Id
	@Column(name="GDSA_USER_PRO_ID")
	@SequenceGenerator(name="userPro_sequence",sequenceName="GDSA_USER_PRO_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="userPro_sequence") 
	private Integer userProId;
		
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_USER_PRO_USER")
	private User user; 	//用户
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_USER_PRO_PROJECT")
	private Project project; 	//项目
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_USER_PRO_ROLE")
	private ProRole proRole;		//角色

	public Integer getUserProId() {
		return userProId;
	}

	public void setUserProId(Integer userProId) {
		this.userProId = userProId;
	}

	

	public ProRole getProRole() {
		return proRole;
	}

	public void setProRole(ProRole proRole) {
		this.proRole = proRole;
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
