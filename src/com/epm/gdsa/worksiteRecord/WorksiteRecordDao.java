package com.epm.gdsa.worksiteRecord;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface WorksiteRecordDao {
	public WorksiteRecord add(WorksiteRecord worksiteRecord);
	public void update(WorksiteRecord worksiteRecord);
	public void delete(WorksiteRecord worksiteRecord);
	public WorksiteRecord getById(Integer id);
	public List<WorksiteRecord> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProject(WorksiteRecord worksiteRecord);
}
