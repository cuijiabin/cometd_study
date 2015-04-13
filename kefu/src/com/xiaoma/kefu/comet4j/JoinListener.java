package com.xiaoma.kefu.comet4j;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.UserService;

/**
 * @description JoinListener
 * @author cuijiabin
 */
public class JoinListener extends ConnectListener {

	private CustomerService customerService = (CustomerService) SpringContextUtil
			.getBean("customerService");

	private UserService userService = (UserService) SpringContextUtil
			.getBean("userService");

	public boolean handleEvent(ConnectEvent anEvent) {

		CometConnection conn = anEvent.getConn();
		HttpServletRequest request = conn.getRequest();

		HttpSession session = request.getSession();

		Object obj = session.getAttribute("user");
		String sessionUserId = (obj == null) ? "" : obj.toString();

		
		// 连接点
		String ccnId = conn.getId();

		// 优先使用userId
		if (StringUtils.isNotBlank(sessionUserId)) {
			
			// 添加至当前用户通信点
			JedisTalkDao.addCurrentUser( JedisConstant.USER_TYPE, ccnId, sessionUserId);
			JedisTalkDao.addUserCcnList(sessionUserId, ccnId);
			JedisTalkDao.addCcnList( JedisConstant.USER_TYPE, ccnId);

			// 添加当前用户列表
			JedisTalkDao.addCurrentUserList( JedisConstant.USER_TYPE, sessionUserId);
		} else {
			try {
				Customer customer = customerService.genCustomer(request);
				String customerId = customer.getId().toString();
				

				// 添加至当前用户通信点
				JedisTalkDao.addCurrentUser(JedisConstant.CUSTOMER_TYPE, ccnId, customerId);
				JedisTalkDao.addUserCcnList(customerId, ccnId);
				JedisTalkDao.addCcnList(JedisConstant.CUSTOMER_TYPE, ccnId);

				// 添加当前用户列表
				JedisTalkDao.addCurrentUserList(JedisConstant.CUSTOMER_TYPE, customerId);

				// 分配客服
				List<String> userIds = JedisTalkDao.getCurrentUserList(JedisConstant.USER_TYPE);
				Integer allocateUserId = JedisTalkDao.allocateUserId(userIds);

				JedisTalkDao.addReceiveList(allocateUserId, customerId);
				JedisTalkDao.incrCurrentReceiveCount(allocateUserId);
				// 写入cookie
				// DesUtil.encrypt(userId,PropertiesUtil.getProperties(CacheName.SECRETKEY));
				
				//通知客更新后台列表
				CometEngine engine = (CometEngine) anEvent.getTarget();
				
				List<String> ccnIds = JedisTalkDao.getUserCcnList(allocateUserId.toString());
				List<CometConnection> ccns = new ArrayList<CometConnection>();
				for(String uccnId : ccnIds){
		            CometConnection ccn = engine.getConnection(uccnId);
//		            if(ccn == null){
//		                JedisTalkDao.delCurrentUser(JedisConstant.USER_TYPE, uccnId);
//		                JedisTalkDao.delCcnList(JedisConstant.USER_TYPE, uccnId);
//		            }
		            ccns.add(ccn);
		        }
				//通知数据
				NoticeData nd = new NoticeData(Constant.UP, null);
		        engine.sendTo("talker", ccns, nd); 
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		

		// 对所有后台用户广播

		return true;
	}

}