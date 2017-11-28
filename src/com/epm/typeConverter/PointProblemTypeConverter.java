package com.epm.typeConverter;

import java.util.Map;
import com.epm.gdsa.pointProblem.PointProblem;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class PointProblemTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
		
        if (toType == PointProblem.class) {
        	String theValue = ((String[])value)[0];
		    Integer params = new Integer(theValue) ;
		    PointProblem pointProblem = new PointProblem();
		    pointProblem.setPointProblemId(params);
		    return pointProblem;  
		} else if (toType == Integer.class) {  
		    PointProblem pointProblem = (PointProblem) value;  
		    return pointProblem.getPointProblemId();  
		}
        return null;  
    } 
}
