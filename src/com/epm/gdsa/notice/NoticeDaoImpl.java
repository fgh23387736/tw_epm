package com.epm.gdsa.notice;

import java.util.ArrayList;
import java.util.Date;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.epm.gdsa.proRole.ProRole;
import com.epm.utils.HibernateUtils;

@Component(value="noticeDaoImpl")
public class NoticeDaoImpl implements NoticeDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Notice getById(Integer id) {
		return hibernateTemplate.get(Notice.class, id);
	}
	
	@Override
	public Notice add(Notice notice) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(notice);
		System.out.println("Id:"+notice.getNoticeId());
		return notice;
	}
	
	@Override
	public void update(Notice notice) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(notice);
		System.out.println("dao");
	}
	
	@Override
	public void delete(Notice notice) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(notice);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Notice> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Notice> newNotices = new ArrayList<Notice>();
		if(page != null && pageSize != null){
			newNotices = (List<Notice>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newNotices = (List<Notice>) hibernateTemplate.findByCriteria(criteria);
		}
		return newNotices;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Notice.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("noticeId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProject(Notice notice) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Notice.class);
		criteria.add(Property.forName("project").eq(notice.getProject()));
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByUser(Notice notice) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Notice.class);
		criteria.add(Property.forName("user").eq(notice.getUser()));
		return criteria;
	}
	
	@Override
	public DetachedCriteria getCriteriaByProjectAndName(Notice notice) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Notice.class);
		criteria.add(Property.forName("project").eq(notice.getProject()));
		criteria.add(Property.forName("name").like("%"+notice.getName()+"%"));
		return criteria;
	}
	
}
