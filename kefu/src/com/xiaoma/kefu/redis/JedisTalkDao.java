package com.xiaoma.kefu.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

public class JedisTalkDao {

	/**
	 * 获取当前通信点用户id
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static String getCurrentUserId(Integer type, String ccnId) {

		String key = JedisConstant.getCurrentUserKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();

		return jedis.get(key);
	}
	
	/**
	 * 获取当前连接点列表
	 * @param type
	 * @param userId
	 * @return
	 */
	public static List<String> getCcnList(Integer type) {

		String key = JedisConstant.getCcnListKey(type);

		Jedis jedis = JedisDao.getJedis();
		Set<String> set = jedis.zrange(key, 0, -1);

		return new ArrayList<String>(set);
	}
	
	public static List<String> getUserCcnList(String userId) {

		String key = JedisConstant.getUserCcnListKey(userId);

		Jedis jedis = JedisDao.getJedis();
		Set<String> set = jedis.zrange(key, 0, -1);

		return new ArrayList<String>(set);
	}

	/**
	 * 获取当前用户id列表
	 * 
	 * @param type
	 * @return
	 */
	public static List<String> getCurrentUserList(Integer type) {

		String key = JedisConstant.getCurrentUserListKey(type);

		Jedis jedis = JedisDao.getJedis();
		Set<String> set = jedis.zrange(key, 0, -1);
		
		return new ArrayList<String>(set);
	}

	/**
	 * 获取对话关系ccnId
	 * 
	 * @param type
	 * @return
	 */
	public static String getTalkerCcnId(Integer type, String ccnId) {

		String key = JedisConstant.getTalkerKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();

		return jedis.get(key);
	}

	/**
	 * 获取接待数量的key
	 * 
	 * @param userId
	 * @return
	 */
	public static Integer getCurrentReceiveCount(Integer userId) {

		String key = JedisConstant.getCurrentReceiveCountKey(userId);

		Jedis jedis = JedisDao.getJedis();
		String value = jedis.get(key);

		Integer count = (StringUtils.isBlank(value)) ? 0 : Integer
				.valueOf(value);

		return count;
	}
	public static Boolean incrCurrentReceiveCount(Integer userId) {

		String key = JedisConstant.getCurrentReceiveCountKey(userId);

		Jedis jedis = JedisDao.getJedis();
		Long replay = jedis.incr(key);


		return (replay > 0);
	}
	
	public static Boolean decrCurrentReceiveCount(Integer userId) {

		String key = JedisConstant.getCurrentReceiveCountKey(userId);

		Jedis jedis = JedisDao.getJedis();
		Long replay = jedis.decr(key);

		return (replay > 0);
	}

	/**
	 * 获取最大接待数量的key
	 * 
	 * @param userId
	 * @return
	 */
	public static Integer getMaxReceiveCount(Integer userId) {

		String key = JedisConstant.getMaxReceiveCountKey(userId);

		Jedis jedis = JedisDao.getJedis();
		String value = jedis.get(key);

		Integer count = (StringUtils.isBlank(value)) ? 0 : Integer
				.valueOf(value);

		return count;
	}
	
	public static Boolean setMaxReceiveCount(Integer userId,Integer count) {

		String key = JedisConstant.getMaxReceiveCountKey(userId);

		Jedis jedis = JedisDao.getJedis();

		String replay = jedis.set(key, count.toString());

		return StringUtils.isNotBlank(replay);
	}
	

	/**
	 * 获取接待列表的key
	 * 
	 * @param userId
	 * @return
	 */
	public static List<String> getReceiveList(Integer userId) {

		String key = JedisConstant.getReceiveListKey(userId);
		Jedis jedis = JedisDao.getJedis();
		
		Set<String> set = jedis.zrange(key, 0, -1);

		return new ArrayList<String>(set);
	}
	
	public static Boolean addReceiveList(Integer userId,String cId) {

		String key = JedisConstant.getReceiveListKey(userId);
		Jedis jedis = JedisDao.getJedis();
		
		Long id = jedis.zadd(key, System.currentTimeMillis(), cId);

		return (id > 0);
	}

	/**
	 * 获取对话列表的key
	 * 
	 * @param userId
	 * @return
	 */
	public static List<String> getDialogueList(String customerCcnId,String userCcnId) {
		String key = JedisConstant.getDialogueListKey(customerCcnId, userCcnId);
		Jedis jedis = JedisDao.getJedis();

		return jedis.lrange(key, 0, -1);
	}
	
	//######################补充服务
	
	/**
	 * 添加用户到当前列表
	 * @param type
	 * @param userId
	 * @return
	 */
	public static Boolean addCurrentUserList(Integer type,String userId) {

		String key = JedisConstant.getCurrentUserListKey(type);

		Jedis jedis = JedisDao.getJedis();
		Long id = jedis.zadd(key, System.currentTimeMillis(), userId);

		return (id > 0);
	}
	
	public static Boolean delCurrentUserList(Integer type, String userId) {

		String key = JedisConstant.getCurrentUserListKey(type);

		Jedis jedis = JedisDao.getJedis();
		Long replay = jedis.zrem(key, userId);

		return (replay > 0);

	}
	
	
	/**
	 * 添加连接点到当前列表
	 * @param type
	 * @param userId
	 * @return
	 */
	public static Boolean addCcnList(Integer type,String ccnId) {

		String key = JedisConstant.getCcnListKey(type);

		Jedis jedis = JedisDao.getJedis();
		Long id = jedis.zadd(key, System.currentTimeMillis(), ccnId);

		return (id > 0);
	}
	
	public static Boolean delCcnList(Integer type,String ccnId) {

		String key = JedisConstant.getCcnListKey(type);

		Jedis jedis = JedisDao.getJedis();
		Long replay = jedis.zrem(key, ccnId);

		return (replay > 0);
	}
	
	public static Boolean addUserCcnList(String userId,String ccnId) {

		String key = JedisConstant.getUserCcnListKey(userId);

		Jedis jedis = JedisDao.getJedis();
		Long id = jedis.zadd(key, System.currentTimeMillis(), ccnId);

		return (id > 0);
	}
	public static Boolean delUserCcnList(String userId,String ccnId) {

		String key = JedisConstant.getUserCcnListKey(userId);

		Jedis jedis = JedisDao.getJedis();
		Long replay = jedis.zrem(key, ccnId);

		return (replay > 0);
	}
	
	public static Integer countUserCcnList(String userId) {

		String key = JedisConstant.getUserCcnListKey(userId);

		Jedis jedis = JedisDao.getJedis();
		Long size = jedis.zcount(key, Long.MIN_VALUE, Long.MAX_VALUE);

		return size.intValue();
	}
	
	
	//添加通信点
	/**
	 * 获取当前通信点用户id
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static Boolean addCurrentUser(Integer type, String ccnId,String userId) {

		String key = JedisConstant.getCurrentUserKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();
		
		String replay = jedis.set(key, userId);

		return StringUtils.isNotBlank(replay);
	}
	
	/**
	 * 删除通信点
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static Boolean delCurrentUser(Integer type, String ccnId) {

		String key = JedisConstant.getCurrentUserKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();
		
		Long replay = jedis.del(key);

		return (replay > 0);
	}
	
	/**
	 * 分配任务
	 * @param userIds
	 * @return
	 */
	public static Integer allocateUserId(List<String> userIds){
		Integer result = null;
		Integer maxLastCount = null;
		for(String userId : userIds){
			Integer id = Integer.valueOf(userId);
			Integer lastCount = getMaxReceiveCount(id)-getCurrentReceiveCount(id);
			if(maxLastCount == null || maxLastCount < lastCount){
				maxLastCount = lastCount;
				result = id;
			}
		}
		
		return result;
		
	}

}
