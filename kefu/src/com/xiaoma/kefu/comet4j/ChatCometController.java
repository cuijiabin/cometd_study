package com.xiaoma.kefu.comet4j;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.Blacklist;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.Dialogue;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.DialogueSwitch;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.BlacklistService;
import com.xiaoma.kefu.service.BusiGroupDetailService;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueService;
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
	
	@Autowired
	private DialogueService dialogueService;
	
	@Autowired
	private BusiGroupDetailService busiGroupDetailService;
	
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
			logger.warn("向客户发送消息参数有误，请检查！");
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
		
		User user = (User) CacheMan.getObject(CacheName.SUSER, uId);
		
		String strMessage = JsonUtil.toJson(dialogueDetail);
		JedisTalkDao.addDialogueList(userCId, cusCId, strMessage);
		
		String messageTime = TimeHelper.convertMillisecondToStr(sendTime, TimeHelper.Time_PATTERN);
		
		//包装消息并发送
		Message umessage = new Message(userCId,"我",message,messageTime,"1");
		NoticeData und = new NoticeData(Constant.ON_MESSAGE, umessage);
		
		Message cmessage = new Message(cusCId, user.getCardName(), message, messageTime,"2");
		NoticeData cnd = new NoticeData(Constant.ON_MESSAGE, cmessage);

		CometConnection userCcn = engine.getConnection(userCId);
		CometConnection customerCcn = engine.getConnection(cusCId);

		engine.sendTo(Constant.CHANNEL, userCcn, und);
		engine.sendTo(Constant.CHANNEL, customerCcn, cnd);

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
			logger.warn("向客服发送消息参数有误，请检查！");
			return ;
		}
		Long sendTime = System.currentTimeMillis();
		CometEngine engine = context.getEngine();
		
		String userCId = JedisTalkDao.getCcnPassiveId(cusCId);
		logger.info("send message to user info: userCId: "+userCId+" ,cusCId: "+cusCId+" ,message: "+message);
		
		//如果userCId是空的话，告诉当前用户对话已结束
		if(StringUtils.isBlank(userCId)){
			logger.warn("向客服发送消息参数有误，客户连接id不在接待列表中");
			return ;
		}
		
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
		CometConnection custommerCcn = engine.getConnection(cusCId);

		engine.sendTo(Constant.CHANNEL, userCcn, und);
		engine.sendTo(Constant.CHANNEL, custommerCcn, cnd);

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
			logger.warn("客服转接参数有误，请检查！");
			return ;
		}
		String messageTime = TimeHelper.convertMillisecondToStr(System.currentTimeMillis(), TimeHelper.OTHER_PATTERN);
		
		User user = (User) CacheMan.getObject(CacheName.SUSER, userId);
		User toUser = (User) CacheMan.getObject(CacheName.SUSER, Integer.valueOf(toUserId));
		
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
		
        
        //修改接待列表
        JedisTalkDao.remCcnReceiveList(ccnId, customerCcnId);
        JedisTalkDao.delCcnPassiveId(customerCcnId);
        
        JedisTalkDao.addCcnReceiveList(toCcnId, customerCcnId);
        JedisTalkDao.setCcnPassiveId(customerCcnId, toCcnId);
        
        //发送通知
		CometEngine engine = context.getEngine();
		
		//获取通道
		CometConnection userCcn = engine.getConnection(ccnId);
		CometConnection toUserCcn = engine.getConnection(toCcnId);
		CometConnection customerCcn = engine.getConnection(customerCcnId);
		
		Message message = new Message(customerId.toString(), user.getCardName(), null, messageTime, toUser.getCardName());
		NoticeData uud = new NoticeData(Constant.ON_SWITCH_FROM, message);
		NoticeData tud = new NoticeData(Constant.ON_SWITCH_TO, message);
		NoticeData cud = new NoticeData(Constant.ON_SWITCH_CUSTOMER, message);
		
		engine.sendTo(Constant.CHANNEL, userCcn, uud);
		engine.sendTo(Constant.CHANNEL, toUserCcn, tud);
		engine.sendTo(Constant.CHANNEL, customerCcn, cud);
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
		     
		     String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, endCcnId);
		     DialogueInfo dInfo = JedisTalkDao.getDialogueInfo(customerId,ccnId);
		     dInfo.setCloseType(type);
		     JedisTalkDao.setDialogueInfo(customerId, ccnId, dInfo);
		     
		    //保存会话
	        String key = JedisConstant.getDialogueListKey(ccnId,endCcnId);
	        JedisTalkDao.lpushSaveDialogue(key);
	        
	        //撮合对话
	        String userId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, ccnId);
			User user = (User) CacheMan.getObject(CacheName.SUSER, Integer.valueOf(userId));
			buildDialogue(ccnId,user);
			 
		}else if(JedisConstant.CUSTOMER_TYPE == type){
			JedisTalkDao.remCcnReceiveList(endCcnId, ccnId);
		    JedisTalkDao.delCcnPassiveId(ccnId);
		    
		    String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, ccnId);
		     DialogueInfo dInfo = JedisTalkDao.getDialogueInfo(customerId,endCcnId);
		     dInfo.setCloseType(type);
		     JedisTalkDao.setDialogueInfo(customerId, ccnId, dInfo);
		    
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
		Long customerId = Long.valueOf(cId);
		Integer userId = Integer.valueOf(uId);
		
		Customer customer  = customerService.getCustomerById(customerId);
		User user = (User) CacheMan.getObject(CacheName.SUSER, userId);
		
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = TimeHelper.addHour(startDate, 8);
		
		Blacklist blacklist = new Blacklist();
		blacklist.setIp(customer.getIp());
		blacklist.setIpInfo(customer.getIpInfo());
		
		blacklist.setStartDate(startDate);
		blacklist.setEndDate(endDate);
		
		blacklist.setCustomerId(customerId);
		blacklist.setDescription(remark);
		blacklist.setUserId(userId);
		blacklist.setUserName(user.getCardName());
		blacklist.setCreateDate(new Date());
		
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
		
		//给自己发送通知
		//TODO-不必写，前端自动调整了！
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
        String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, customerCcnId);
        String userId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, userCcnId);
        
        //如果对话结束的话需要保存到数据库中！
        String last = JedisTalkDao.getDialogueLasts(customerId,userCcnId);
        Boolean isSave = StringUtils.isNotBlank(last);
        if(isSave){
        	Dialogue dialogue = dialogueService.getLastBycustomerIdAndUserId(Long.valueOf(customerId), Integer.valueOf(userId));
        	dialogue.setScoreType(scoreType);
        	dialogue.setScoreRemark(remark);
        	dialogueService.update(dialogue);
        	JedisTalkDao.delDialogueInfo(customerId, userCcnId);
        }else{
        	DialogueInfo dInfo = JedisTalkDao.getDialogueInfo(customerId,userCcnId);
            dInfo.setScoreType(scoreType);
            dInfo.setScoreRemark(remark);
            
            JedisTalkDao.setDialogueInfo(customerId, userCcnId, dInfo);
        }
        
	}
	
	@RequestMapping(value = "userOnLine.action", method = RequestMethod.POST)
	public void userOnLine(HttpServletRequest request,HttpServletResponse response, String userCcnId){
		
		logger.info("userOnLine param userCcnId: "+userCcnId);
		//参数检查
		if(StringUtils.isBlank(userCcnId)){
			logger.warn("socreDialogue param is illegal!");
			return ;
		}
		
		String userId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, userCcnId);
		Boolean isOnline = JedisTalkDao.isInOffLineUserSet(userId);
		if(isOnline){
			JedisTalkDao.remOffLineUserSet(userId);
			
			//撮合对话
			User user = (User) CacheMan.getObject(CacheName.SUSER, Integer.valueOf(userId));
			buildDialogue(userCcnId,user);
		}else{
			JedisTalkDao.addOffLineUserSet(userId);
		}
		
	}
	
	/**
	 * 撮合对话
	 * @param ccnId
	 * @param user
	 */
	private void buildDialogue(String ccnId,User user){
		
		CometEngine engine = context.getEngine();
		if(JedisTalkDao.sizeCustomerWaitSet() > 0){
			Integer surplusSize = 1;//剩余可分配客户名额
			List<DictItem> list = DictMan.getDictList("d_dialog_android");
			List<Integer> styleIds = busiGroupDetailService.getStyleIdsByuser(user);
			if(CollectionUtils.isEmpty(styleIds)){
				return ;
			}
			
			while(surplusSize > 0 && JedisTalkDao.sizeCustomerWaitSet() > 0){
				String customerCcnId = JedisTalkDao.popCustomerWaitSet();
				
				String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, customerCcnId);
				DialogueInfo dInfo = JedisTalkDao.getDialogueInfo(customerId,null);
				Integer styleId = (dInfo.getStyleId() == null) ? 1 : dInfo.getStyleId();
				
				//如果风格相同则分配对话
				if(!styleIds.contains(styleId)){
					continue;
				}
				
				Integer waitTime = JedisTalkDao.getCustomerWaitTime(customerCcnId);
				JedisTalkDao.delCustomerWaitSet(customerCcnId);
				
				logger.info("客服："+user.getId()+" ,接待通信点id： "+customerCcnId);
				
				//接待设置：两方面
				JedisTalkDao.addCcnReceiveList(ccnId, customerCcnId);
				JedisTalkDao.setCcnPassiveId(customerCcnId, ccnId);
				
				//修改对话缓存
				JedisTalkDao.delDialogueInfo(customerId, null);
				dInfo.setUserCcnId(ccnId);
				dInfo.setUserId(user.getId());
				dInfo.setDeptId(user.getDeptId());
				dInfo.setCardName(user.getCardName());
				dInfo.setWaitTime(waitTime);
				JedisTalkDao.setDialogueInfo(customerId, ccnId, dInfo);
				
				//通知客更新后台列表
		        CometConnection userCcn = engine.getConnection(ccnId);
		        CometConnection customerCcn = engine.getConnection(customerCcnId);
		        
				//通知数据
		        Message message = new Message(ccnId, user.getCardName(), "", "", customerCcnId);
				NoticeData nd = new NoticeData(Constant.ON_OPEN, message);
		        engine.sendTo(Constant.CHANNEL, userCcn, nd); 
		        engine.sendTo(Constant.CHANNEL, customerCcn, nd); 
		        
		        surplusSize = JedisTalkDao.getLastReceiveCount(user.getId().toString());
		        
		        if(CollectionUtils.isNotEmpty(list)){
		        	for(DictItem dictItem : list){
		        		JoinListener.talkToCustomer(engine,ccnId,customerCcnId,dictItem.getItemName());
		        	}
		        }
			}
			
		}
	}
	
}
