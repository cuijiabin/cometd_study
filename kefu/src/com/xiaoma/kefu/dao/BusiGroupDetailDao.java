package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.BusiGroupDetail;

/**
 * 业务分组明细	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月13日下午5:41:16
**********************************
 */
public interface BusiGroupDetailDao extends BaseDao<BusiGroupDetail>{
	
	/**
	 * 获取分组下的明细list
	* @Description: TODO
	* @param groupId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public List<BusiGroupDetail> findByGroupId(Integer groupId);
	
	
}
