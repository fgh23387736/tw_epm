package com.epm.gdsa.learnDoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;

@Component(value="learnDocAction")
@Scope(value="prototype")
public class LearnDocAction extends ActionSupport {
	
	@Autowired
	private LearnDocService learnDocService;
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		learnDocService.add();
		
		return NONE;
	}

	public LearnDocService getLearnDocService() {
		return learnDocService;
	}

	public void setLearnDocService(LearnDocService learnDocService) {
		this.learnDocService = learnDocService;
	}
}
