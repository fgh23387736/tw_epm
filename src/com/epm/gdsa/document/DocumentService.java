package com.epm.gdsa.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component(value="documentService")
public class DocumentService {
	
	@Autowired
	private DocumentDao documentDao;

	
	
	public DocumentDao getDocumentDao() {
		return documentDao;
	}



	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}



	public void add() {
		System.out.println("service....");
		documentDao.add();
		
	}
}
