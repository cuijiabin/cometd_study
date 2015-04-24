package com.xiaoma.kefu.comet4j;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

		// 优先使用userId
		if (user != null) {
			String userId = user.getId().toString();
			// 添加至当前用户通信点
			JedisTalkDao.setCnnUserId(JedisConstant.USER_TYPE, ccnId, userId);
			JedisTalkDao.addUserCcnList(userId, ccnId);
			JedisTalkDao.addCcnList( JedisConstant.USER_TYPE, ccnId);
			logger.info("客服："+userId+" ,进入对话系统; 通信点id： "+ccnId);
		} else {
			try {
				
				String customerId = CookieUtil.getCustomerIdFromCookie(request);

				// 添加至当前用户通信点
				JedisTalkDao.setCnnUserId(JedisConstant.CUSTOMER_TYPE, ccnId, customerId);
				JedisTalkDao.addUserCcnList(customerId, ccnId);
				JedisTalkDao.addCcnList(JedisConstant.CUSTOMER_TYPE, ccnId);


				// 分配客服
				String allocateCnnId = JedisTalkDao.allocateCcnId();
				
				JedisTalkDao.addCcnReceiveList(allocateCnnId, ccnId);
				
				//设置被谁接待
				JedisTalkDao.setCcnPassiveId(ccnId, allocateCnnId);
				logger.info("前端用户："+customerId+" ,进入对话系统; 通信点id： "+ccnId+"被通知客服通信点id："+allocateCnnId);
				
				//通知客更新后台列表
				CometEngine engine = (CometEngine) anEvent.getTarget();
		        CometConnection ccn = engine.getConnection(allocateCnnId);
		        
				//通知数据
				NoticeData nd = new NoticeData(Constant.ON_OPEN, null);
		        engine.sendTo(Constant.CHANNEL, ccn, nd); 
		        
		        String uId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, allocateCnnId);
		        User allocateUser = userService.getUserById(Integer.valueOf(uId));
		        Message message = new Message(allocateCnnId, allocateUser.getCardName(), "", "", ccnId);
		        
		        //告知自己已经连接上
		        CometConnection myCcn = engine.getConnection(ccnId);
		        NoticeData myNd = new NoticeData(Constant.ON_OPEN, message);
		        engine.sendTo(Constant.CHANNEL, myCcn, myNd); 
		        
			} catch (Exception e) {
				e.printStackTrace();
			}

		}


		return true;
	}

}