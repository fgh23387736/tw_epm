package com.epm.gdsa.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;

@Component(value="materialAction")
@Scope(value="prototype")
public class MaterialAction extends ActionSupport {
	
	@Autowired
	private MaterialService materialService;
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		materialService.add();
		
		return NONE;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
}
