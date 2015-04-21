package com.xiaoma.kefu.dao;

import com.xiaoma.kefu.model.AllotRule;

/**
 * 分配机制	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:24
**********************************
 */
public interface AllotRuleDao extends BaseDao<AllotRule>{
	
	/**
	 * 根据风格id查找
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public AllotRule findByStyleId(Integer styleId);
	
	
}
