package com.xiaoma.kefu.redis;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.xiaoma.kefu.util.SerializeUtil;
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
	private static String host = SystemConfiguration.getInstance().getHost();
	private static Integer port = SystemConfiguration.getInstance().getPort();
	private static Integer timeout = SystemConfiguration.getInstance().getTimeout();
	private static String password = SystemConfiguration.getInstance().getPassword();
	
    
	/**
	 * 获取redis操作实例（不必加锁）
	 * 
	 * @return jedis
	 */
	public static Jedis getJedis() {
		try {
			if (pool == null) {
				
				password = (StringUtils.isBlank(password)) ? null : password;
				
				pool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
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
		jedis = getJedis();
		return jedis.set(key, value);
	}
	
	/**
	 * 添加key value 并设置失效时间
	 * @param key
	 * @param value
	 * @param seconds -单位秒
	 */
	public static void setKVT(String key, String value, Integer seconds){
		jedis = getJedis();
		jedis.set(key, value);
		jedis.expire(key, seconds);
	}
	
	public static void setKO(String key, Object obj){
		jedis = getJedis();
		
		byte[] bKey = key.getBytes();
		byte[] bObj = SerializeUtil.serialize(obj);
		
		jedis.set(bKey, bObj);
	}
	
	public static void setKOT(String key, Object obj, int seconds){
		jedis = getJedis();
		
		byte[] bKey = key.getBytes();
		byte[] bObj = SerializeUtil.serialize(obj);
		
		jedis.set(bKey, bObj);
		jedis.expire(bKey, seconds);
		
	}
	
	public static String getValue(String key){
		
		jedis = getJedis();
		return jedis.get(key);
		
	}
	
	public static Object getObject(String key){
		
		jedis = getJedis();
		
		byte[] byteObj = jedis.get(key.getBytes());
		
		return SerializeUtil.unserialize(byteObj);
		
	}
	
	public static void remove(String key){
		
		jedis = getJedis();
		jedis.del(key);
	}
	
	
	
	
	
	
	/***
	 * 删除全部缓存，慎用。
	 */
	public static void flushAll(){
		jedis = getJedis();
		jedis.flushAll();
		
		Set<String> keys = jedis.keys("chananme*");
		jedis.del((String[]) keys.toArray());
	}
	
	public static void main(String[] args) {
		setKV("test","test");
		System.out.println(getValue("test"));
	}
	
}
