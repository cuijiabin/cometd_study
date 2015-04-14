/**
 * Copyright (c) Css Team
 * All rights reserved.
 *
 * This file CacheNameUtil.java creation date: [Dec 13, 2010 10:50:10 PM] by hanyu
 * http://www.css.com.cn
 */
/**
 * 
 */
package com.xiaoma.kefu.cache;

import java.util.Date;

/**
 * <descption>缓存工具类</descption>
 * 
 * @author hanyu
 * 
 */
public class CacheUtil {
	public static void main(String[] args) {
		Date d= new Date(System.currentTimeMillis()+ 10* 60 * 1000);
		System.out.println(d);
	}
	/**
	 * 生成缓存标识key
	 * 
	 * @param cacheName
	 * @param value
	 * @return
	 */
	public static String getCacheName(Object cacheName, Object value) {
//		return Md5Util.MD5Encode(new StringBuilder((String) cacheName).append(value).toString());
		return new StringBuilder((String) cacheName).append(value).toString();
	}
	/**
	 * 转换缓存时间格式
	 * 
	 * @param time
	 * @return
	 */
	public static int getCacheTime(int time) {
		return time * 60 * 1000;
	}
	/**
	 * 转换缓存时间格式
	 * 
	 * @param time
	 * @return
	 */
	public static Date getCacheTime(String time) throws Exception {
		return new Date(Integer.valueOf(time) * 60 * 1000);
	}
}
