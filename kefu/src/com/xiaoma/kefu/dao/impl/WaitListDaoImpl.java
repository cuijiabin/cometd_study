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
		String hql = "from WaitList t where (t.pId is null or t.pId = 0) and  t.styleId = :styleId ";
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
	
	/**
	 * 校验名称是否存在
	 * @param waitList
	 * @return
	 */
	public Integer validateName(WaitList waitList){
		Integer result = 1 ;
		Session session = getSession();
		if(waitList==null) return result;
		if(waitList.getId()!=null){
			//更新校验
			String hql = " from WaitList t where t.id != :id and t.styleId = :styleId and t.name = :name ";
			Query query = session.createQuery(hql);
			query.setInteger("id", waitList.getId());
			query.setInteger("styleId", waitList.getStyleId());
			query.setString("name", waitList.getName());
			result = query.list().size();
		}else{
			//新增
			String hql = " from WaitList t where t.styleId = :styleId and t.name = :name ";
			Query query = session.createQuery(hql);
			query.setInteger("styleId", waitList.getStyleId());
			query.setString("name", waitList.getName());
			result = query.list().size();
		}
		return	result;
	}
	
	/**
	 * 校验名称是否存在(二级菜单)
	 * @param waitList
	 * @return
	 * @Author: wangxingfei
	 * @Date: 2015年4月16日
	 */
	public Integer validateName2(WaitList waitList){
		Integer result = 1 ;
		Session session = getSession();
		if(waitList==null) return result;
		if(waitList.getId()!=null){
			//更新校验
			String hql = " from WaitList t where t.id != :id and t.pId = :pId and t.name = :name ";
			Query query = session.createQuery(hql);
			query.setInteger("id", waitList.getId());
			query.setInteger("pId", waitList.getpId());
			query.setString("name", waitList.getName());
			result = query.list().size();
		}else{
			//新增
			String hql = " from WaitList t where t.pId = :pId and t.name = :name ";
			Query query = session.createQuery(hql);
			query.setInteger("pId", waitList.getpId());
			query.setString("name", waitList.getName());
			result = query.list().size();
		}
		return	result;
	}
	
	/**
	 * 根据父id,删除菜单
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月16日
	 */
	public int deleteByPid(Integer id){
		Session session = getSession();
		String hql = "delete WaitList t where t.pId = :id ";
		Query query = session.createQuery(hql);
		query.setInteger("id", id);
		return query.executeUpdate();
	}
	/***
	 * 根据风格ID和父级ID获取List
	 * @param styleId
	 * @param id
	 * @return
	 */
	public List<WaitList> findListById(Integer styleId,Integer id){
		Session session = getSession();
		String hql = "from WaitList a where a.styleId=:styleId and a.pId = :id ";
		Query query = session.createQuery(hql);
		query.setInteger("styleId", styleId);
		query.setInteger("id", id);
		return query.list();
	}
	
}
