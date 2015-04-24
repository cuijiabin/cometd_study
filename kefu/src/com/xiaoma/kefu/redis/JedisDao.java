package com.xiaoma.kefu.redis;

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
		try {
			jedis = getJedis();
			return jedis.set(key, value);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 添加key value 并设置失效时间
	 * @param key
	 * @param value
	 * @param seconds -单位秒
	 */
	public static void setKVT(String key, String value, Integer seconds){
		try {
			jedis = getJedis();
			jedis.set(key, value);
			jedis.expire(key, seconds);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void setKO(String key, Object obj){
		try {
			jedis = getJedis();
			
			byte[] bKey = key.getBytes();
			byte[] bObj = SerializeUtil.serialize(obj);
			
			jedis.set(bKey, bObj);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void setKOT(String key, Object obj, int seconds){
		try {
			jedis = getJedis();
			
			byte[] bKey = key.getBytes();
			byte[] bObj = SerializeUtil.serialize(obj);
			
			jedis.set(bKey, bObj);
			jedis.expire(bKey, seconds);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	public static String getValue(String key){
		try {
			jedis = getJedis();
			return jedis.get(key);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
	}
	
	public static Object getObject(String key){
		try {
			jedis = getJedis();
			
			byte[] byteObj = jedis.get(key.getBytes());
			
			return SerializeUtil.unserialize(byteObj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
	}
	
	public static void remove(String key){
		try {
			jedis = getJedis();
			jedis.del(key);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	/***
	 * 删除全部缓存，慎用。
	 */
	public static void flushAll(){
		try {
			jedis = getJedis();
			jedis.flushAll();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void main(String[] args) {
		setKV("test","test");
		System.out.println(getValue("test"));
	}
	
}
