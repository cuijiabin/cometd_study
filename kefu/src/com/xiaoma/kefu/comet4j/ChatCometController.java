package com.xiaoma.kefu.comet4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.comet4j.core.CometConnection;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.util.JsonUtil;
import com.xiaoma.kefu.util.StudyMapUtil;
import com.xiaoma.kefu.util.TimeHelper;

@Controller
@RequestMapping(value = "chat")
public class ChatCometController {

	@Autowired
	private CustomerService customerService;

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

		Long sendTime = System.currentTimeMillis();
		
		CometEngine engine = context.getEngine();
		String toId = JedisTalkDao.getTalkerCcnId(JedisConstant.USER_TYPE,userCId);

		// 添加对话链接
		if (toId == null) {
			JedisTalkDao.setTalkerCcnId(JedisConstant.USER_TYPE, userCId, cusCId);
			JedisTalkDao.setTalkerCcnId(JedisConstant.CUSTOMER_TYPE, cusCId,userCId);
		}
		
		//把消息放入redis
		DialogueDetail dialogueDetail = new DialogueDetail();
		dialogueDetail.setContent(message);
		dialogueDetail.setDialogueType(2);
		dialogueDetail.setCreateDate(new Date(sendTime));
		dialogueDetail.setUserId(null);
		dialogueDetail.setCustomerId(null);
		dialogueDetail.setCardName(null);
		String strMessage = JsonUtil.toJson(dialogueDetail);
		
		JedisTalkDao.addDialogueList(userCId, cusCId, strMessage);
		
		String messageTime = TimeHelper.convertMillisecondToStr(sendTime, TimeHelper.Time_PATTERN);
		
		//包装消息并发送
		Message umessage = new Message(userCId,"我",message,messageTime);
		NoticeData und = new NoticeData(Constant.ON_MESSAGE, umessage);
		
		Message cmessage = new Message(cusCId,"客服",message,messageTime);
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
        
		Long sendTime = System.currentTimeMillis();
		CometEngine engine = context.getEngine();
		
		String userCId = JedisTalkDao.getCcnPassiveId(cusCId);
		String toId = JedisTalkDao.getTalkerCcnId(JedisConstant.USER_TYPE,cusCId);

		// 添加对话链接
		if (toId == null) {
			JedisTalkDao.setTalkerCcnId(JedisConstant.CUSTOMER_TYPE, cusCId,userCId);
			JedisTalkDao.setTalkerCcnId(JedisConstant.USER_TYPE, userCId, cusCId);
			
		}
		
		//把消息放入redis
		DialogueDetail dialogueDetail = new DialogueDetail();
		dialogueDetail.setContent(message);
		dialogueDetail.setDialogueType(1);
		dialogueDetail.setCreateDate(new Date(sendTime));
		dialogueDetail.setUserId(null);
		dialogueDetail.setCustomerId(null);
		dialogueDetail.setCardName(null);
		String strMessage = JsonUtil.toJson(dialogueDetail);
		
		JedisTalkDao.addDialogueList(userCId, cusCId, strMessage);
		
		String messageTime = TimeHelper.convertMillisecondToStr(sendTime, TimeHelper.Time_PATTERN);
		
		//包装消息并发送
		Message umessage = new Message(userCId,"客户",message,messageTime);
		NoticeData und = new NoticeData(Constant.ON_MESSAGE, umessage);
		
		Message cmessage = new Message(cusCId,"我",message,messageTime);
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

		List<String> ccnIds = JedisTalkDao.getCcnReceiveList(ccnId);
		Map<String, Long> ccnIdCustomerMap = new HashMap<String, Long>();
		for (String cId : ccnIds) {
			String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, cId);
			if (customerId != null) {
				ccnIdCustomerMap.put(cId, Long.parseLong(customerId));
			}

		}
		List<Long> customerIds = new ArrayList<Long>(ccnIdCustomerMap.values());
		List<Customer> customers = customerService.findByIds(customerIds);
		
		List<DealQuene> list = DealQuene.buildDealQuenes(ccnIdCustomerMap, customers);
		
		CometEngine engine = context.getEngine();
		CometConnection ccn = engine.getConnection(ccnId);
		
		NoticeData nd = new NoticeData(Constant.UPDATE_LIST, list);
		
		engine.sendTo(Constant.CHANNEL, ccn, nd);

		return;

	}
}
