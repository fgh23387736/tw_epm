package com.epm.gdsa.learnDoc;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface LearnDocDao {
	public LearnDoc add(LearnDoc learnDoc);
	public void update(LearnDoc learnDoc);
	public void delete(LearnDoc learnDoc);
	public LearnDoc getById(Integer id);
	public List<LearnDoc> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProject(LearnDoc learnDoc);
	public DetachedCriteria getCriteriaByProjectAndName(LearnDoc learnDoc);
}
