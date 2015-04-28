package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
	
	/**
	 * 根据分组id,删除
	* @param id
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月20日
	 */
	public int deleteByGroupId(Integer groupId){
		Session session = getSession();
		String hql = "delete BusiGroupDetail t where t.groupId = :groupId ";
		Query query = session.createQuery(hql);
		query.setInteger("groupId", groupId);
		return query.executeUpdate();
	}
	
	/**
	 * 根据分组id,用户id,用户类型, 查找当前对象
	* @param busiGroupDetail
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	public BusiGroupDetail getByModel(BusiGroupDetail busiGroupDetail){
		Session session = getSession();
		String hql = "from BusiGroupDetail t where t.groupId = :groupId and t.userId = :userId and t.userType = :userType  ";
		Query query = session.createQuery(hql);
		query.setInteger("groupId", busiGroupDetail.getGroupId());
		query.setInteger("userId", busiGroupDetail.getUserId());
		query.setInteger("userType", busiGroupDetail.getUserType());
		return (BusiGroupDetail) query.uniqueResult();
	}
	
	/**
	 * 更新是否 接待客户
	* @param detail
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	public int updateIsRece(BusiGroupDetail detail){
		Session session = getSession();
	    Query query = session.createQuery("update BusiGroupDetail t set t.isReception = :isReception where id = :id "); 
	    query.setInteger("isReception", detail.getIsReception());
	    query.setInteger("id", detail.getId());
	    return query.executeUpdate();  
	}
	
	/**
	 * 根据用户和类型,删除分组明细信息
	* @param userId	用户或部门id
	* @param userType	1=用户 2=部门
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月22日
	 */
	public int deleteByUserOrDept(Integer userId,Integer userType){
		Session session = getSession();
		String hql = "delete BusiGroupDetail t where t.userId = :userId and t.userType = :userType ";
		Query query = session.createQuery(hql);
		query.setInteger("userId", userId);
		query.setInteger("userType", userType);
		return query.executeUpdate();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findUserIdsByStyleId(Integer styleId) {
		if(null == styleId){
			return null;
		}
		Session session = getSession();
		String sql = "select userId From busi_group_detail b WHERE b.styleId = "+styleId;
		
		SQLQuery query = session.createSQLQuery(sql);
		
		
		
		return ((List<Integer>)query.list());
	}
	

}
