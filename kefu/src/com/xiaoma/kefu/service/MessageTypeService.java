package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.MessageTypeDao;
import com.xiaoma.kefu.model.MessageType;

/**
 * @author frongji
 * @time 2015年4月8日下午3:36:28
 * 
 */
@Service
public class MessageTypeService {

	@Autowired
	private MessageTypeDao messageTypeDaoImpl;
   /**
    * 查询树
    * @param tid
    * @return
    */
	public List findTree(int tid) {
		return messageTypeDaoImpl.findTree(tid);
	}
	
	/**
	 * 添加
	 */
   public boolean createNewMessageType(MessageType messageType){
      return messageTypeDaoImpl.createNewMessageType(messageType);
   }
  

   /**
    * 修改
    */
   public boolean updateMessageType(MessageType messageType){
	  return messageTypeDaoImpl.updateMessageType(messageType); 
   }
   
    /**
	 * 删除
	 */
   public boolean deleteMessageTypeById(Integer id){
	
	return messageTypeDaoImpl.deleteMessageTypeById(id);
   }

	/**
	 * 在弹出的对话框显示详细信息
	 */
	public MessageType getMessageTypeById(Integer id) {
		return messageTypeDaoImpl.getMessageTypeById(id);
	}

}
