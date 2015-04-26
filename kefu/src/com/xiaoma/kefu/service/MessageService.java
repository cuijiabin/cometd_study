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
	   private MessageDao messageDaoImplDao;
	   
		/**
		 * 分页查询
		 * 
		 * @param map
		 * @param pageBean
		 * @return
		 */
		public void getResult(Map<String, String> conditions,PageBean<Message> pageBean,Integer id) {
			messageDaoImplDao.findByCondition(conditions, pageBean, id);
		}
		
		 /**
	     * 查询最大的id 值
	     */
	   public Integer maxId(){
		   Integer num =  messageDaoImplDao.getMaxId();  // num 最大的排序号
		      return num;
	   }
	   
		/**
		 * 添加
		 */
	   public boolean createNewMessage(Message message){
	      return messageDaoImplDao.createNewMessage(message);
	   }
	  

	   /**
	    * 修改
	    */
	   public boolean updateMessage(Message message){
		  return messageDaoImplDao.updateMessage(message); 
	   }
	   
	    /**
		 * 删除
		 */
	   public boolean deleteMessageById(Integer id){
		
		return messageDaoImplDao.deleteMessageById(id);
	   }
}
