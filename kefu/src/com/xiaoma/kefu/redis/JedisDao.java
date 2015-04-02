package com.xiaoma.kefu.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    
	/**
	 * 获取redis操作实例（不必加锁）
	 * 
	 * @return jedis
	 */
	public static Jedis getJedis() {
		try {
			if (pool == null) {
				pool = new JedisPool(new JedisPoolConfig(), "192.168.1.210");
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
	
	public String getValue(String key){
		
		Jedis jedis = getJedis();
		
		return jedis.get(key);
		
	}
	
	public Object getObject(String key){
		
		Jedis jedis = getJedis();
		
		 byte[] byteObj = jedis.get(key.getBytes());
		
		return SerializeUtil.unserialize(byteObj);
		
	}
	
}
