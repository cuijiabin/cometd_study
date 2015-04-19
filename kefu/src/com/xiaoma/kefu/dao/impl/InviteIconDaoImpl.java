package com.xiaoma.kefu.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.InviteIconDao;
import com.xiaoma.kefu.model.InviteIcon;
import com.xiaoma.kefu.util.SysConst.DeviceType;

/**
 * 对话邀请框	daoImpl
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:34
**********************************
 */
@Repository("inviteIconDaoImpl")
public class InviteIconDaoImpl extends BaseDaoImpl<InviteIcon> implements InviteIconDao {
	
	/**
	 * 根据风格id,设备类型 查找
	* @Description: TODO
	* @param styleId
	* @param deviceType
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public InviteIcon findByStyleId(Integer styleId,DeviceType deviceType){
		Session session = getSession();
		String hql = "from InviteIcon t where t.styleId = :styleId and t.deviceType = :deviceType ";
		Query query = session.createQuery(hql);
		query.setInteger("styleId", styleId);
		query.setInteger("deviceType", deviceType.getCode());
		return (InviteIcon) query.uniqueResult();
	}
}
