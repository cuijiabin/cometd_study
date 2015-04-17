package com.xiaoma.kefu.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.ServiceIconDao;
import com.xiaoma.kefu.model.ServiceIcon;
import com.xiaoma.kefu.util.SysConst.DeviceType;

/**
 * 客服图标	daoImpl
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:34
**********************************
 */
@Repository("serviceIconDaoImpl")
public class ServiceIconDaoImpl extends BaseDaoImpl<ServiceIcon> implements ServiceIconDao {
	
	/**
	 * 根据风格id,设备类型 查找
	* @Description: TODO
	* @param styleId
	* @param deviceType
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ServiceIcon findByStyleId(Integer styleId,DeviceType deviceType){
		Session session = getSession();
		String hql = "from ServiceIcon t where t.styleId = :styleId and t.deviceType = :deviceType ";
		Query query = session.createQuery(hql);
		query.setInteger("styleId", styleId);
		query.setInteger("deviceType", deviceType.getCode());
		return (ServiceIcon) query.uniqueResult();
	}
}
