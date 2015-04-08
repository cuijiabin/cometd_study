package com.xiaoma.kefu.util;

import javax.servlet.http.Cookie;

/**
 * cookie读写
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
}
