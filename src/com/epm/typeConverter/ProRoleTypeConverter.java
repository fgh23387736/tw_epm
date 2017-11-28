package com.epm.typeConverter;

import java.util.Map;

import com.epm.gdsa.proRole.ProRole;
import com.epm.gdsa.project.Project;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class ProRoleTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
		
        if (toType == ProRole.class) {
        	String theValue = ((String[])value)[0];
		    Integer params = new Integer(theValue) ;
		    ProRole proRole = new ProRole();
		    proRole.setProRoleId(params);
		    return proRole;  
		} else if (toType == Integer.class) { 
		    System.out.println(value);  
		    ProRole proRole = (ProRole) value;  
		    return proRole.getProRoleId();  
		}
        return null;  
    } 
}
