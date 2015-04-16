package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.WaitListDao;
import com.xiaoma.kefu.model.WaitList;

/**
 *  等待菜单列表	daoImpl
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月3日下午4:42:35
**********************************
 */
@Repository("waitListDaoImpl")
public class WaitListDaoImpl extends BaseDaoImpl<WaitList> implements WaitListDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<WaitList> findByNameLike(String waitListName) {
		Session session = getSession();
		String hql = "from WaitList t where t.name LIKE :waitListName ";
		Query query = session.createQuery(hql);
		query.setString("waitListName", "%" + waitListName + "%");
		return	query.list();
	}
	
	/**
	 * 查找风格下的 一级菜单
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaitList> findOneLev(Integer styleId) {
		Session session = getSession();
		String hql = "from WaitList t where t.styleId = :styleId ";
		Query query = session.createQuery(hql);
		query.setInteger("styleId", styleId);
		return	query.list();
	}
	/**
	 * 根据pid,查找二级菜单
	* @Description: TODO
	* @param pId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaitList> findByPid(Integer pId){
		Session session = getSession();
		String hql = "from WaitList t where t.pId = :pId ";
		Query query = session.createQuery(hql);
		query.setInteger("pId", pId);
		return	query.list();
	}

	
}
