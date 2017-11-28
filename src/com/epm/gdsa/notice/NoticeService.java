package com.epm.gdsa.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component(value="noticeService")
public class NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;

	
	
	public NoticeDao getNoticeDao() {
		return noticeDao;
	}



	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}



	public void add() {
		System.out.println("service....");
		noticeDao.add();
		
	}
}
