package com.epm.typeConverter;

import java.util.Map;

import com.epm.gdsa.user.User;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class UserTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
		
        if (toType == User.class) {
        	String theValue = ((String[])value)[0];
        	System.out.println("user\n");
		    Integer params = new Integer(theValue) ;
		    User user = new User();
		    user.setUserId(params);
		    return user;  
		} else if (toType == Integer.class) { 
			System.out.println("user2\n");
		    System.out.println(value);  
		    User user = (User) value;  
		    return user.getUserId();  
		}
        return null;  
    } 
}
