package com.xiaoma.kefu.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaoma.kefu.redis.JedisDao;

public class CacheMan {

	private static Log log = LogFactory.getLog(CacheMan.class);

	// 获取缓存对象；param1:缓存前缀，param2:对应的id
	public static Object getObject(String cacheName, Object value) {
		try {
			String key = CacheUtil.getCacheName(cacheName, value);
			return JedisDao.getObject(key);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	// 设置缓存对象（永久缓存），包括新增、修改功能；param1:缓存前缀，param2:对应的id，param3:要缓存的值
	public static Object add(String cacheName, Object value, Object obj) {
		try {
			String key = CacheUtil.getCacheName(cacheName, value);
			JedisDao.setKO(key, obj);
			return obj;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	// 设置缓存对象（默认10分钟缓存），包括新增、修改功能；param1:缓存前缀，param2:对应的id，param3:要缓存的值
	// 至于设定特殊缓存时间的功能，1：开放带有缓存时间的函数；2：新增函数，更换缓存函数名；
	public static Object addObjectTimer(String cacheName, Object value,
			Object obj, int time) {
		try {
			String key = CacheUtil.getCacheName(cacheName, value);
			JedisDao.setKOT(key, obj,CacheUtil.getCacheTime(time));
			return obj;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	// 删除指定缓存对象；param1:缓存前缀，param2:对应的id
	public static void remove(Object cacheName, Object value) {
		try {
			JedisDao.remove(
					CacheUtil.getCacheName(cacheName, value));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	// 删除所有服务器缓存对象，谨慎使用
	public static void removeAll() {
		try {
			JedisDao.flushAll();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

}
