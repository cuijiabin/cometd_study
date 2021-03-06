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
	public List findTree(int typeId,int userId) {
		return messageTypeDaoImpl.findTree(typeId,userId);
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
     * 查询是否有子节点
     */
   public Integer checkChild(Integer id){
	      Integer count =  messageTypeDaoImpl.checkChild(id);
	      return count;
   }
	/**
	 * 显示详细信息
	 */
	public MessageType getMessageTypeById(Integer id) {
		return messageTypeDaoImpl.getMessageTypeById(id);
	}
	/**
	 * 条件查询
	 */
	public MessageType getResultBySearch(Integer typeId, String title,Integer userId){
		return  messageTypeDaoImpl.getResultBySearch(typeId,title,userId);
		
	}
	 /**
     * 查询是否有子节点
     */
   public Integer getChildCount(Integer id,Integer typeId,Integer userId){
	   Integer num =  messageTypeDaoImpl.checkChildMax(id,typeId,userId);  // num 最大的排序号
	      return num;
   }
	
	
	

}
