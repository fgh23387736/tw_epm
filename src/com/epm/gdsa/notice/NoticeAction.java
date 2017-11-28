package com.epm.gdsa.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;

@Component(value="noticeAction")
@Scope(value="prototype")
public class NoticeAction extends ActionSupport {
	
	@Autowired
	private NoticeService noticeService;
	
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		noticeService.add();
		
		return NONE;
	}

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
}
