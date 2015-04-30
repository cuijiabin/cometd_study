package com.xiaoma.kefu.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.MessageDao;
import com.xiaoma.kefu.model.Message;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author frongji
 * @time 2015年4月7日下午5:24:07
 *     常用语  service class
 */
@Service("messageService")
public class MessageService {
      
	   @Autowired
	   private MessageDao messageDaoImpl;
	   
		/**
		 * 分页查询
		 * 
		 * @param map
		 * @param pageBean
		 * @return
		 */
		public void getResult(Map<String, String> conditions,PageBean<Message> pageBean,Integer id) {
			messageDaoImpl.findByCondition(conditions, pageBean, id);
		}
		
		 /**
	     * 查询最大的id 值
	     */
	   public Integer maxId(){
		   Integer num =  messageDaoImpl.getMaxId();  // num 最大的排序号
		      return num;
	   }
	   
	   /**
	    * 查询一条
	    */
	   public Message getMessageById(Integer id){
		   return messageDaoImpl.getMessageById(id);
	   }
	    /**
	     * 查询是否有常用
	     */
	   public Integer checkDaily(Integer messageTypeId){
		      Integer count =  messageDaoImpl.checkDaily(messageTypeId);
		      return count;
	   }
		/**
		 * 添加
		 */
	   public boolean createNewMessage(Message message){
	      return messageDaoImpl.createNewMessage(message);
	   }
	  

	   /**
	    * 修改
	    */
	   public boolean updateMessage(Message message){
		  return messageDaoImpl.updateMessage(message); 
	   }
	   
	    /**
		 * 删除
		 */
	   public Integer deleteMessageById(Integer id){
		return messageDaoImpl.deleteMessageById(id);
	   }
}
