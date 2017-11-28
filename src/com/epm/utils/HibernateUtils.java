package com.epm.utils;

import java.sql.Clob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import oracle.sql.CLOB;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class HibernateUtils {
	static Configuration cfg = null;
	static SessionFactory sessionFactory = null;
	//闈欐�佷唬鐮佸潡瀹炵幇
	static {
		//鍔犺浇鏍稿績閰嶇疆鏂囦欢
		cfg = new Configuration().configure();
		sessionFactory = cfg.buildSessionFactory();
	}
	
	//鎻愪緵杩斿洖涓庢湰鍦扮嚎绋嬪府鐨剆ession鐨勬柟娉�
	public static Session getSessionobject() {
		return sessionFactory.getCurrentSession();
	}
	
	//鎻愪緵鏂规硶杩斿洖sessionFactory
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
