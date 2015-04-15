package com.xiaoma.kefu.thread;

import org.apache.commons.lang.StringUtils;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueDetailService;
import com.xiaoma.kefu.service.DialogueService;
import com.xiaoma.kefu.service.UserService;

/**
 * 保存对话记录
 * @author cuijiabin
 * @date 2015-04-15
 */
public class SaveDialogueThread implements Runnable{
	
	private String userCcnId;
	private String customerCcnId;
	
	private CustomerService customerService = (CustomerService) SpringContextUtil.getBean("customerService");
	private UserService userService = (UserService) SpringContextUtil.getBean("userService");
	private DialogueDetailService dialogueDetailService = (DialogueDetailService) SpringContextUtil.getBean("dialogueDetailService");
	private DialogueService dialogueService = (DialogueService) SpringContextUtil.getBean("dialogueService");
	
	@Override
	public void run() {
		//删除对话点
		if(StringUtils.isBlank(userCcnId)){
			userCcnId = JedisTalkDao.getTalkerCcnId(JedisConstant.CUSTOMER_TYPE,customerCcnId);
		}
		if(StringUtils.isBlank(userCcnId)){
			customerCcnId = JedisTalkDao.getTalkerCcnId(JedisConstant.CUSTOMER_TYPE,userCcnId);
		}
		//获取对话列表
		
		
		
		JedisTalkDao.delTalkerCcnId(JedisConstant.CUSTOMER_TYPE, customerCcnId);
		JedisTalkDao.delTalkerCcnId(JedisConstant.USER_TYPE, userCcnId);
		
	}

}
