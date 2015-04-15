package com.xiaoma.kefu.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.ClientStyleDao;
import com.xiaoma.kefu.model.ClientStyle;

/**
 * 访客端界面	daoImpl
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:34
**********************************
 */
@Repository("clientStyleDaoImpl")
public class ClientStyleDaoImpl extends BaseDaoImpl<ClientStyle> implements ClientStyleDao {
	
	/**
	 * 根据风格id查找
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ClientStyle findByStyleId(Integer styleId){
		Session session = getSession();
		String hql = "from ClientStyle t where t.styleId = :styleId ";
		Query query = session.createQuery(hql);
		query.setInteger("styleId", styleId);
		return (ClientStyle) query.uniqueResult();
	}
}
