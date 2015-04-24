package com.xiaoma.kefu.comet4j;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Blacklist;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.DialogueSwitch;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.BlacklistService;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueSwitchService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.JsonUtil;
import com.xiaoma.kefu.util.TimeHelper;

@Controller
@RequestMapping(value = "chat")
public class ChatCometController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DialogueSwitchService dialogueSwitchService;
	
	@Autowired
	private BlacklistService blacklistService;
	
	private Logger logger = Logger.getLogger(ChatCometController.class);

	private static final CometContext context = CometContext.getInstance();

	/**
	 * 客服向客户发送消息
	 * 
	 * @param request
	 * @param response
	 * @param userCId
	 * @param cusCId
	 * @param message
	 * @throws IOException
	 */
	@RequestMapping(value = "toCustomer.action", method = RequestMethod.POST)
	public void talkToCustomer(HttpServletRequest request,HttpServletResponse response, String userCId, String cusCId,String message) throws IOException {

		logger.info("send message to customer info: userCId: "+userCId+" ,cusCId: "+cusCId+" ,message: "+message);
		//参数检查
		if(StringUtils.isBlank(userCId) || StringUtils.isBlank(cusCId) || StringUtils.isBlank(message)){
			
			return ;
		}
		
		Long sendTime = System.currentTimeMillis();
		CometEngine engine = context.getEngine();
		
		//获取用户id
		String userId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, userCId);
		String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, cusCId);
		Integer uId = Integer.valueOf(userId);
		Long cId = Long.valueOf(customerId);
		
		//把消息放入redis
		DialogueDetail dialogueDetail = new DialogueDetail();
		dialogueDetail.setContent(message);
		dialogueDetail.setDialogueType(2);
		dialogueDetail.setCreateDate(new Date(sendTime));
		dialogueDetail.setUserId(uId);
		dialogueDetail.setCustomerId(cId);
		
		User user = userService.getUserById(uId);
		
		String strMessage = JsonUtil.toJson(dialogueDetail);
		JedisTalkDao.addDialogueList(userCId, cusCId, strMessage);
		
		String messageTime = TimeHelper.convertMillisecondToStr(sendTime, TimeHelper.Time_PATTERN);
		
		//包装消息并发送
		Message umessage = new Message(userCId,"我",message,messageTime,"1");
		NoticeData und = new NoticeData(Constant.ON_MESSAGE, umessage);
		
		Message cmessage = new Message(cusCId, user.getCardName(), message, messageTime,"2");
		NoticeData cnd = new NoticeData(Constant.ON_MESSAGE, cmessage);

		CometConnection userCcn = engine.getConnection(userCId);
		CometConnection cusCcn = engine.getConnection(cusCId);

		engine.sendTo(Constant.CHANNEL, userCcn, und);
		engine.sendTo(Constant.CHANNEL, cusCcn, cnd);

		return;

	}
	
	/**
	 * 客户提问发送
	 * 
	 * @param request
	 * @param response
	 * @param cusCId
	 * @param message
	 * @throws IOException
	 */
	@RequestMapping(value = "toUser.action", method = RequestMethod.POST)
	public void talkToUser(HttpServletRequest request,HttpServletResponse response, String cusCId, String message) throws IOException {
        
		//参数检查
		if(StringUtils.isBlank(cusCId) || StringUtils.isBlank(message)){
			return ;
		}
		Long sendTime = System.currentTimeMillis();
		CometEngine engine = context.getEngine();
		
		String userCId = JedisTalkDao.getCcnPassiveId(cusCId);
		logger.info("send message to user info: userCId: "+userCId+" ,cusCId: "+cusCId+" ,message: "+message);
		
		//如果userCId是空的话，告诉当前用户对话已结束
		//TODO
		
		//获取用户id
		String userId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, userCId);
		String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, cusCId);
		Integer uId = Integer.valueOf(userId);
		Long cId = Long.valueOf(customerId);
		
		//把消息放入redis
		DialogueDetail dialogueDetail = new DialogueDetail();
		dialogueDetail.setContent(message);
		dialogueDetail.setDialogueType(1);
		dialogueDetail.setCreateDate(new Date(sendTime));
		dialogueDetail.setUserId(uId);
		dialogueDetail.setCustomerId(cId);
		
		String strMessage = JsonUtil.toJson(dialogueDetail);
		JedisTalkDao.addDialogueList(userCId, cusCId, strMessage);
		
		String messageTime = TimeHelper.convertMillisecondToStr(sendTime, TimeHelper.Time_PATTERN);
		
		Customer customer  = customerService.getCustomerById(cId);
		String customerName = (customer == null || StringUtils.isEmpty(customer.getCustomerName())) ? ("客户："+cId) : customer.getCustomerName();
		
		//包装消息并发送
		Message umessage = new Message(cusCId, customerName, message, messageTime,"2");
		NoticeData und = new NoticeData(Constant.ON_MESSAGE, umessage);
		
		Message cmessage = new Message(userCId,"我",message,messageTime,"1");
		NoticeData cnd = new NoticeData(Constant.ON_MESSAGE, cmessage);

		CometConnection userCcn = engine.getConnection(userCId);
		CometConnection cusCcn = engine.getConnection(cusCId);

		engine.sendTo(Constant.CHANNEL, userCcn, und);
		engine.sendTo(Constant.CHANNEL, cusCcn, cnd);

		return;

	}

	
	/**
	 * 客服转接操作
	 * @param request
	 * @param response
	 * @param ccnId
	 * @param toUserId
	 * @param customerId
	 */
	@RequestMapping(value = "switchDialogue.action", method = RequestMethod.POST)
	public void switchDialogue(HttpServletRequest request,HttpServletResponse response, String ccnId,String toUserId,Long customerId,String remark,String customerCcnId){
		
		logger.info("switchDialogue param ccnId: "+ccnId+" ,toUserId: "+toUserId+" ,customerId: "+customerId);
		String uId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, ccnId);
		Integer userId = Integer.valueOf(uId);
		
		//参数检查
		if(userId == null || toUserId == null || customerId == null){
			return ;
		}
		
		//保存转接记录到数据库
		DialogueSwitch dialogueSwitch = new DialogueSwitch();
		dialogueSwitch.setFromUserId(userId);
		dialogueSwitch.setToUserId(Integer.valueOf(toUserId));
		dialogueSwitch.setCustomerId(customerId);
		
		dialogueSwitch.setRemark(remark);
		dialogueSwitch.setCreateDate(new Date());
		
		dialogueSwitchService.addDialogueSwitch(dialogueSwitch);
		
		String toCcnId = JedisTalkDao.getUserCcnList(toUserId).get(0);
		
		//保存会话
        String key = JedisConstant.getDialogueListKey(ccnId,customerCcnId);
        JedisTalkDao.lpushSaveDialogue(key);
		
        //添加到离线客户列表
        JedisTalkDao.addOffLineUserSet(uId);
        
        //修改接待列表
        JedisTalkDao.remCcnReceiveList(ccnId, customerCcnId);
        JedisTalkDao.delCcnPassiveId(customerCcnId);
        
        JedisTalkDao.addCcnReceiveList(toCcnId, customerCcnId);
        JedisTalkDao.setCcnPassiveId(customerCcnId, toCcnId);
        
        //发送通知
		CometEngine engine = context.getEngine();
		
		//获取通道
		CometConnection ucn = engine.getConnection(ccnId);
		CometConnection tcn = engine.getConnection(toCcnId);
		CometConnection cun = engine.getConnection(customerCcnId);
		
		NoticeData uud = new NoticeData(Constant.ON_SWITCH_FROM, null);
		NoticeData ttd = new NoticeData(Constant.ON_SWITCH_TO, null);
		NoticeData cud = new NoticeData(Constant.ON_SWITCH_CUSTOMER, null);
		
		engine.sendTo(Constant.CHANNEL, ucn, uud);
		engine.sendTo(Constant.CHANNEL, tcn, ttd);
		engine.sendTo(Constant.CHANNEL, cun, cud);
	}
	
	/**
	 * 结束对话操作
	 * @param request
	 * @param response
	 * @param ccnId
	 * @param toUserId
	 * @param customerId
	 * @param remark
	 * @param customerCcnId
	 */
	@RequestMapping(value = "endDialogue.action", method = RequestMethod.POST)
	public void endDialogue(HttpServletRequest request,HttpServletResponse response, String ccnId, Integer type, String endCcnId){
		
		logger.info("endDialogue param ccnId: "+ccnId+" ,type: "+type+" ,endCcnId: "+endCcnId);
		//参数验证
		if(StringUtils.isBlank(ccnId) || type == null || StringUtils.isBlank(endCcnId)){
			logger.warn("endDialogue param is illegal! ");
			return ;
		}
		
		CometEngine engine = context.getEngine();
		
		CometConnection ccn = engine.getConnection(ccnId);
		CometConnection ecn = engine.getConnection(endCcnId);
		
		NoticeData nd = new NoticeData(Constant.END_DIALOGUE, type);
		
		if(JedisConstant.USER_TYPE == type){
			 JedisTalkDao.remCcnReceiveList(ccnId, endCcnId);
		     JedisTalkDao.delCcnPassiveId(endCcnId);
		     
		    //保存会话
	        String key = JedisConstant.getDialogueListKey(ccnId,endCcnId);
	        JedisTalkDao.lpushSaveDialogue(key);
			 
		}else if(JedisConstant.CUSTOMER_TYPE == type){
			JedisTalkDao.remCcnReceiveList(endCcnId, ccnId);
		    JedisTalkDao.delCcnPassiveId(ccnId);
		    
		    //保存会话
	        String key = JedisConstant.getDialogueListKey(endCcnId,ccnId);
	        JedisTalkDao.lpushSaveDialogue(key);
	        
		}
		
		engine.sendTo(Constant.CHANNEL, ccn, nd);
		engine.sendTo(Constant.CHANNEL, ecn, nd);
	}
	
	/**
	 * 访客阻止 （类似于结束对话，多了黑名单操作）
	 * @param request
	 * @param response
	 * @param ccnId
	 * @param customerCcnId
	 * @param remark
	 */
	@RequestMapping(value = "forbidCuntomer.action", method = RequestMethod.POST)
	public void forbidCuntomer(HttpServletRequest request,HttpServletResponse response, String ccnId, String customerCcnId, String remark){
		logger.info("forbidCuntomer param ccnId: "+ccnId+" ,customerCcnId: "+customerCcnId+" ,remark: "+remark);
		if(StringUtils.isBlank(ccnId) || StringUtils.isBlank(customerCcnId)){
			logger.warn("forbidCuntomer param is illegal!");
			return ;
		}
		String uId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, ccnId);
		String cId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, customerCcnId);
		
		Blacklist blacklist = new Blacklist();
		blacklist.setCreateDate(new Date());
		blacklist.setCustomerId(Long.valueOf(cId));
		blacklist.setUserId(Integer.valueOf(uId));
		blacklistService.createNewBlacklist(blacklist);
		
		//修改对话关系
	    JedisTalkDao.remCcnReceiveList(ccnId, customerCcnId);
        JedisTalkDao.delCcnPassiveId(customerCcnId);
	     
	    //保存会话
        String key = JedisConstant.getDialogueListKey(ccnId,customerCcnId);
        JedisTalkDao.lpushSaveDialogue(key);
        
        //发送通知
        CometEngine engine = context.getEngine();
		CometConnection ccn = engine.getConnection(customerCcnId);
		NoticeData nd = new NoticeData(Constant.END_DIALOGUE, null);
		engine.sendTo(Constant.CHANNEL, ccn, nd);
	}
	
	/**
	 * 对话评分
	 * @param request
	 * @param response
	 * @param customerCcnId
	 * @param userCcnId
	 * @param scoreType
	 * @param remark
	 */
	@RequestMapping(value = "socreDialogue.action", method = RequestMethod.POST)
	public void socreDialogue(HttpServletRequest request,HttpServletResponse response, String customerCcnId, String userCcnId, Integer scoreType,String remark){
		
		logger.info("socreDialogue param customerCcnId: "+customerCcnId+" ,userCcnId: "+userCcnId+" ,remark: "+remark+" ,scoreType: "+scoreType);
		//参数检查
		if(StringUtils.isBlank(userCcnId) || StringUtils.isBlank(customerCcnId) || scoreType == null){
			logger.warn("socreDialogue param is illegal!");
			return ;
		}
		
		//会话 key
        String key = JedisConstant.getDialogueListKey(userCcnId,customerCcnId);
        DialogueScore dialogue = new DialogueScore();
        dialogue.setScoreType(scoreType);
        dialogue.setRemark(remark);
        
        JedisTalkDao.setDialogueScore(key, dialogue);
        
	}
	
}
