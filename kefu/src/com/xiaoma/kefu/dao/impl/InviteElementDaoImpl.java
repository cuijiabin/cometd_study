package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.InviteElementDao;
import com.xiaoma.kefu.model.InviteElement;

/**
 * 邀请框元素	daoImpl
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:34
**********************************
 */
@Repository("inviteElementDaoImpl")
public class InviteElementDaoImpl extends BaseDaoImpl<InviteElement> implements InviteElementDao {
	
	/**
	 * 根据邀请框id,获取元素list
	* @Description: TODO
	* @param inviteId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月17日
	 */
	@SuppressWarnings("unchecked")
	public List<InviteElement> findByInviteId(Integer inviteId){
		Session session = getSession();
		String hql = "from InviteElement t where t.inviteId = :inviteId order by t.createDate ";
		Query query = session.createQuery(hql);
		query.setInteger("inviteId", inviteId);
		return	query.list();
	}
	
	/**
	 * 校验名称是否存在
	* @Description: TODO
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	@Override
	public Integer validateName(InviteElement inviteElement) {
		Integer result = 0 ;
		Session session = getSession();
		if(inviteElement==null) return result;
		if(inviteElement.getId()!=null){
			//更新校验
			String hql = " from InviteElement t where t.id != :id and t.name = :name and t.inviteId= :inviteId ";
			Query query = session.createQuery(hql);
			query.setInteger("id", inviteElement.getId());
			query.setString("name", inviteElement.getName());
			query.setInteger("inviteId", inviteElement.getInviteId());
			result = query.list().size();
		}else{
			//新增
			String hql = " from InviteElement t where t.name = :name and t.inviteId= :inviteId ";
			Query query = session.createQuery(hql);
			query.setString("name", inviteElement.getName());
			query.setInteger("inviteId", inviteElement.getInviteId());
			result = query.list().size();
		}
		return	result;
	}
	
	/**
	 * 获取邀请框的第一个元素, 就是外框
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月26日
	 */
	public InviteElement findFirstEle(Integer inviteId){
		Session session = getSession();
		String hql = "from InviteElement t where t.inviteId = :inviteId order by t.createDate ";
		Query query = session.createQuery(hql).setFirstResult(0).setMaxResults(1);
		query.setInteger("inviteId", inviteId);
		return	(InviteElement) query.uniqueResult();
	}
}
