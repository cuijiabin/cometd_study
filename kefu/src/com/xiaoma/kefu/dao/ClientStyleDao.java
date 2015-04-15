package com.xiaoma.kefu.dao;

import com.xiaoma.kefu.model.ClientStyle;

/**
 * 访客端界面	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:24
**********************************
 */
public interface ClientStyleDao extends BaseDao<ClientStyle>{
	
	/**
	 * 根据风格id查找
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ClientStyle findByStyleId(Integer styleId);
	
	
}
