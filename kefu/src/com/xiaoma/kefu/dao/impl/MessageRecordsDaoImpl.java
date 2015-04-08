package com.xiaoma.kefu.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.MessageRecordsDao;
import com.xiaoma.kefu.model.MessageRecords;

/**
 * 留言信息	daoImpl
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月8日上午10:56:34
**********************************
 */
@Repository("messageRecordsDaoImpl")
public class MessageRecordsDaoImpl extends BaseDaoImpl<MessageRecords> implements MessageRecordsDao {
	
	/**
	 * 更新为删除状态, 用于逻辑删除
	* @Description: TODO
	* @param record
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	@Override
	public int update2Del(MessageRecords record) {
		Session session = getSession();
	    Query query = session.createQuery("update MessageRecords t set t.isDel = 1 where t.id = :id "); 
	    query.setInteger("id", record.getId());
	    return query.executeUpdate();  
	}
	
	/**
	 * 更新为正常状态,用于回收站还原
	* @Description: TODO
	* @param record
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	@Override
	public int update2Restore(MessageRecords record) {
		Session session = getSession();
	    Query query = session.createQuery("update MessageRecords t set t.isDel = 0 where t.id = :id "); 
	    query.setInteger("id", record.getId());
	    return query.executeUpdate();  
	}

}
