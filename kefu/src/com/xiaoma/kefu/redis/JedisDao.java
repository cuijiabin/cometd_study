package com.xiaoma.kefu.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 
 * 获取redis操作实例（不必加锁）
 * 
 * @author cuijiabin
 * 
 */
public class JedisDao {
	
	private static Log log = LogFactory.getLog(JedisDao.class);
	private static Jedis jedis;
	private static JedisPool pool;

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
}
