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

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.BusiGroupDetailService;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.util.CookieUtil;
import com.xiaoma.kefu.util.JsonUtil;
import com.xiaoma.kefu.util.TimeHelper;

/**
 * 进入长连接监听
 * @author cuijiabin
 * 1.判断类型（客服、客户）
 * 2.加入redis对话信息
 * 3.撮合对话
 * 4.通知后台
 */
public class JoinListener extends ConnectListener {


	private CustomerService customerService = (CustomerService) SpringContextUtil.getBean("customerService");
	private BusiGroupDetailService busiGroupDetailService = (BusiGroupDetailService) SpringContextUtil.getBean("busiGroupDetailService");
	
	private Logger logger = Logger.getLogger(JoinListener.class);

	public boolean handleEvent(ConnectEvent anEvent) {

		CometConnection conn = anEvent.getConn();
		HttpServletRequest request = conn.getRequest();
		
		//根据referer 判断是否为客户
		String referer = request.getHeader("referer");
		logger.info("JoinListener referer: "+referer);
		
		boolean isCustomer = StringUtils.isBlank(referer) ? false : referer.contains("customerChat.action");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		
		// 当前连接点
		String ccnId = conn.getId();
		CometEngine engine = (CometEngine) anEvent.getTarget();

		// 根据session与referer判断是客户还是客服进入连接
		if (user != null && !isCustomer) {
			String userId = user.getId().toString();
			// 添加至当前用户通信点
			JedisTalkDao.setCnnUserId(JedisConstant.USER_TYPE, ccnId, userId);
			JedisTalkDao.addUserCcnList(userId, ccnId);
			JedisTalkDao.addCcnList( JedisConstant.USER_TYPE, ccnId);
			logger.info("客服："+userId+" ,进入对话系统; 通信点id： "+ccnId);
			
			List<Integer> styleIds = busiGroupDetailService.getStyleIdsByuserId(user.getId());
			if(CollectionUtils.isEmpty(styleIds)){
				return true;
			}
			
			//为客服分配客户
			if(JedisTalkDao.sizeCustomerWaitSet() > 0){
				Integer surplusSize = 1;//剩余可分配客户名额
				List<DictItem> list = DictMan.getDictList("d_dialog_android");
				
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
					
					logger.info("客服："+userId+" ,接待通信点id： "+customerCcnId);
					
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
			        		talkToCustomer(engine,ccnId,customerCcnId,dictItem.getItemName());
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

				logger.info("客户："+customerId+" ,进入对话系统;");
				
				CometConnection customerCcn = engine.getConnection(ccnId);
				
				DialogueInfo dInfo = JedisTalkDao.getDialogueInfo(customerId, null);
				Integer styleId = (dInfo.getStyleId() == null) ? 1 : dInfo.getStyleId();
				List<Integer> onlineUserIds = CacheMan.getOnlineUserIdsByStyleId(styleId);
				
				//对不起，客服不在线，请留言
				if(CollectionUtils.isEmpty(onlineUserIds)){
					JedisTalkDao.addCustomerWaitSet(ccnId);
					dInfo.setIsWait(1);
					JedisTalkDao.setDialogueInfo(customerId, null, dInfo);
					logger.info("客户："+customerId+" ,进入等待队列！");
					engine.sendTo(Constant.CHANNEL, customerCcn, new NoticeData(Constant.NO_USER, null)); 
					return true;
				}
				
				// 分配客服
				String allocateCnnId = JedisTalkDao.allocateCcnIdByStyleId(onlineUserIds,styleId);
				
				//对不起，客服正忙，请留言
				if(StringUtils.isBlank(allocateCnnId)){
					JedisTalkDao.addCustomerWaitSet(ccnId);
					
					dInfo.setIsWait(1);
					JedisTalkDao.setDialogueInfo(customerId, null, dInfo);
					logger.info("客户："+customerId+" ,进入等待队列！");
					engine.sendTo(Constant.CHANNEL, customerCcn, new NoticeData(Constant.USER_BUSY, null)); 
					
					return true;
				}
				
				//设置接待
				JedisTalkDao.addCcnReceiveList(allocateCnnId, ccnId);
				JedisTalkDao.setCcnPassiveId(ccnId, allocateCnnId);
				
				JedisTalkDao.delDialogueInfo(customerId, null);
				
				logger.info("客户："+customerId+" 通信点id： "+ccnId+"被接待，客服通信点id："+allocateCnnId);
				
				//通知客更新后台列表
		        CometConnection userCcn = engine.getConnection(allocateCnnId);
		        
				//通知数据
		        String uId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, allocateCnnId);
		        User allocateUser = (User) CacheMan.getObject(CacheName.SUSER, uId);
		        Message message = new Message(allocateCnnId, allocateUser.getCardName(), "", "", ccnId);
				NoticeData nd = new NoticeData(Constant.ON_OPEN, message);
				
				//通知连接
		        engine.sendTo(Constant.CHANNEL, userCcn, nd); 
		        engine.sendTo(Constant.CHANNEL, customerCcn, nd);
		        
		        //保存对话信息
		        dInfo.setUserCcnId(allocateCnnId);
				dInfo.setUserId(allocateUser.getId());
				dInfo.setDeptId(allocateUser.getDeptId());
				dInfo.setCardName(allocateUser.getCardName());
				JedisTalkDao.setDialogueInfo(customerId, allocateCnnId, dInfo);
		        
		        List<DictItem> list = DictMan.getDictList("d_dialog_android");
		        if(CollectionUtils.isNotEmpty(list)){
		        	for(DictItem dictItem : list){
		        		talkToCustomer(engine,allocateCnnId,ccnId,dictItem.getItemName());
		        	}
		        }
				
		        
			} catch (Exception e) {
				logger.warn("客户进入对话系统失败！");
				e.printStackTrace();
			}

		}


		return true;
	}
	
	/**
	 * 自动与客户对话
	 * @param engine
	 * @param userCId
	 * @param cusCId
	 * @param message
	 */
	public static void talkToCustomer(CometEngine engine,String userCId, String cusCId,String message) {

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
		
		User user = (User) CacheMan.getObject(CacheName.SUSER, userId);
		
		String strMessage = JsonUtil.toJson(dialogueDetail);
		JedisTalkDao.addDialogueList(userCId, cusCId, strMessage);
		
		String messageTime = TimeHelper.convertMillisecondToStr(sendTime, TimeHelper.Time_PATTERN);
		
		//包装消息并发送
		Message umessage = new Message(cusCId,"我",message,messageTime,"1");
		NoticeData und = new NoticeData(Constant.ON_MESSAGE, umessage);
		
		Message cmessage = new Message(cusCId, user.getCardName(), message, messageTime,"2");
		NoticeData cnd = new NoticeData(Constant.ON_MESSAGE, cmessage);

		CometConnection userCcn = engine.getConnection(userCId);
		CometConnection customerCcn = engine.getConnection(cusCId);

		engine.sendTo(Constant.CHANNEL, userCcn, und);
		engine.sendTo(Constant.CHANNEL, customerCcn, cnd);

		return;

	}

}