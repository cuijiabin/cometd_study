package com.xiaoma.kefu.comet4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.DialogueSwitch;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueSwitchService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.JsonUtil;
import com.xiaoma.kefu.util.StudyMapUtil;
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
		Message umessage = new Message(userCId,"我",message,messageTime);
		NoticeData und = new NoticeData(Constant.ON_MESSAGE, umessage);
		
		Message cmessage = new Message(cusCId, user.getCardName(), message, messageTime);
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
		Message umessage = new Message(cusCId, customerName, message, messageTime);
		NoticeData und = new NoticeData(Constant.ON_MESSAGE, umessage);
		
		Message cmessage = new Message(userCId,"我",message,messageTime);
		NoticeData cnd = new NoticeData(Constant.ON_MESSAGE, cmessage);

		CometConnection userCcn = engine.getConnection(userCId);
		CometConnection cusCcn = engine.getConnection(cusCId);

		engine.sendTo(Constant.CHANNEL, userCcn, und);
		engine.sendTo(Constant.CHANNEL, cusCcn, cnd);

		return;

	}

	/**
	 * 获取当前对话列表
	 * 
	 * @param request
	 * @param response
	 * @param ccnId
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "receiveList.action", method = RequestMethod.POST)
	public void customerList(HttpServletRequest request,HttpServletResponse response, String ccnId)
			throws IOException, NoSuchFieldException, SecurityException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		List<DialogueQuene> list = new ArrayList<DialogueQuene>();
		List<String> ccnIds = JedisTalkDao.getCcnReceiveList(ccnId);
		
		if(CollectionUtils.isNotEmpty(ccnIds)){
			
			Map<String, Long> ccnIdCustomerMap = new HashMap<String, Long>();
			
			for (String cId : ccnIds) {
				String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, cId);
				if (customerId != null) {
					ccnIdCustomerMap.put(cId, Long.parseLong(customerId));
				}
			}
			List<Long> customerIds = new ArrayList<Long>(ccnIdCustomerMap.values());
			List<Customer> customers = new ArrayList<Customer>();
			Map<Long, Customer> customerMap = new HashMap<Long, Customer>();
			if(CollectionUtils.isNotEmpty(customerIds)){
				customers = customerService.findByIds(customerIds);
				customerMap = StudyMapUtil.convertList2Map(customers, Customer.class.getDeclaredField("id"));
			}
			
			for (String cId : ccnIds) {
				DialogueQuene dialogueQuene = new DialogueQuene();
				dialogueQuene.setCcnId(cId);
				
				Long customerId = ccnIdCustomerMap.get(cId);
				Customer customer = customerMap.get(customerId);
				if(customer != null){
					dialogueQuene.setCustomer(customer);
				}
				Long millTime = JedisTalkDao.getCcnReceiveScore(ccnId,cId);
				if(millTime != null){
					dialogueQuene.setEnterTime(new Date(millTime));
				}
				
				list.add(dialogueQuene);
			}
		}
		
		CometEngine engine = context.getEngine();
		CometConnection ccn = engine.getConnection(ccnId);
		
		NoticeData nd = new NoticeData(Constant.UPDATE_LIST, list);
		
		engine.sendTo(Constant.CHANNEL, ccn, nd);

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
	public void switchDialogue(HttpServletRequest request,HttpServletResponse response, String ccnId,Integer toUserId,Long customerId,String remark,String customerCcnId){
		
		logger.info("switchDialogue param ccnId: "+ccnId+" ,toUserId: "+toUserId+" ,customerId: "+customerId);
		String uId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, ccnId);
		Integer userId = Integer.valueOf(uId);
		
		//参数检查
		if(userId == null || toUserId == null || customerId == null){
			return ;
		}
		
		//保存转接记录到数据库
		DialogueSwitch dialogueSwitch = new DialogueSwitch();
		dialogueSwitch.setFromUserId(userId);
		dialogueSwitch.setToUserId(toUserId);
		dialogueSwitch.setCustomerId(customerId);
		
		dialogueSwitch.setRemark(remark);
		dialogueSwitch.setCreateDate(new Date());
		
		dialogueSwitchService.addDialogueSwitch(dialogueSwitch);
		
		String toCcnId = JedisTalkDao.getUserCcnList(toUserId.toString()).get(0);
		
		//保存会话
        String key = JedisConstant.getDialogueListKey(ccnId,customerCcnId);
        JedisTalkDao.lpushSaveDialogue(key);
		
        //添加到离线客户列表
        JedisTalkDao.addOffLineUserSet(uId);
        
        //修改接待列表
        JedisTalkDao.remCcnReceiveList(ccnId, customerCcnId);
        JedisTalkDao.addCcnReceiveList(toCcnId, customerCcnId);
        
        //发送通知

		CometEngine engine = context.getEngine();
		CometConnection ccn = engine.getConnection(ccnId);
		
		NoticeData nd = new NoticeData(Constant.UPDATE_LIST, null);
		
		engine.sendTo(Constant.CHANNEL, ccn, nd);
	}
}
