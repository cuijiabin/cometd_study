package com.xiaoma.kefu.dao;

import com.xiaoma.kefu.model.InviteIcon;
import com.xiaoma.kefu.util.SysConst.DeviceType;

/**
 * 对话邀请框	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:24
**********************************
 */
public interface InviteIconDao extends BaseDao<InviteIcon>{
	
	/**
	 * 根据风格id,设备类型 查找
	* @Description: TODO
	* @param styleId
	 * @param deviceType 
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public InviteIcon findByStyleId(Integer styleId, DeviceType deviceType);
	
	
}
