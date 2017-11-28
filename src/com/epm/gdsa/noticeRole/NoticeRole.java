package com.epm.gdsa.noticeRole;

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

import com.epm.gdsa.notice.Notice;
import com.epm.gdsa.proRole.ProRole;

@Entity
@Table(name = "GDSA_EPMS_NOTICE_ROLE")
public class NoticeRole {
	
	@Id
	@Column(name="GDSA_NOTICE_ROLE_ID")
	@SequenceGenerator(name="noticeRole_sequence",sequenceName="GDSA_NOTICE_ROLE_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="noticeRole_sequence") 
	private Integer noticeRoleId;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_NOTICE_ROLE_NOTICE")
	private Notice notice; 	//公告
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GDSA_NOTICE_ROLE_ROLE")
	private ProRole proRole; 	//角色

	public Integer getNoticeRoleId() {
		return noticeRoleId;
	}

	public void setNoticeRoleId(Integer noticeRoleId) {
		this.noticeRoleId = noticeRoleId;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public ProRole getProRole() {
		return proRole;
	}

	public void setProRole(ProRole proRole) {
		this.proRole = proRole;
	}

	
	
	
	
}
