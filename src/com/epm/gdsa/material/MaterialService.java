package com.epm.gdsa.material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gdsa.project.Project;
import com.epm.gdsa.user.User;
import com.epm.utils.PublicUtils;

@Transactional
@Component(value="materialService")
public class MaterialService {
	
	@Autowired
	private MaterialDao materialDao;

	public MaterialDao getMaterialDao() {
		return materialDao;
	}

	public void setMaterialDao(MaterialDao materialDao) {
		this.materialDao = materialDao;
	}
	
	public List<Map<String, Object>> getMaterialByKeys(String keys,List<Material> materials){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"materialId",
					"name",
					"date",
					"price",
					"type",
					"photo",
					"number",
					"unit",
					"buyer",
					"checker",
					"submitter",
					"seller",
					"project",
					"user"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Material material : materials) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(material,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Material material,String str){
		Map<String, Object> theMap;
		User theUser;
		switch (str) {
			case "materialId":
				return material.getMaterialId();
			case "name":
				return material.getName();
			case "date":
				return material.getDate();
			case "price":
				return material.getPrice();
			case "type":
				return material.getType();
			case "photo":
				return material.getPhoto();
			case "number":
				return material.getNumber();
			case "unit":
				return material.getUnit();
			case "buyer":
				theMap = new HashMap<String, Object>();
				theUser = material.getBuyer();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "checker":
				theMap = new HashMap<String, Object>();
				theUser = material.getChecker();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "submitter":
				theMap = new HashMap<String, Object>();
				theUser = material.getSubmitter();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "seller":
				theMap = new HashMap<String, Object>();
				theUser = material.getSeller();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "user":
				theMap = new HashMap<String, Object>();
				theUser = material.getUser();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				return theMap;
			case "project":
				theMap = new HashMap<String, Object>();
				Project theProject = material.getProject();
				theMap.put("projectId", theProject.getProjectId());
				theMap.put("name", theProject.getName());
				return theMap;
			default:
				return null;
		}
	}
	
	public Material getNewMaterialByKeys(Material material,Material newMaterial,String keys){
		if(material == null || newMaterial == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					material.setName(newMaterial.getName());
					break;
				case "date":
					material.setDate(newMaterial.getDate());
					break;
				case "price":
					material.setPrice(newMaterial.getPrice());
					break;
				case "type":
					material.setType(newMaterial.getType());
					break;
				/*case "photo":
					material.setPhoto(newMaterial.getPhoto());
					break;*/
				case "number":
					material.setNumber(newMaterial.getNumber());
					break;
				case "unit":
					material.setUnit(newMaterial.getUnit());
					break;
				case "buyer":
					material.setBuyer(newMaterial.getBuyer());
					break;
				case "checker":
					material.setChecker(newMaterial.getChecker());
					break;
				case "submitter":
					material.setSubmitter(newMaterial.getSubmitter());
					break;
				case "seller":
					material.setSeller(newMaterial.getSeller());
					break;
				default:
					
			}
		}
		
		return material;
		
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = 0;
		List<Material> materials = materialDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getMaterialByKeys(keys,materials);
		if(page != null && pageSize != null){
			total = materialDao.getAllCountByCriteria(criteria);
		}else{
			total = list.size();
		}
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Material add(Material material) {
		return materialDao.add(material);
		
	}
	

	public Map<String, Object> getMaterialByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = materialDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Material material, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			Material material2 = materialDao.getById(integer);
			if(material2 != null){
				Project theProject = material2.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(!theProject.getUser().getUserId().equals(loginUser.getUserId()) && !material2.getUser().getUserId().equals(loginUser.getUserId())){
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
					}
				}else{
					//具有权限时的业务逻辑
					material2 = getNewMaterialByKeys(material2,material,keys);
					materialDao.update(material2);
				}
				
			}else{
				map.put("code", 404);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据修改失败;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败;");
				}
			}
		}
		
		map.put("result", theMap);
		return map;
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			Material material2 = materialDao.getById(integer);
			if(material2 != null){
				Project theProject = material2.getProject();
				if(theProject == null){
					map.put("code", 404);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:项目不存在;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:项目不存在;");
					}
				}else if(!theProject.getUser().getUserId().equals(loginUser.getUserId()) && !material2.getUser().getUserId().equals(loginUser.getUserId())){
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据修改失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败:您不具有权限;");
					}
				}else{
					//具有权限时的业务逻辑
					if(PublicUtils.deleteFileFromServer(material2.getPhoto().replace("/tw_epm", ""))){
						materialDao.delete(material2);
					}else{
						map.put("code", 400);
						if(theMap == null){
							theMap = new HashMap<String, Object>();
							theMap.put("error", "id为"+integer+"的文件删除失败;");
						}else{
							theMap.put("error",theMap.get("error")+"id为"+integer+"的文件删除失败;");
						}
						
					}
				}
			}else{
				map.put("code", 404);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据删除失败：数据不存在;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败：数据不存在;");
				}
			}
		}
		
		map.put("result", theMap);
		return map;
	}

	public Map<String, Object> getByProject(String keys, Integer page,
			Integer pageSize, Material material) {
		DetachedCriteria criteria = materialDao.getCriteriaByProject(material);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}	
	





}
