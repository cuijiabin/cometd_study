package com.xiaoma.kefu.common;

import java.nio.CharBuffer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.CookieUtil;
import com.xiaoma.kefu.util.DesUtil;
import com.xiaoma.kefu.util.PropertiesUtil;
import com.xiaoma.kefu.websocket.WebSocketMI;

public class DialogueUtil {

	public static DialogueUniqueTag genUniqueTag(Integer type,
			HttpSession session, Cookie[] cookies) {

		if (DialogueUniqueTag.CUSTOMER_TYPE == type) {
			String customerInfo = null;
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (CookieUtil.KF_CUSTOMER_ID.equals(cookie.getName())) {
					customerInfo = cookie.getValue();
				}
			}
			if (StringUtils.isBlank(customerInfo)) {
				return null;
			}

			try {
				customerInfo = DesUtil.decrypt(customerInfo,PropertiesUtil.getProperties(CacheName.SECRETKEY));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			return new DialogueUniqueTag(type, Long.valueOf(customerInfo), null);
		}

		if (DialogueUniqueTag.USER_TYPE == type) {
			User user = (User) session.getAttribute("user");

			if (null == user) {
				return null;
			}

			return new DialogueUniqueTag(type, null, user.getId());
		}

		return null;

	}
	
	public static DialogueUniqueTag genUniqueTag(HttpServletRequest request) {
		
		String[] paramArr = request.getPathInfo().split("/");
		if(paramArr == null || paramArr.length < 2){
			return null;
		}
    	Integer type = Integer.valueOf(paramArr[1]);
    	
    	
    	HttpSession session = request.getSession();
    	Cookie[] cookies = request.getCookies();

		if (DialogueUniqueTag.CUSTOMER_TYPE == type) {
			String customerInfo = null;
			if(cookies != null){
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					if (CookieUtil.KF_CUSTOMER_ID.equals(cookie.getName())) {
						customerInfo = cookie.getValue();
					}
				}
				
				try {
					customerInfo = DesUtil.decrypt(customerInfo,PropertiesUtil.getProperties(CacheName.SECRETKEY));
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				
				if (StringUtils.isBlank(customerInfo)) {
					//新生成一个
					customerInfo = "100000000";
				}

				
			}else{
				if (StringUtils.isBlank(customerInfo)) {
					//新生成一个
					customerInfo = "100000000";
				}
			}
			

			return new DialogueUniqueTag(type, Long.valueOf(customerInfo), null);
		}

		if (DialogueUniqueTag.USER_TYPE == type) {
			User user = (User) session.getAttribute("user");

			if (null == user) {
				
				//默认给定一个
				user = new User();
				user.setId(1);
			}
            Long customerId = (StringUtils.isBlank(paramArr[2])) ? null : Long.valueOf(paramArr[2]);
            
			return new DialogueUniqueTag(type, customerId, user.getId());
		}

		return null;

	}
	
	public static String generateMessage(CharBuffer message,WebSocketMI webSocketMI){
		
		//TODO
		
		return message.toString();
	}

	public static void main(String[] args) {
		Integer type = Integer.valueOf("1");
		System.out.println(DialogueUniqueTag.CUSTOMER_TYPE.equals(type));
	}
}
