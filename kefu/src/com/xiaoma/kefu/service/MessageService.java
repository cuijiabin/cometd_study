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
	 * 查询所有、 条件查询
	 */
	public void getResult(Map<String, String> conditions,PageBean<Message> pageBean){
		messageDaoImplDao.findByCondition(conditions,pageBean);

	 }
}
