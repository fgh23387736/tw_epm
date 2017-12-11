package com.epm.gdsa.proRoleAuth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Enumerated;

import com.epm.enums.ProRoleAuthEnum;
import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.project.Project;

@Entity
@Table(name = "GDSA_PRO_ROLE_AUTH")
public class ProRoleAuth {
	@Id
	@Column(name="GDSA_PRO_ROLE_AUTH_ID")
	@SequenceGenerator(name="proRoleAuth_sequence",sequenceName="GDSA_PRO_ROLE_AUTH_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="proRoleAuth_sequence")  
	private Integer proRoleAuthId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_PRO_ROLE_AUTH_ROLE")
	private ProRole proRole; 	//角色
	
	@Column(name="GDSA_PRO_ROLE_AUTH_AUTH")
	@Enumerated(EnumType.STRING)
	private ProRoleAuthEnum auth; 	//权限

	public Integer getProRoleAuthId() {
		return proRoleAuthId;
	}

	public void setProRoleAuthId(Integer proRoleAuthId) {
		this.proRoleAuthId = proRoleAuthId;
	}

	public ProRole getProRole() {
		return proRole;
	}

	public void setProRole(ProRole proRole) {
		this.proRole = proRole;
	}

	public ProRoleAuthEnum getAuth() {
		return auth;
	}

	public void setAuth(ProRoleAuthEnum auth) {
		this.auth = auth;
	}

	
	
	
}
