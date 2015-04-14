package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.BusiGroup;

/**
 * 业务分组	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月13日下午5:41:16
**********************************
 */
public interface BusiGroupDao extends BaseDao<BusiGroup>{
	
	/**
	 * 获取风格下的 业务分组list
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public List<BusiGroup> findByStyleId(Integer styleId);
	
	/**
	 * 校验名称是否存在
	* @Description: TODO
	* @param group
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer validateName(BusiGroup group);
	
}
