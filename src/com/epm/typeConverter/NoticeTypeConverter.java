package com.epm.typeConverter;

import java.util.Map;

import com.epm.gdsa.notice.Notice;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class NoticeTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
		
        if (toType == Notice.class) {
        	String theValue = ((String[])value)[0];
        	System.out.println("user\n");
		    Integer params = new Integer(theValue) ;
		    Notice notice = new Notice();
		    notice.setNoticeId(params);
		    return notice;  
		} else if (toType == Integer.class) { 
			System.out.println("user2\n");
		    System.out.println(value);  
		    Notice notice = (Notice) value;  
		    return notice.getNoticeId();  
		}
        return null;  
    } 
}
