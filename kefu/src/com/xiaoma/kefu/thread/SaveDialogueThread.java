package com.xiaoma.kefu.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.Dialogue;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisDao;
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
	
	private CustomerService customerService = (CustomerService) SpringContextUtil.getBean("customerService");
	private UserService userService = (UserService) SpringContextUtil.getBean("userService");
	private DialogueDetailService dialogueDetailService = (DialogueDetailService) SpringContextUtil.getBean("dialogueDetailService");
	private DialogueService dialogueService = (DialogueService) SpringContextUtil.getBean("dialogueService");
	private Jedis jedis = JedisDao.getJedis();
	
	private Logger logger = Logger.getLogger(SaveDialogueThread.class);
	
	public void run() {
		while (true) {
			
			try {
				Thread.sleep(5000L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			String toSaveDialogueListKey = JedisTalkDao.rpopSaveDialogue();
			
			//如果格式不正确，处理下一条
			Boolean isRight = JedisConstant.checkDialogueListKey(toSaveDialogueListKey);
			if(!isRight){
				continue;
			}
			
			logger.info("保存对话初始值：toSaveDialogueListKey: "+toSaveDialogueListKey);
			
			//获取对话列表
			List<String> dialogueList = jedis.lrange(toSaveDialogueListKey, 0, -1);
			logger.info("保存对话列表 dialogueList: "+dialogueList.toString());
			if(CollectionUtils.isEmpty(dialogueList)){
				continue;
			}
			
			
			//获取双方的真实id
			DialogueDetail one = (DialogueDetail) JsonUtil.getObjFromJson(dialogueList.get(0), DialogueDetail.class);
			
			Integer userId = one.getUserId();
			Long customerId = one.getCustomerId();
			logger.info("对话双方id: userId: "+userId+" ,customerId: "+customerId);
			
			User user = userService.getUserById(userId);
			Customer customer = customerService.getCustomerById(customerId);
			
			//对话数据整理与插入
			List<DialogueDetail> list = new ArrayList<DialogueDetail>(); 
			for(String message : dialogueList){
				DialogueDetail dialogueDetail = (DialogueDetail) JsonUtil.getObjFromJson(message, DialogueDetail.class);
				dialogueDetail.setCardName(user.getCardName());
				list.add(dialogueDetail);
			}
			
			Dialogue dialogue = genDialogueByList(list,user,customer);
			Long dialogueId = dialogueService.add(dialogue);
			
			for(DialogueDetail dialogueDetail : list){
				dialogueDetail.setDialogueId(dialogueId);
			}
			
			dialogueDetailService.batchAdd(list);
			
			//删除对话内容
			jedis.del(toSaveDialogueListKey);
			
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
		Integer last = list.size()-1;
		
		last = (last == -1) ? 0 : last;
		Date beginDate = list.get(0).getCreateDate();
		Date endDate = list.get(last).getCreateDate();
		
		Long duration = endDate.getTime() - beginDate.getTime();
		Integer durationTime = duration.intValue()/1000;
		
		Dialogue dialogue = new Dialogue();
		
		dialogue.setUserId(user.getId());
		dialogue.setCardName(user.getCardName());
		dialogue.setDeptId(user.getDeptId());
		dialogue.setCustomerId(customer.getId());
		
		dialogue.setBeginDate(beginDate);
		dialogue.setEndDate(endDate);
		dialogue.setDurationTime(durationTime);
		
		dialogue.setMaxSpace(null);
		dialogue.setIsWait(null);
		dialogue.setWaitTime(null);
		dialogue.setFirstTime(null);
		dialogue.setIsTalk(null);
		
		
		dialogue.setIp(customer.getIp());
		dialogue.setIpInfo(customer.getIpInfo());
		
		return dialogue;
	}
	
	public static void main(String[] args) {
		Thread saveDialogueThread = new Thread(new SaveDialogueThread(), "SaveDialogueThread");
		saveDialogueThread.setDaemon(true);
		saveDialogueThread.start();
	}

}
