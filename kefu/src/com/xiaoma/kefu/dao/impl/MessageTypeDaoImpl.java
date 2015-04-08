package com.xiaoma.kefu.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.MessageTypeDao;
import com.xiaoma.kefu.model.MessageType;

/**
 * @author frongji
 * @time 2015年4月8日下午3:40:09
 *
 */
@Repository("messageTypeDaoImpl")
public class MessageTypeDaoImpl extends BaseDaoImpl<MessageType> implements MessageTypeDao {
  
	
    /**
     * 查询一条
     */
	@Override
	public MessageType getMessageTypeById(Integer id) {
		if(id==null)
		{
			return null;
		}
		return findById(MessageType.class,id);
	}
	
	/**
	 * 添加一条
	 */
	@Override
	public boolean createNewMessageType(MessageType messageType){
		
		try {
			 add(messageType);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * 修改
	 * @param 
	 * @return
	 */
	@Override
	public boolean updateMessageType(MessageType messageType) {
		try {
			update(messageType);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	



	
	/**
	 * 删除
	 */
	@Override
	public boolean deleteMessageTypeById(Integer id){
		MessageType messageType = this.getMessageTypeById(id);
		try {	
		delete(messageType);
		return true;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}


}
