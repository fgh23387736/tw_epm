package com.epm.typeConverter;

import java.util.Map;

import com.epm.gdsa.point.Point;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class PointTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
		
        if (toType == Point.class) {
        	String theValue = ((String[])value)[0];
        	System.out.println("user\n");
		    Integer params = new Integer(theValue) ;
		    Point point = new Point();
		    point.setPointId(params);
		    return point;  
		} else if (toType == Integer.class) { 
			System.out.println("user2\n");
		    System.out.println(value);  
		    Point point = (Point) value;  
		    return point.getPointId();  
		}
        return null;  
    } 
}
