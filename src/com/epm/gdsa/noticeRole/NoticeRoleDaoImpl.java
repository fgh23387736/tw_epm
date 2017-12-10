package com.epm.gdsa.noticeRole;

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
import com.epm.gdsa.noticeRole.NoticeRole;
import com.epm.gdsa.noticeRole.NoticeRoleDao;

@Component(value="noticeRoleDaoImpl")
public class NoticeRoleDaoImpl implements NoticeRoleDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public NoticeRole getById(Integer id) {
		return hibernateTemplate.get(NoticeRole.class, id);
	}
	
	@Override
	public NoticeRole add(NoticeRole noticeRole) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(noticeRole);
		System.out.println("Id:"+noticeRole.getNoticeRoleId());
		return noticeRole;
	}
	
	@Override
	public void update(NoticeRole noticeRole) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(noticeRole);
		System.out.println("dao");
	}
	
	@Override
	public void delete(NoticeRole noticeRole) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(noticeRole);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<NoticeRole> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		//criteria.setProjection(null);
		List<NoticeRole> newNoticeRoles = new ArrayList<NoticeRole>();
		if(page != null && pageSize != null){
			newNoticeRoles = (List<NoticeRole>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newNoticeRoles = (List<NoticeRole>) hibernateTemplate.findByCriteria(criteria);
		}
		return newNoticeRoles;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NoticeRole.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("noticeRoleId").in( ids ) );
		}
		return criteria;
	}
	
	@Override
	public DetachedCriteria getCriteriaByNotice(NoticeRole noticeRole) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NoticeRole.class);
		criteria.add(Property.forName("notice").eq(noticeRole.getNotice()));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByNoticeAndProRole(NoticeRole noticeRole) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NoticeRole.class);
		criteria.add(Property.forName("notice").eq(noticeRole.getNotice()));
		criteria.add(Property.forName("proRole").eq(noticeRole.getProRole()));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProRole(NoticeRole noticeRole) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NoticeRole.class);
		criteria.add(Property.forName("proRole").eq(noticeRole.getProRole()));
		return criteria;
	}


	
}
