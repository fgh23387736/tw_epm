package com.epm.gdsa.worksiteRecord;

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

@Component(value="worksiteRecordDaoImpl")
public class WorksiteRecordDaoImpl implements WorksiteRecordDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public WorksiteRecord getById(Integer id) {
		return hibernateTemplate.get(WorksiteRecord.class, id);
	}
	
	@Override
	public WorksiteRecord add(WorksiteRecord worksiteRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(worksiteRecord);
		System.out.println("Id:"+worksiteRecord.getWorksiteRecordId());
		return worksiteRecord;
	}
	
	@Override
	public void update(WorksiteRecord worksiteRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(worksiteRecord);
		System.out.println("dao");
	}
	
	@Override
	public void delete(WorksiteRecord worksiteRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(worksiteRecord);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<WorksiteRecord> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<WorksiteRecord> newWorksiteRecords = new ArrayList<WorksiteRecord>();
		if(page != null && pageSize != null){
			newWorksiteRecords = (List<WorksiteRecord>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newWorksiteRecords = (List<WorksiteRecord>) hibernateTemplate.findByCriteria(criteria);
		}
		return newWorksiteRecords;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(WorksiteRecord.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("worksiteRecordId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProject(WorksiteRecord worksiteRecord) {
		DetachedCriteria criteria = DetachedCriteria.forClass(WorksiteRecord.class);
		criteria.add(Property.forName("project").eq(worksiteRecord.getProject()));
		return criteria;
	}


	
}
