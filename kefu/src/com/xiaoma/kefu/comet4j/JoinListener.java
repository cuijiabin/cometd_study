package com.xiaoma.kefu.comet4j;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

import com.cloopen.rest.sdk.utils.PropertiesUtil;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.redis.JedisDao;
import com.xiaoma.kefu.redis.SystemConfiguration;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.DesUtil;

/**
 * @description JoinListener
 * @author cuijiabin
 */
public class JoinListener extends ConnectListener {

	private CustomerService customerService = (CustomerService)SpringContextUtil.getBean("customerService");

	private UserService userService  = (UserService)SpringContextUtil.getBean("userService");

	public boolean handleEvent(ConnectEvent anEvent) {

		CometConnection conn = anEvent.getConn();
		HttpServletRequest request = conn.getRequest();

		String userId = request.getParameter("userId");

		String value = userId;
		String type = "2###";
		
		String cookieVal = "";

		// 优先使用userId
		if (StringUtils.isNotBlank(userId)) {
			userService.getUserById(Integer.valueOf(userId));
		} else {
			try {
				Customer customer = customerService.genCustomer(request);
				value = customer.getId().toString();
				type = "1###";
				// 写入cookie
				cookieVal = DesUtil.encrypt(value,SystemConfiguration.getInstance().getSecretKey());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		// 创建对象
		JoinDTO dto = new JoinDTO(conn.getId(), value);
		dto.setCookieVal(cookieVal);

		// 放入全集缓存中
		JedisDao.getJedis().set(type + conn.getId(), value);

		// 广播
		((CometEngine) anEvent.getTarget()).sendToAll("talker", dto);

		return true;
	}

}