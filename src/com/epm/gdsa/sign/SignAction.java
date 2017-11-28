package com.epm.gdsa.sign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;

@Component(value="signAction")
@Scope(value="prototype")
public class SignAction extends ActionSupport {
	
	@Autowired
	private SignService signService;
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		signService.add();
		
		return NONE;
	}

	public SignService getSignService() {
		return signService;
	}

	public void setSignService(SignService signService) {
		this.signService = signService;
	}
}
