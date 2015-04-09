package com.xiaoma.kefu.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie读写
 * 
 * @author cuijiabin
 *
 */
public class CookieUtil {

	public static String KF_CUSTOMER_ID = "KF_CUSTOMER_ID";

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
