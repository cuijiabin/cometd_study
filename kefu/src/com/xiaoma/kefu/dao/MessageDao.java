package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Message;

/**
 * @author frongji
 * @time 2015年4月7日下午5:18:15
 *
 */
public interface MessageDao extends BaseDao<Message> {
    
	/**
	 * 获得常用语的总条数
	 * @return
	 */
	public Integer getAllMessageCount();
   
  /**
   * 	条件查询
   * @param start
   * @param offset
   * @param 
   * @param 
   * @return
   */
	public List<Message> getMessageByConditions(Integer start, Integer offset,
			String customerName, String phone);

}
