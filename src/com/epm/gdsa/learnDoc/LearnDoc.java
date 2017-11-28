package com.epm.gdsa.learnDoc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "GDSA_EPMS_LEARNDOC")
public class LearnDoc {
	
	@Id
	@Column(name="GDSA_LEARNDOC_ID")
	@SequenceGenerator(name="learnDoc_sequence",sequenceName="GDSA_LEARNDOC_SEQUENCE",allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="learnDoc_sequence") 
	private Integer learnDocId;
	
	@Column(name="GDSA_LEARNDOC_CONTENT")
	private String content;
	
	@Column(name="GDSA_LEARNDOC_TYPE")
	private Integer type;

	public Integer getLearnDocId() {
		return learnDocId;
	}

	public void setLearnDocId(Integer learnDocId) {
		this.learnDocId = learnDocId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
}
