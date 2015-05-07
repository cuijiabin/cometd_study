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

	
	/**
	 * 根据风格id,获取 分组明细
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年5月7日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BusiGroupDetail> findByStyleId(Integer styleId){
		Session session = getSession();
		String hql = "from BusiGroupDetail t where t.styleId = :styleId ";
		Query query = session.createQuery(hql);
		query.setInteger("styleId",styleId);
		return	query.list();
	}
	

	/**
	 * 根据用户id,用户部门id,获取有效风格id列表
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getStyleIdsByuser(Integer userId,Integer deptId){
		if(null == userId || deptId == null){
			return null;
		}
		Session session = getSession();
		String sql = " select styleId from busi_group_detail t where t.userId = " + userId + " and userType = 1 and isReception = 1 "
				+ " union all  "
				+ " select styleId from busi_group_detail t where t.userId = " + deptId + " and userType = 2 and isReception =1  " ;
		SQLQuery query = session.createSQLQuery(sql);
		
		return ((List<Integer>)query.list());
	}
	

}
