package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.BusiGroupDao;
import com.xiaoma.kefu.model.BusiGroup;

/**
 * *********************************
* @Description: 业务分组	daoImpl
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:29:12
**********************************
 */
@Repository("busiGroupDaoImpl")
public class BusiGroupDaoImpl extends BaseDaoImpl<BusiGroup> implements BusiGroupDao {
	
	/**
	 * 获取风格下的 业务分组list
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@SuppressWarnings("unchecked")
	public List<BusiGroup> findByStyleId(Integer styleId){
		Session session = getSession();
		String hql = "from BusiGroup t where t.styleId = :styleId ";
		Query query = session.createQuery(hql);
		query.setInteger("styleId",styleId);
		return	query.list();
	}
	
	/**
	 * 校验名称是否存在
	* @Description: TODO
	* @param group
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer validateName(BusiGroup group){
		Integer result = 0 ;
		Session session = getSession();
		if(group==null) return result;
		if(group.getId()!=null){
			//更新校验
			String hql = " from BusiGroup t where t.id != :id and t.name = :name and t.styleId= :styleId ";
			Query query = session.createQuery(hql);
			query.setInteger("id", group.getId());
			query.setString("name", group.getName());
			query.setInteger("styleId", group.getStyleId());
			result = query.list().size();
		}else{
			//新增
			String hql = " from BusiGroup t where t.name = :name and t.styleId= :styleId ";
			Query query = session.createQuery(hql);
			query.setString("name", group.getName());
			query.setInteger("styleId", group.getStyleId());
			result = query.list().size();
		}
		return	result;
	}

}
