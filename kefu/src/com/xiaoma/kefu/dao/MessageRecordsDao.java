package com.xiaoma.kefu.dao;

import com.xiaoma.kefu.model.MessageRecords;




/**
 * 留言信息	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月8日上午10:54:53
**********************************
 */
public interface MessageRecordsDao extends BaseDao<MessageRecords> {
	
	/**
	 * 更新为删除状态, 用于逻辑删除
	* @Description: TODO
	* @param record
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	public int update2Del(MessageRecords record) ;
	
	/**
	 * 更新为正常状态,用于回收站还原
	* @Description: TODO
	* @param record
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	public int update2Restore(MessageRecords record);
	
}
