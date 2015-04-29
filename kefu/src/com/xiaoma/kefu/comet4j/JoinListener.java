package com.xiaoma.kefu.comet4j;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.CookieUtil;
import com.xiaoma.kefu.util.JsonUtil;
import com.xiaoma.kefu.util.TimeHelper;

/**
 * @description JoinListener
 * @author cuijiabin
 */
public class JoinListener extends ConnectListener {


	private UserService userService = (UserService) SpringContextUtil.getBean("userService");
	private Logger logger = Logger.getLogger(JoinListener.class);

	public boolean handleEvent(ConnectEvent anEvent) {

		CometConnection conn = anEvent.getConn();
		HttpServletRequest request = conn.getRequest();
		
		String referer = request.getHeader("referer");
		
		logger.info("JoinListener referer: "+referer);

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		
		// 连接点
		String ccnId = conn.getId();
		
		CometEngine engine = (CometEngine) anEvent.getTarget();

		// 优先使用userId
		if (user != null) {
			String userId = user.getId().toString();
			// 添加至当前用户通信点
			JedisTalkDao.setCnnUserId(JedisConstant.USER_TYPE, ccnId, userId);
			JedisTalkDao.addUserCcnList(userId, ccnId);
			JedisTalkDao.addCcnList( JedisConstant.USER_TYPE, ccnId);
			logger.info("客服："+userId+" ,进入对话系统; 通信点id： "+ccnId);
			
			//为客服分配客户
			if(JedisTalkDao.sizeCustomerWaitSet() > 0){
				Integer surplusSize = 1;//剩余可分配客户名额
				List<DictItem> list = DictMan.getDictList("d_dialog_android");
				
				while(surplusSize > 0 && JedisTalkDao.sizeCustomerWaitSet() > 0){
					String customerCcnId = JedisTalkDao.popCustomerWaitSet();
					Integer waitTime = JedisTalkDao.getCustomerWaitTime(customerCcnId);
					JedisTalkDao.delCustomerWaitSet(customerCcnId);
					
					JedisTalkDao.addCcnReceiveList(ccnId, customerCcnId);
					
					//设置被谁接待
					JedisTalkDao.setCcnPassiveId(customerCcnId, ccnId);
					
					//修改对话缓存
					String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, customerCcnId);
					DialogueInfo dInfo = JedisTalkDao.getDialogueScore(customerId,null);
					JedisTalkDao.delDialogueInfo(customerId, null);
					dInfo.setUserCcnId(ccnId);
					dInfo.setUserId(user.getId());
					dInfo.setDeptId(user.getDeptId());
					dInfo.setCardName(user.getCardName());
					dInfo.setWaitTime(waitTime);
					JedisTalkDao.setDialogueInfo(customerId, ccnId, dInfo);
					
					//通知客更新后台列表
			        CometConnection ccn = engine.getConnection(ccnId);
			        CometConnection myCcn = engine.getConnection(customerCcnId);
			        
					//通知数据
					NoticeData nd = new NoticeData(Constant.ON_OPEN, null);
			        engine.sendTo(Constant.CHANNEL, ccn, nd); 
			        
			        Message message = new Message(ccnId, user.getCardName(), "", "", ccnId);
			        
			        //告知自己已经连接上
			        NoticeData myNd = new NoticeData(Constant.ON_OPEN, message);
			        engine.sendTo(Constant.CHANNEL, myCcn, myNd); 
			        
			        surplusSize = JedisTalkDao.getMaxReceiveCount(user.getId().toString()) - JedisTalkDao.getReceiveCount(user.getId().toString());
			        
			        if(CollectionUtils.isNotEmpty(list)){
			        	for(DictItem dictItem : list){
			        		talkToCustomer(engine,ccnId,customerCcnId,dictItem.getItemName());
			    			try {
			    				Thread.sleep(1000L);
			    			} catch (Exception ex) {
			    				ex.printStackTrace();
			    			}
			        	}
			        }
				}
				
			}
			
			
		} else {
			try {
				
				String customerId = CookieUtil.getCustomerIdFromCookie(request);

				// 添加至当前用户通信点
				JedisTalkDao.setCnnUserId(JedisConstant.CUSTOMER_TYPE, ccnId, customerId);
				JedisTalkDao.addUserCcnList(customerId, ccnId);
				JedisTalkDao.addCcnList(JedisConstant.CUSTOMER_TYPE, ccnId);

				CometConnection myCcn = engine.getConnection(ccnId);

				// 分配客服
				String allocateCnnId = JedisTalkDao.allocateCcnId();
				if(StringUtils.isBlank(allocateCnnId)){
					//对不起，客服不在线，请留言
					JedisTalkDao.addCustomerWaitSet(ccnId);
					DialogueInfo dInfo = JedisTalkDao.getDialogueScore(customerId, null);
					dInfo.setIsWait(1);
					JedisTalkDao.setDialogueInfo(customerId, null, dInfo);
					
					engine.sendTo(Constant.CHANNEL, myCcn, new NoticeData(Constant.NO_USER, null)); 
					
					return true;
				}
				
				JedisTalkDao.addCcnReceiveList(allocateCnnId, ccnId);
				DialogueInfo dInfo = JedisTalkDao.getDialogueScore(customerId, null);
				JedisTalkDao.delDialogueInfo(customerId, null);
				
				
				//设置被谁接待
				JedisTalkDao.setCcnPassiveId(ccnId, allocateCnnId);
				logger.info("前端用户："+customerId+" ,进入对话系统; 通信点id： "+ccnId+"被通知客服通信点id："+allocateCnnId);
				
				//通知客更新后台列表
		        CometConnection ccn = engine.getConnection(allocateCnnId);
		        
				//通知数据
				NoticeData nd = new NoticeData(Constant.ON_OPEN, null);
		        engine.sendTo(Constant.CHANNEL, ccn, nd); 
		        
		        String uId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, allocateCnnId);
		        User allocateUser = userService.getUserById(Integer.valueOf(uId));
		        Message message = new Message(allocateCnnId, allocateUser.getCardName(), "", "", ccnId);
		        
		        dInfo.setUserCcnId(allocateCnnId);
				dInfo.setUserId(allocateUser.getId());
				dInfo.setDeptId(allocateUser.getDeptId());
				dInfo.setCardName(allocateUser.getCardName());
				JedisTalkDao.setDialogueInfo(customerId, allocateCnnId, dInfo);
		        
		        //告知自己已经连接上
		       
		        NoticeData myNd = new NoticeData(Constant.ON_OPEN, message);
		        engine.sendTo(Constant.CHANNEL, myCcn, myNd);
		        
		        List<DictItem> list = DictMan.getDictList("d_dialog_android");
		        if(CollectionUtils.isNotEmpty(list)){
		        	for(DictItem dictItem : list){
		        		talkToCustomer(engine,allocateCnnId,ccnId,dictItem.getItemName());

		    			try {
		    				Thread.sleep(1000L);
		    			} catch (Exception ex) {
		    				ex.printStackTrace();
		    			}
		        	}
		        }
				
		        
			} catch (Exception e) {
				e.printStackTrace();
			}

		}


		return true;
	}
	
	private void talkToCustomer(CometEngine engine,String userCId, String cusCId,String message) {

		logger.info("send message to customer info: userCId: "+userCId+" ,cusCId: "+cusCId+" ,message: "+message);
		//参数检查
		if(StringUtils.isBlank(userCId) || StringUtils.isBlank(cusCId) || StringUtils.isBlank(message)){
			
			return ;
		}
		
		Long sendTime = System.currentTimeMillis();
		
		//获取用户id
		String userId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, userCId);
		String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, cusCId);
		Integer uId = Integer.valueOf(userId);
		Long cId = Long.valueOf(customerId);
		
		//把消息放入redis
		DialogueDetail dialogueDetail = new DialogueDetail();
		dialogueDetail.setContent(message);
		dialogueDetail.setDialogueType(3);
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

}