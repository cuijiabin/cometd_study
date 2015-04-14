package com.xiaoma.kefu.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class JedisTalkDao {
	
	private static Logger logger = Logger.getLogger(JedisTalkDao.class);

	// #############################
	/**
	 * 通信点用户操作 get
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static String getCnnUserId(Integer type, String ccnId) {

		String key = JedisConstant.genCcnKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();

		return jedis.get(key);
	}

	/**
	 * 通信点用户操作 set
	 * 
	 * @param type
	 * @param ccnId
	 * @param userId
	 * @return
	 */
	public static Boolean setCnnUserId(Integer type, String ccnId, String userId) {

		String key = JedisConstant.genCcnKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();

		String replay = jedis.set(key, userId);
		
		logger.info("redis set key:" + key +" value: "+ userId);
		
		return StringUtils.isNotBlank(replay);
	}

	/**
	 * 通信点用户操作 del
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static Boolean delCnnUserId(Integer type, String ccnId) {

		String key = JedisConstant.genCcnKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.del(key);
		
		logger.info("redis del key:" + key +" value: "+ ccnId);

		return (replay > 0);
	}

	// #############################

	/**
	 * 用户所有的连接点列表操作 get
	 * 
	 * @param userId
	 * @return
	 */
	public static List<String> getUserCcnList(String userId) {

		String key = JedisConstant.genUserCcnListKey(userId);

		Jedis jedis = JedisDao.getJedis();

		Set<String> set = jedis.zrange(key, 0, -1);

		return new ArrayList<String>(set);
	}

	/**
	 * 用户所有的连接点列表操作 add
	 * 
	 * @param userId
	 * @param ccnId
	 * @return
	 */
	public static Boolean addUserCcnList(String userId, String ccnId) {

		String key = JedisConstant.genUserCcnListKey(userId);

		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.zadd(key, System.currentTimeMillis(), ccnId);
		
		logger.info("redis zadd key:" + key +" value: "+ ccnId);

		return (id > 0);
	}

	/**
	 * 用户所有的连接点列表操作 rem
	 * 
	 * @param userId
	 * @param ccnId
	 * @return
	 */
	public static Boolean remUserCcnList(String userId, String ccnId) {

		String key = JedisConstant.genUserCcnListKey(userId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.zrem(key, ccnId);
		
		logger.info("redis zrem key:" + key +" value: "+ ccnId);

		return (replay > 0);
	}

	// #############################

	/**
	 * 当前通信点列表操作 get
	 * 
	 * @param type
	 * @return
	 */
	public static List<String> getCcnList(Integer type) {

		String key = JedisConstant.genCcnListKey(type);

		Jedis jedis = JedisDao.getJedis();

		Set<String> set = jedis.zrange(key, 0, -1);

		return new ArrayList<String>(set);
	}

	/**
	 * 当前通信点列表操作 add
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static Boolean addCcnList(Integer type, String ccnId) {

		String key = JedisConstant.genCcnListKey(type);

		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.zadd(key, System.currentTimeMillis(), ccnId);
		
		logger.info("redis zadd key:" + key +" value: "+ ccnId);
		
		return (id > 0);
	}

	/**
	 * 当前通信点列表操作 rem
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static Boolean remCcnList(Integer type, String ccnId) {

		String key = JedisConstant.genCcnListKey(type);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.zrem(key, ccnId);
		
		logger.info("redis zrem key:" + key +" value: "+ ccnId);

		return (replay > 0);
	}

	// #################################

	public static Integer getMaxReceiveCount(String userId) {

		String key = JedisConstant.genMaxReceiveCountKey(userId);

		Jedis jedis = JedisDao.getJedis();

		String value = jedis.get(key);

		Integer count = (StringUtils.isBlank(value)) ? 0 : Integer
				.valueOf(value);

		return count;
	}

	public static Boolean setMaxReceiveCount(String userId, Integer count) {

		String key = JedisConstant.genMaxReceiveCountKey(userId);

		Jedis jedis = JedisDao.getJedis();

		String replay = jedis.set(key, count.toString());

		logger.info("redis set key:" + key +" value: "+ count);
		
		return StringUtils.isNotBlank(replay);
	}

	public static Boolean incrMaxReceiveCount(String userId) {

		String key = JedisConstant.genMaxReceiveCountKey(userId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.incr(key);
		
		logger.info("redis incr key:" + key);

		return (replay > 0);
	}

	public static Boolean decrMaxReceiveCount(String userId) {

		String key = JedisConstant.genMaxReceiveCountKey(userId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.decr(key);
		
		logger.info("redis decr key:" + key);

		return (replay > 0);
	}

	// #################################

	public static Integer getCurrentReceiveCount(String ccnId) {

		String key = JedisConstant.genCurrentReceiveCountKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		String value = jedis.get(key);

		Integer count = (StringUtils.isBlank(value)) ? 0 : Integer
				.valueOf(value);

		return count;
	}

	public static Boolean setCurrentReceiveCount(String ccnId, Integer count) {

		String key = JedisConstant.genCurrentReceiveCountKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		String replay = jedis.set(key, count.toString());
		
		logger.info("redis set key:" + key +" value: "+ count);

		return StringUtils.isNotBlank(replay);
	}

	public static Boolean incrCurrentReceiveCount(String ccnId) {

		String key = JedisConstant.genCurrentReceiveCountKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.incr(key);
		
		logger.info("redis incr key:" + key);

		return (replay > 0);
	}

	public static Boolean decrCurrentReceiveCount(String ccnId) {

		String key = JedisConstant.genCurrentReceiveCountKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.decr(key);
		
		logger.info("redis decr key:" + key);

		return (replay > 0);
	}

	// #################################

	public static List<String> getCcnReceiveList(String ccnId) {

		String key = JedisConstant.genCcnReceiveListKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		Set<String> set = jedis.zrange(key, 0, -1);

		return new ArrayList<String>(set);
	}

	public static Boolean addCcnReceiveList(String ccnId, String opeCcnId) {

		String key = JedisConstant.genCcnReceiveListKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.zadd(key, System.currentTimeMillis(), opeCcnId);
		
		logger.info("redis zadd key:" + key +" value: "+ opeCcnId);

		return (id > 0);
	}

	public static Boolean remCcnReceiveList(String ccnId, String opeCcnId) {

		String key = JedisConstant.genCcnReceiveListKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.zrem(key, opeCcnId);
		
		logger.info("redis zrem key:" + key +" value: "+ opeCcnId);

		return (replay > 0);
	}

	// #################################
	public static String getCcnPassiveId(String ccnId) {

		String key = JedisConstant.genCcnPassiveKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		return jedis.get(key);
	}

	public static Boolean setCcnPassiveId(String ccnId, String opeCcnId) {

		String key = JedisConstant.genCcnPassiveKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		String replay = jedis.set(key, opeCcnId);
		
		logger.info("redis set key:" + key +" value: "+ opeCcnId);

		return StringUtils.isNotBlank(replay);
	}

	public static Boolean delCcnPassiveId(String ccnId) {

		String key = JedisConstant.genCcnPassiveKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.del(key);
		
		logger.info("redis del key:" + key );

		return (replay > 0);
	}

	// #################################
	/**
	 * 获取对话关系ccnId
	 * 
	 * @param type
	 * @return
	 */
	public static String getTalkerCcnId(Integer type, String ccnId) {

		String key = JedisConstant.genTalkerRelationKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();

		return jedis.get(key);
	}

	public static Boolean setTalkerCcnId(Integer type, String ccnId,
			String opeCcnId) {

		String key = JedisConstant.genTalkerRelationKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();

		String replay = jedis.set(key, opeCcnId);
		
		logger.info("redis set key:" + key +" value: "+ opeCcnId);

		return StringUtils.isNotBlank(replay);
	}

	public static Boolean delTalkerCcnId(Integer type, String ccnId) {

		String key = JedisConstant.genTalkerRelationKey(type, ccnId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.del(key);
		
		logger.info("redis del key:" + key);

		return (replay > 0);
	}

	// ######################补充服务

	/**
	 * 分配任务
	 * 
	 * @param userIds
	 * @return
	 */
	public static String allocateCcnId() {

		List<String> cnnList = getCcnList(JedisConstant.USER_TYPE);
		Set<String> userList = new HashSet<String>();
		Map<String, Integer> userCountMap = new HashMap<String, Integer>();
		for (String ccnId : cnnList) {
			String userId = getCnnUserId(JedisConstant.USER_TYPE, ccnId);
			if (StringUtils.isNotBlank(userId)) {
				Integer num = getCurrentReceiveCount(userId);
				userList.add(userId);
				Integer count = userCountMap.get(userId);
				count = (count == null) ? num : (num + count);
				userCountMap.put(userId, count);
			}
		}

		String result = null;
		Integer maxLastCount = null;
		for (String userId : userList) {
			Integer lastCount = getMaxReceiveCount(userId)
					- userCountMap.get(userId);
			if (maxLastCount == null || maxLastCount < lastCount) {
				maxLastCount = lastCount;
				result = userId;
			}
		}
		if (result == null) {
			return result;
		}

		result = getUserCcnList(result).get(0);

		return result;

	}

}
