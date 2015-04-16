package com.xiaoma.kefu.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.Dialogue;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueDetailService;
import com.xiaoma.kefu.service.DialogueService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.JsonUtil;

/**
 * 保存对话记录
 * @author cuijiabin
 * @date 2015-04-15
 */
public class SaveDialogueThread implements Runnable{
	
	private String userCcnId;
	private String customerCcnId;
	
	/**
	 * 使用构造方法时只需要添加一个参数
	 * @param userCcnId
	 * @param customerCcnId
	 */
	public SaveDialogueThread(String userCcnId, String customerCcnId){
		this.userCcnId = userCcnId;
		this.customerCcnId = customerCcnId;
	}
	
	private CustomerService customerService = (CustomerService) SpringContextUtil.getBean("customerService");
	private UserService userService = (UserService) SpringContextUtil.getBean("userService");
	private DialogueDetailService dialogueDetailService = (DialogueDetailService) SpringContextUtil.getBean("dialogueDetailService");
	private DialogueService dialogueService = (DialogueService) SpringContextUtil.getBean("dialogueService");
	private Logger logger = Logger.getLogger(SaveDialogueThread.class);
	
	public void run() {
		
		logger.info("保存对话初始值：userCcnId: "+userCcnId+" ,customerCcnId: "+customerCcnId);
		
		/**
		 * 退出标识 false-客服 true-客户
		 */
		boolean tag = false;
		//获取对话点
		if(StringUtils.isBlank(userCcnId)){
			userCcnId = JedisTalkDao.getTalkerCcnId(JedisConstant.CUSTOMER_TYPE,customerCcnId);
		}
		if(StringUtils.isBlank(customerCcnId)){
			customerCcnId = JedisTalkDao.getTalkerCcnId(JedisConstant.USER_TYPE,userCcnId);
			tag = true;
		}
		logger.info("保存对话处理值： userCcnId: "+userCcnId+" ,customerCcnId: "+customerCcnId);
		
		//获取对话列表
		List<String> dialogueList = JedisTalkDao.getDialogueList(userCcnId, customerCcnId);
		logger.info("保存对话列表 dialogueList: "+dialogueList.toString());
		if(CollectionUtils.isEmpty(dialogueList)){
			return ;
		}
		
		//获取双方的真实id
		String userId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, userCcnId);
		String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, customerCcnId);
		
		logger.info("保存对话 userCcnId: "+userCcnId+" ,customerCcnId"+customerCcnId+" ,userId: "+userId+" ,customerId: "+customerId);
		Integer uId = Integer.valueOf(userId);
		Long cId = Long.valueOf(customerId);
		
		User user = userService.getUserById(uId);
		Customer customer = customerService.getCustomerById(cId);
		
		List<DialogueDetail> list = new ArrayList<DialogueDetail>(); 
		for(String message : dialogueList){
			DialogueDetail dialogueDetail = (DialogueDetail) JsonUtil.getObjFromJson(message, DialogueDetail.class);
			dialogueDetail.setUserId(uId);
			dialogueDetail.setCustomerId(cId);
			dialogueDetail.setCardName(user.getCardName());
			
			list.add(dialogueDetail);
		}
		
		Dialogue dialogue = genDialogueByList(list,user,customer);
		
		Long dialogueId = dialogueService.add(dialogue);
		
		for(DialogueDetail dialogueDetail : list){
			dialogueDetail.setDialogueId(dialogueId);
		}
		
		dialogueDetailService.batchAdd(list);
		
		//删除对话点
		JedisTalkDao.delTalkerCcnId(JedisConstant.CUSTOMER_TYPE, customerCcnId);
		JedisTalkDao.delTalkerCcnId(JedisConstant.USER_TYPE, userCcnId);
		
		//删除对话内容
		JedisTalkDao.delDialogueList(userCcnId, customerCcnId);
		
		//删除当前用户
		if(tag){
			JedisTalkDao.delCnnUserId(JedisConstant.CUSTOMER_TYPE, customerCcnId);
		}else{
			JedisTalkDao.delCnnUserId(JedisConstant.USER_TYPE, userCcnId);
		}
		
	}
	
	/**
	 * 根据对话详情列表生成对话信息
	 * @param list
	 * @param user
	 * @param customer
	 * @return
	 */
	private Dialogue genDialogueByList(List<DialogueDetail> list,User user,Customer customer){
		Date beginDate = list.get(0).getCreateDate();
		
		Dialogue dialogue = new Dialogue();
		
		dialogue.setBeginDate(beginDate);
		dialogue.setCustomerId(customer.getId());
		dialogue.setUserId(user.getId());
		dialogue.setIp(customer.getIp());
		
		return dialogue;
	}
	
	public static void main(String[] args) {
		SaveDialogueThread sdt = new SaveDialogueThread("6b351d13-dd3c-471d-8ae5-ea315c5331d8","8f2ff2de-3281-4b50-b638-8803a0c3ce8d");
		sdt.run();
	}

}
