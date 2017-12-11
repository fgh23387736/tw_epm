package com.epm.gdsa.proRole;

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
import com.epm.enums.ProRoleAuthEnum;
import com.epm.gdsa.noticeRole.NoticeRole;
import com.epm.gdsa.proRoleAuth.ProRoleAuth;
import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;

@Entity
@Table(name = "GDSA_USER_PRO_ROLE")
public class ProRole {
	
	@Id
	@Column(name="GDSA_USER_PRO_ROLE_ID")
	@SequenceGenerator(name="proRole_sequence",sequenceName="GDSA_USER_PRO_ROLE_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="proRole_sequence") 
	private Integer proRoleId;
	
	@Column(name="GDSA_USER_PRO_ROLE_NAME")
	private String name;		//名称
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_USER_PRO_ROLE_PROJECT")
	private Project project; 	//项目

	@OneToMany(mappedBy="proRole",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<UserPro> userPros = new HashSet<UserPro>();
	
	@OneToMany(mappedBy="proRole",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<NoticeRole> noticeRoles = new HashSet<NoticeRole>();
	
	@OneToMany(mappedBy="proRole",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<ProRoleAuth> proRoleAuths = new HashSet<ProRoleAuth>();
	
	public Set<ProRoleAuth> getProRoleAuths() {
		return proRoleAuths;
	}

	public void setProRoleAuths(Set<ProRoleAuth> proRoleAuths) {
		this.proRoleAuths = proRoleAuths;
	}

	public Set<NoticeRole> getNoticeRoles() {
		return noticeRoles;
	}

	public void setNoticeRoles(Set<NoticeRole> noticeRoles) {
		this.noticeRoles = noticeRoles;
	}
	
	public Integer getProRoleId() {
		return proRoleId;
	}

	public void setProRoleId(Integer proRoleId) {
		this.proRoleId = proRoleId;
	}
	
	public Set<UserPro> getUserPros() {
		return userPros;
	}

	public void setUserPros(Set<UserPro> userPros) {
		this.userPros = userPros;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	
	
	
	
}

