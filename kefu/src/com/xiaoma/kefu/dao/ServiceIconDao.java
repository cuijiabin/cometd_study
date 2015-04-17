package com.xiaoma.kefu.dao;

import com.xiaoma.kefu.model.ServiceIcon;
import com.xiaoma.kefu.util.SysConst.DeviceType;

/**
 * 客服图标	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:24
**********************************
 */
public interface ServiceIconDao extends BaseDao<ServiceIcon>{
	
	/**
	 * 根据风格id,设备类型 查找
	* @Description: TODO
	* @param styleId
	 * @param deviceType 
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ServiceIcon findByStyleId(Integer styleId, DeviceType deviceType);
	
	
}
