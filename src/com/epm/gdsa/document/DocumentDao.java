package com.epm.gdsa.document;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface DocumentDao {
	public Document add(Document document);
	public void update(Document document);
	public void delete(Document document);
	public Document getById(Integer id);
	public List<Document> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByProject(Document document);
	public DetachedCriteria getCriteriaByProjectAndName(Document document);
}
