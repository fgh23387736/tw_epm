package com.epm.gdsa.noticeRole;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.epm.gdsa.user.User;
import com.epm.gdsa.noticeRole.NoticeRole;
import com.epm.gdsa.project.Project;


public interface NoticeRoleDao {
	public NoticeRole add(NoticeRole noticeRole);
	public void update(NoticeRole noticeRole);
	public void delete(NoticeRole noticeRole);
	public NoticeRole getById(Integer id);
	public List<NoticeRole> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByNotice(NoticeRole noticeRole);
	public DetachedCriteria getCriteriaByNoticeAndProRole(NoticeRole noticeRole);
	public DetachedCriteria getCriteriaByProRole(NoticeRole noticeRole);
}
