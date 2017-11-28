package com.epm.gdsa.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;

@Component(value="specificationAction")
@Scope(value="prototype")
public class SpecificationAction extends ActionSupport {
	
	@Autowired
	private SpecificationService specificationService;
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		specificationService.add();
		
		return NONE;
	}

	public SpecificationService getSpecificationService() {
		return specificationService;
	}

	public void setSpecificationService(SpecificationService specificationService) {
		this.specificationService = specificationService;
	}
}
