package com.epm.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
  






import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  

public class PublicUtils {
	public static String getMD5(String str) {
		try {
		// 生成一个MD5加密计算摘要
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 计算md5函数
		md.update(str.getBytes());
		// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
		// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
		return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
		 
		}
		return null;
	}
	
	
	public static Integer[] getIdsByString(String ids,String regex){
		if(ids == null || ids.equals("")){
			return null;
		}
		String[] idsArr = ids.split(regex);
		Integer[] theIds = new Integer[idsArr.length];
		for(int i = 0; i < idsArr.length; i++) {
			theIds[i] = Integer.parseInt(idsArr[i]);
		}
		return theIds;
	}
	
	
	public static Map<String, Object> generateImage(String imgStr, String path) {

		//data:image/jpeg;base64,
		if(imgStr == null)
			return null;
		String[] imgStrings = imgStr.split(",");
		String type = imgStrings[0].split("\\/")[1];
		type = type.split(";")[0];
		String name =""+ new Date().getTime()+PublicUtils.getRandom(0, 9)+"."+type;
		System.out.println(name);
		String realPath = ServletActionContext.getServletContext().getRealPath(path); 
		realPath += "/" + name;
		path = "/tw_epm"+path+"/" + name;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(imgStrings[1]);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(realPath);
			out.write(b);
			out.flush();
			out.close();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("path", path);
			map.put("size", b.length);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int getRandom(int min,int max){
		Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
}
