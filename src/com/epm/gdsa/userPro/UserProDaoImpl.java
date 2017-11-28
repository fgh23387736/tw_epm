package com.epm.gdsa.userPro;

import java.util.ArrayList;
import java.util.Date;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.gdsa.userPro.UserPro;
import com.epm.gdsa.userPro.UserProDao;

@Component(value="userProDaoImpl")
public class UserProDaoImpl implements UserProDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public UserPro getById(Integer id) {
		return hibernateTemplate.get(UserPro.class, id);
	}
	
	@Override
	public UserPro add(UserPro userPro) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(userPro);
		System.out.println("Id:"+userPro.getUserProId());
		return userPro;
	}
	
	@Override
	public void update(UserPro userPro) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(userPro);
		System.out.println("dao");
	}
	
	@Override
	public void delete(UserPro userPro) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(userPro);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<UserPro> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		//criteria.setProjection(null);
		List<UserPro> newUserPros = new ArrayList<UserPro>();
		if(page != null && pageSize != null){
			newUserPros = (List<UserPro>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newUserPros = (List<UserPro>) hibernateTemplate.findByCriteria(criteria);
		}
		return newUserPros;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserPro.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("userProId").in( ids ) );
		}
		return criteria;
	}


	@Override
	public DetachedCriteria getCriteriaByProjectAndUserName(UserPro userPro,
			String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserPro.class);
		ProjectionList pList = Projections.projectionList();  
		pList = pList.add(Projections.property("userProId"),"userProId");  
		pList = pList.add(Projections.property("user"),"user");
		pList = pList.add(Projections.property("project"),"project");  
		pList = pList.add(Projections.property("proRole"),"proRole");
		criteria.setProjection(pList);
		criteria.setResultTransformer(Transformers.aliasToBean(UserPro.class));  
		criteria.createAlias("user", "u");
		criteria.add(Property.forName("project").eq(userPro.getProject()));
		criteria.add(Property.forName("u.name").like("%"+userName+"%"));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProjectAndUser(UserPro userPro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserPro.class);
		criteria.add(Property.forName("project").eq(userPro.getProject()));
		criteria.add(Property.forName("user").eq(userPro.getUser()));
		return criteria;
	}



	
}
