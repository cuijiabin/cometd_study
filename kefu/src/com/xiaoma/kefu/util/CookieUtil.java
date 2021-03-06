package com.xiaoma.kefu.util;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.xiaoma.kefu.redis.SystemConfiguration;

/**
 * cookie读写
 * 
 * @author cuijiabin
 *
 */
public class CookieUtil {

	public static String KF_CUSTOMER_ID = "KF_CUSTOMER_ID";

	public static Cookie genCookieByCustomerId(String cusatomerId) {

		String value = null;
		try {
			value = DesUtil.encrypt(cusatomerId, SystemConfiguration
					.getInstance().getSecretKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Cookie(KF_CUSTOMER_ID, value);

	}

	public static Cookie getCustomerFromCookies(Cookie[] cookies) {
		if (cookies == null) {
			return null;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (KF_CUSTOMER_ID.equals(cookie.getName())) {
				return cookie;
			}
		}

		return null;

	}

	public static Cookie getCustomerCookie(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();

		return getCustomerFromCookies(cookies);

	}

	public static String getCustomerIdFromCookie(HttpServletRequest request) {
		Cookie cookie = getCustomerCookie(request);
		String customerId = null;
		
		if (cookie != null) {
			try {
				customerId = DesUtil.decrypt(cookie.getValue(),SystemConfiguration.getInstance().getSecretKey());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return customerId;

	}

	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

		}

		return ip;

	}
}
