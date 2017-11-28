package com.epm.gdsa.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;

@Component(value="documentAction")
@Scope(value="prototype")
public class DocumentAction extends ActionSupport {
	
	@Autowired
	private DocumentService documentService;
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		documentService.add();
		
		return NONE;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
}
