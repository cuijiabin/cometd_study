package com.xiaoma.kefu.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.xiaoma.kefu.util.JsonUtil;

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

	// redisConf
	private static String host = SystemConfiguration.getInstance().getHost();
	private static Integer port = SystemConfiguration.getInstance().getPort();
	private static Integer timeout = SystemConfiguration.getInstance()
			.getTimeout();
	private static String password = SystemConfiguration.getInstance()
			.getPassword();

	public static JedisPool getJedisPool() {
		if (pool == null) {
			password = (StringUtils.isBlank(password)) ? null : password;

			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxActive(500);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(5);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWait(1000 * 100);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);

			pool = new JedisPool(config, host, port, timeout, password);
		}

		return pool;
	}

	/**
	 * 获取redis操作实例（不必加锁）
	 * 
	 * @return jedis
	 */
	public static Jedis getJedis() {
		try {
			pool = getJedisPool();

			jedis = pool.getResource();
			return jedis;
		} catch (Exception e) {
			log.error("JedisDao::getJedis:" + e.getMessage());
			jedis = null;
		}
		return jedis;
	}

	/**
	 * 添加key value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String setKV(String key, String value) {
		try {
			jedis = getJedis();

			String result = jedis.set(key, value);

			return result;
		} catch (Exception e) {
			log.error("JedisDao::setKV:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	/**
	 * 添加key value 并设置失效时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            -单位秒
	 */
	public static void setKVT(String key, String value, Integer seconds) {
		try {
			jedis = getJedis();
			jedis.set(key, value);
			jedis.expire(key, seconds);

		} catch (Exception e) {
			log.error("JedisDao::setKVT:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static void setKO(String key, Object obj) {
		try {
			jedis = getJedis();

			String value = JsonUtil.toJson(obj);
			jedis.set(key, value);

		} catch (Exception e) {
			log.error("JedisDao::setKO:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static void setKList(String key, List list) {
		try {
			jedis = getJedis();
			jedis.del(key);

			for (Object obj : list) {
				String value = JsonUtil.toJson(obj);
				jedis.rpush(key, value);
			}

		} catch (Exception e) {
			log.error("JedisDao::setKList:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static <T> List<T> getKList(String key, Class<T> clazz) {
		try {
			jedis = getJedis();

			List<String> results = jedis.lrange(key, 0, -1);

			if (CollectionUtils.isEmpty(results)) {
				return null;
			}
			List<T> list = new ArrayList<T>();
			for (String str : results) {
				T obj = (T) JsonUtil.getObjFromJson(str, clazz);
				list.add(obj);
			}

			return list;

		} catch (Exception e) {
			log.error("JedisDao::getKList:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	public static void setKOT(String key, Object obj, int seconds) {
		try {
			jedis = getJedis();

			String value = JsonUtil.toJson(obj);

			jedis.set(key, value);
			jedis.expire(key, seconds);

		} catch (Exception e) {
			log.error("JedisDao::setKOT:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}

	}

	public static String getValue(String key) {
		try {
			jedis = getJedis();
			String value = jedis.get(key);
			return value;
		} catch (Exception e) {
			log.error("JedisDao::getValue:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;

	}

	public static <T> Object getObject(String key, Class<T> clazz) {
		try {
			jedis = getJedis();

			String value = jedis.get(key);

			if (StringUtils.isEmpty(value)) {
				return null;
			}

			Object obj = JsonUtil.getObjFromJson(value, clazz);

			return obj;

		} catch (Exception e) {
			log.error("JedisDao::getObject:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;

	}

	public static void remove(String key) {
		try {
			jedis = getJedis();
			jedis.del(key);

		} catch (Exception e) {
			log.error("JedisDao::remove:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}

	}

	/***
	 * 删除全部缓存，慎用。
	 */
	public static void flushAll() {
		try {
			jedis = getJedis();
			jedis.flushAll();

		} catch (Exception e) {
			log.error("JedisDao::flushAll:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}

	}
	
	/**
	 * 获取全部zset元素
	 * @param key
	 * @return
	 */
	public static Set<String> zrangeAll(String key){
		
		try {
			jedis = getJedis();
			return jedis.zrange(key, 0, -1);
		} catch (Exception e) {
			log.error("JedisDao::zrangeAll:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}
	
	public static Boolean zaddTimestamp(String key,String member){
		
		try {
			jedis = getJedis();
			Long id = jedis.zadd(key, System.currentTimeMillis(), member);
			return (id >= 0);
		} catch (Exception e) {
			log.error("JedisDao::zaddTimestamp:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return true;
	}
	
	public static Boolean zrem(String key,String member){
		try {
			jedis = getJedis();
			Long id = jedis.zrem(key, member);
			return (id >= 0);
		} catch (Exception e) {
			log.error("JedisDao::zaddTimestamp:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return true;
	}
	
	public static Integer zcard(String key){
		try {
			jedis = getJedis();
			Long size = jedis.zcard(key);
			return size.intValue();
		} catch (Exception e) {
			log.error("JedisDao::zaddTimestamp:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}
	
	
	public static Long zscore(String key, String member){
		try {
			jedis = getJedis();
			Double socre = jedis.zscore(key, member);
			if (socre == null) {
				return null;
			}
			return socre.longValue();
		} catch (Exception e) {
			log.error("JedisDao::zaddTimestamp:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}
	
	public static List<String> lrangeAll(String key){
		try {
			jedis = getJedis();
			return jedis.lrange(key, 0, -1);
		} catch (Exception e) {
			log.error("JedisDao::zaddTimestamp:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}
	
	public static Boolean rpush(String key,String member){
		try {
			jedis = getJedis();
			Long id = jedis.rpush(key, member);
			return (id >= 0);
		} catch (Exception e) {
			log.error("JedisDao::zaddTimestamp:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return true;
	}
	

	public static void main(String[] args) {
		setKV("test", "test");
		System.out.println(getValue("test"));
	}

}
