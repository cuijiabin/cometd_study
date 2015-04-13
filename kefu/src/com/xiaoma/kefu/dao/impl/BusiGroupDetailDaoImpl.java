package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.BusiGroupDetailDao;
import com.xiaoma.kefu.model.BusiGroupDetail;

/**
 * *********************************
* @Description: 业务分组明细	daoImpl
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:29:12
**********************************
 */
@Repository("busiGroupDetailDaoImpl")
public class BusiGroupDetailDaoImpl extends BaseDaoImpl<BusiGroupDetail> implements BusiGroupDetailDao {
	
	/**
	 * 获取分组下的明细list
	* @Description: TODO
	* @param groupId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BusiGroupDetail> findByGroupId(Integer groupId) {
		Session session = getSession();
		String hql = "from BusiGroupDetail t where t.groupId = :groupId ";
		Query query = session.createQuery(hql);
		query.setInteger("groupId",groupId);
		return	query.list();
	}
	

}
