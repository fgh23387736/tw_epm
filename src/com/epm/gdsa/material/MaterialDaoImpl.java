package com.epm.gdsa.material;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;


@Component(value="materialDaoImpl")
public class MaterialDaoImpl implements MaterialDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Material getById(Integer id) {
		return hibernateTemplate.get(Material.class, id);
	}
	
	@Override
	public Material add(Material material) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(material);
		System.out.println("Id:"+material.getMaterialId());
		return material;
	}
	
	@Override
	public void update(Material material) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(material);
		System.out.println("dao");
	}
	
	@Override
	public void delete(Material material) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(material);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Material> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Material> newMaterials = new ArrayList<Material>();
		if(page != null && pageSize != null){
			newMaterials = (List<Material>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newMaterials = (List<Material>) hibernateTemplate.findByCriteria(criteria);
		}
		return newMaterials;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Material.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("materialId").in( ids ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByProject(Material material) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Material.class);
		criteria.add(Property.forName("project").eq(material.getProject()));
		return criteria;
	}


	
}
