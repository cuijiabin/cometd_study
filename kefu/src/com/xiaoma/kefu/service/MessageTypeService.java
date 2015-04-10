package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.MessageTypeDao;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.MessageType;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author frongji
 * @time 2015年4月8日下午3:36:28
 *
 */
@Service
public class MessageTypeService {
	
	@Autowired
	private MessageTypeDao messageTypeDaoImpl;
	  
      public List findTree(int tid) {
		
		return messageTypeDaoImpl.findTree(tid);
	  }
	
	  /**
	    * 在弹出的对话框显示详细信息
	    */
	   public MessageType getMessageTypeById(Integer id){
		   return messageTypeDaoImpl.getMessageTypeById(id);
	   }


}
