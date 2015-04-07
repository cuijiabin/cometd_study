package com.xiaoma.kefu.redis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.util.PropertiesUtil;
import com.xiaoma.kefu.util.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 
 * redis操作封装
 * 
 * @author cuijiabin
 * 
 */
public class JedisDao {
	
	private static Log log = LogFactory.getLog(JedisDao.class);
	private static Jedis jedis;
	private static JedisPool pool;
	
	//redisConf
	private static String host = PropertiesUtil.getProperties(CacheName.REDISHOST);
	private static String port = PropertiesUtil.getProperties(CacheName.REDISPORT);
	private static String timeout = PropertiesUtil.getProperties(CacheName.REDISTIMEOUT);
	private static String password = PropertiesUtil.getProperties(CacheName.REDISPASSWORD);
	
    
	/**
	 * 获取redis操作实例（不必加锁）
	 * 
	 * @return jedis
	 */
	public static Jedis getJedis() {
		try {
			if (pool == null) {
				
				password = (StringUtils.isBlank(password)) ? null : password;
				
				pool = new JedisPool(new JedisPoolConfig(), host, Integer.valueOf(port), Integer.valueOf(timeout), password);
				jedis = pool.getResource();
			} else
				return jedis;
		} catch (Exception e) {
			log.error("JedisDao::getJedis:" + e.getMessage());
			jedis = null;
		}
		return jedis;
	}
	
	/**
	 * 添加key value
	 * @param key
	 * @param value
	 * @return
	 */
	public static String setKV(String key, String value){
		Jedis jedis = getJedis();
		return jedis.set(key, value);
	}
	
	/**
	 * 添加key value 并设置失效时间
	 * @param key
	 * @param value
	 * @param seconds -单位秒
	 */
	public static void setKVT(String key, String value, Integer seconds){
		Jedis jedis = getJedis();
		jedis.set(key, value);
		jedis.expire(key, seconds);
	}
	
	public static void setKO(String key, Object obj){
		Jedis jedis = getJedis();
		
		byte[] bKey = key.getBytes();
		byte[] bObj = SerializeUtil.serialize(obj);
		
		jedis.set(bKey, bObj);
	}
	
	public static void setKOT(String key, Object obj, Integer seconds){
		Jedis jedis = getJedis();
		
		byte[] bKey = key.getBytes();
		byte[] bObj = SerializeUtil.serialize(obj);
		
		jedis.set(bKey, bObj);
		jedis.expire(bKey, seconds);
		
	}
	
	public static String getValue(String key){
		
		Jedis jedis = getJedis();
		
		return jedis.get(key);
		
	}
	
	public static Object getObject(String key){
		
		Jedis jedis = getJedis();
		
		 byte[] byteObj = jedis.get(key.getBytes());
		
		return SerializeUtil.unserialize(byteObj);
		
	}
	
	public static void main(String[] args) {
		setKV("test","test");
		System.out.println(getValue("test"));
	}
	
}
