package com.xiaoma.kefu.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.xiaoma.kefu.comet4j.DialogueInfo;
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
import com.xiaoma.kefu.util.TimeHelper;

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
			
			String uccnId = JedisConstant.getUccnIdFromDialogueListKey(toSaveDialogueListKey);
			if(uccnId == null){
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
			
			//按时间升序排序
			Collections.sort(list, new Comparator<DialogueDetail>(){
				            public int compare(DialogueDetail d1, DialogueDetail d2) {
				                return d1.getCreateDate().compareTo(d2.getCreateDate());
				            }
				        });
			
			Dialogue dialogue = genDialogueByList(list,uccnId,customer);
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
	private Dialogue genDialogueByList(List<DialogueDetail> list,String uccnId,Customer customer){
		
		
		Integer last = list.size()-1;
		last = (last == -1) ? 0 : last;
		
		Date beginDate = list.get(0).getCreateDate();
		Date endDate = list.get(last).getCreateDate();
		Integer durationTime = TimeHelper.diffSecond(endDate, beginDate);
		
		Map<String,Integer> resultMap = getMaxSpace(list);
		Integer maxSpace = resultMap.get("maxSpace");
		Integer isTalk = resultMap.get("isTalk");
		Integer firstTime = resultMap.get("firstTime");
		
		Long customerId = customer.getId();
		if(!JedisTalkDao.isUser(uccnId)){
	    	 logger.error("传入的不是用户连接点 uccnId："+uccnId);
	    }
		DialogueInfo dInfo = JedisTalkDao.getDialogueInfo(customerId.toString(),uccnId);
		Dialogue dialogue = new Dialogue();
		
		dialogue.setUserId(dInfo.getUserId()); //客服ID
		dialogue.setCardName(dInfo.getCardName()); //工号名片
		dialogue.setDeptId(dInfo.getDeptId());// 部门ID
		dialogue.setCustomerId(dInfo.getCustomerId());// 客户ID
		
		dialogue.setBeginDate(beginDate);// 对话开始时间
		dialogue.setEndDate(endDate);// 对话结束时间
		dialogue.setDurationTime(durationTime);// 对话时长（秒）
		
		dialogue.setMaxSpace(maxSpace);// 最大回复时间间隔（秒）--统计
		dialogue.setIsWait(dInfo.getIsWait());// 是否进入等待队列（0否 1 是 --收集
		dialogue.setWaitTime(dInfo.getWaitTime());// 等待时长（秒）--收集
		dialogue.setFirstTime(firstTime);// 机器人与客服时间间隔（秒）--统计
		dialogue.setIsTalk(isTalk);// 是否有客户的说话记录（0，无 1，有）--统计
		
		if(dInfo.getScoreType() == null || dInfo.getScoreType() == 0){
			JedisTalkDao.setDialogueLasts(customerId.toString(), uccnId);
		}else{
			dialogue.setScoreType(dInfo.getScoreType());// 评分类型 --收集
			dialogue.setScoreRemark(dInfo.getScoreRemark());// 评分备注 --收集
		}
		
		dialogue.setIp(dInfo.getIp());// ip地址
		dialogue.setIpInfo(dInfo.getIpInfo());// ip分析（省市县运营商）
		
		dialogue.setKeywords(dInfo.getKeywords());// 网站关键词 ##隔开 --收集
		dialogue.setDeviceType(dInfo.getDeviceType());// 设备类型（1 pc 2 移动） --收集
		dialogue.setConsultPage(dInfo.getConsultPage());// 咨询页面 --收集
		dialogue.setOpenType(dInfo.getOpenType());// 打开类型(图标,邀请框等) --收集
		dialogue.setBtnCode(dInfo.getBtnCode());// 按钮id --收集
		dialogue.setWaitListId(null);// 考试项目或需求编码id
		dialogue.setCloseType(dInfo.getCloseType());// 关闭类型（1 客户关闭 2 系统关闭 3 客服关闭）
		dialogue.setIsDel(0);// 回收站（0 正常 1 回收）
		dialogue.setTotalNum(list.size());// 本次对话总条数  --统计
		dialogue.setLandingPage(dInfo.getLandingPage());// 着陆页 --收集
		dialogue.setStyleId(dInfo.getStyleId());// 风格id --收集
		
		//清理缓存对话信息
		JedisTalkDao.delDialogueInfo(customerId.toString(), uccnId);
		return dialogue;
	}
	
	public static void main(String[] args) {
		Thread saveDialogueThread = new Thread(new SaveDialogueThread(), "SaveDialogueThread");
		saveDialogueThread.setDaemon(true);
		saveDialogueThread.start();
	}
	
	/**
	 * 获取最大回复间隔
	 * @param list
	 * @return
	 */
	private Map<String,Integer> getMaxSpace(List<DialogueDetail> list){
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		Date askDate = null;
		Date replyDate = null;
		Boolean askType = false;
		Integer maxSpace = null;
		for(DialogueDetail dialogueDetail : list){
			Integer dialogueType = dialogueDetail.getDialogueType();
			if(1 == dialogueType){
				askDate = dialogueDetail.getCreateDate();
				askType = true;
			}
			if(2 == dialogueType && askType){
				replyDate = dialogueDetail.getCreateDate();
				Integer duration = TimeHelper.diffSecond(replyDate, askDate);
				if(maxSpace == null || duration > maxSpace){
					maxSpace = duration;
				}
				askType = false;
			}
		}
		map.put("maxSpace", maxSpace);//最大等待时间
		
		Integer firstTime = null;
		Integer isTalk = 0;
		askType = false;
		for(DialogueDetail dialogueDetail : list){
			Integer dialogueType = dialogueDetail.getDialogueType();
			if(3 == dialogueType){
				askDate = dialogueDetail.getCreateDate();
				askType = true;
			}
			if(2 == dialogueType && askType){
				replyDate = dialogueDetail.getCreateDate();
				firstTime = TimeHelper.diffSecond(replyDate, askDate);
				isTalk = 1;
				break;
			}
		}
		map.put("firstTime", firstTime);
		map.put("isTalk", isTalk);
		
		return map;
		
	}

}
