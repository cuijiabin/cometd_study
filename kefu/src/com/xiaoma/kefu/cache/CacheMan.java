package com.xiaoma.kefu.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;

import com.xiaoma.kefu.redis.JedisDao;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.util.JsonUtil;

public class CacheMan {

	private static Log log = LogFactory.getLog(CacheMan.class);

	// 获取缓存对象；param1:缓存前缀，param2:对应的id
	public static <T> Object getObject(String cacheName, Object value,Class<T> clazz) {
		try {
			String key = CacheUtil.getCacheName(cacheName, value);
			Object obj = JedisDao.getObject(key,clazz);
			if (obj == null) {
				obj = CacheFactory.factory(cacheName, value);
				if (obj != null)
					JedisDao.setKO(key, obj);
			}
			return obj;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	public static List<Integer> getOnlineUserIdsByStyleId(Integer styleId) {
		try {
			String key = CacheUtil.getCacheName(CacheName.ONLINE_USER_STYLEID, styleId);
			Object obj = JedisDao.getObject(key,List.class);
			if (obj == null) {
				obj = CacheFactory.factory(CacheName.ONLINE_USER_STYLEID, styleId);
				if (obj != null)
					JedisDao.setKO(key, obj);
			}
			List<Integer> list = (List<Integer>) obj;
			Set<Integer> userIds = new HashSet<Integer>(list);
			List<String> ids = JedisTalkDao.getSwitchList();
			Set<Integer> onUserIds = JsonUtil.convertString2IntegerSet(ids);
			if(CollectionUtils.isEmpty(onUserIds)){
				return null;
			}
			onUserIds.retainAll(userIds);
			return new ArrayList<Integer>(onUserIds);
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
			JedisDao.setKOT(key, obj, CacheUtil.getCacheTime(time));
			return obj;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	// 删除指定缓存对象；param1:缓存前缀，param2:对应的id
	public static void remove(Object cacheName, Object value) {
		try {
			JedisDao.remove(CacheUtil.getCacheName(cacheName, value));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	// 根绝key匹配删除
	public static void removeByKeyPattern(String cacheName) {
		try {
			Jedis jedis = JedisDao.getJedis();
			Set<String> keys = jedis.keys(cacheName + "*");
			if(keys == null || keys.size() < 1){
				return ;
			}
			String[] delKeys = new String[keys.size()];
			delKeys = keys.toArray(delKeys);
			jedis.del(delKeys);
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
	
	public static void main(String[] args) {
		List<Integer> list = getOnlineUserIdsByStyleId(1);
		for(Integer i : list){
			System.out.println(i);
		}
	}
	
	
	
	/**
	 * 更新缓存对象	,先删除,再add
	* @param cacheName	缓存前缀
	* @param value	对应的id
	* @param obj	最新值
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public static Object update(String cacheName, Object value, Object obj) {
		try {
			remove(cacheName, value);
			obj = add(cacheName, value, obj);
			return obj;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

}
