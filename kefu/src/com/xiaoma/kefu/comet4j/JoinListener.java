package com.xiaoma.kefu.comet4j;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.CookieUtil;

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
				
				String customerCcnId = JedisTalkDao.popCustomerWaitSet();
				JedisTalkDao.addCcnReceiveList(ccnId, customerCcnId);
				
				//设置被谁接待
				JedisTalkDao.setCcnPassiveId(customerCcnId, ccnId);
				
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
					engine.sendTo(Constant.CHANNEL, myCcn, new NoticeData(Constant.NO_USER, null)); 
					
					return true;
				}
				
				JedisTalkDao.addCcnReceiveList(allocateCnnId, ccnId);
				
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
		        
		        //告知自己已经连接上
		       
		        NoticeData myNd = new NoticeData(Constant.ON_OPEN, message);
		        engine.sendTo(Constant.CHANNEL, myCcn, myNd); 
		        
			} catch (Exception e) {
				e.printStackTrace();
			}

		}


		return true;
	}

}