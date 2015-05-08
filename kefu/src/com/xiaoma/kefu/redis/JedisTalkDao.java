package com.xiaoma.kefu.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.comet4j.DialogueInfo;
import com.xiaoma.kefu.util.JsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTalkDao {

	private static Logger logger = Logger.getLogger(JedisTalkDao.class);

	private static JedisPool pool = JedisDao.getJedisPool();

	//##########用户通信点#########################
	/**
	 * 通信点用户操作 get
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static String getCnnUserId(Integer type, String ccnId) {
		
		String key = JedisConstant.genCcnKey(type, ccnId);
		String value = JedisDao.getValue(key);
		return value;
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
		JedisDao.setKV(key,userId);
		return true;
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
		JedisDao.remove(key);
		
		return true;
	}

	//##########用户连接点列表#########################
	/**
	 * 用户所有的连接点列表操作 get
	 * 
	 * @param userId
	 * @return
	 */
	public static List<String> getUserCcnList(String userId) {
		
		String key = JedisConstant.genUserCcnListKey(userId);
		Set<String> set = JedisDao.zrangeAll(key);
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
		return JedisDao.zaddTimestamp(key, ccnId);
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
		return JedisDao.zrem(key, ccnId);
	}

	//##########当前通信点列表#########################
	/**
	 * 当前通信点列表操作 get
	 * 
	 * @param type
	 * @return
	 */
	public static List<String> getCcnList(Integer type) {

		String key = JedisConstant.genCcnListKey(type);
		Set<String> set = JedisDao.zrangeAll(key);
		return new ArrayList<String>(set);
	}

	public static Integer getCcnListSize(Integer type) {
		
		String key = JedisConstant.genCcnListKey(type);
		return JedisDao.zcard(key);
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
		return JedisDao.zaddTimestamp(key, ccnId);
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
		return JedisDao.zrem(key, ccnId);
	}

	//##########用户最大接待数量#########################
	
	public static Integer getMaxReceiveCount(String userId) {
		
		String key = JedisConstant.genMaxReceiveCountKey(userId);
		String value = JedisDao.getValue(key);
		Integer count = (StringUtils.isBlank(value)) ? 0 : Integer.valueOf(value);
		return count;
	}

	public static Boolean setMaxReceiveCount(String userId, Integer count) {
		
		String key = JedisConstant.genMaxReceiveCountKey(userId);
		JedisDao.setKV(key, count.toString());
		
		return true;
	}


	//##########用户当前接待数量#########################

	public static Integer getCurrentReceiveCount(String ccnId) {

		String key = JedisConstant.genCcnReceiveListKey(ccnId);
		return JedisDao.zcard(key);
		
	}

	//##########用户当前接待列表#########################

	public static List<String> getCcnReceiveList(String ccnId) {
		
		String key = JedisConstant.genCcnReceiveListKey(ccnId);
		Set<String> set = JedisDao.zrangeAll(key);
		return new ArrayList<String>(set);
	}

	public static Long getCcnReceiveScore(String ccnId, String member) {
		
		String key = JedisConstant.genCcnReceiveListKey(ccnId);
		return JedisDao.zscore(key, member);
	}

	/**
	 * 为通信点 添加接待通信点
	 * 
	 * @param ccnId
	 * @param opeCcnId
	 * @return
	 */
	public static Boolean addCcnReceiveList(String ccnId, String opeCcnId) {
		
		String key = JedisConstant.genCcnReceiveListKey(ccnId);
		return JedisDao.zaddTimestamp(key, opeCcnId);
	}

	public static Boolean remCcnReceiveList(String ccnId, String opeCcnId) {

		String key = JedisConstant.genCcnReceiveListKey(ccnId);
		return JedisDao.zrem(key, opeCcnId);
	}

	//##########客户被动接待#########################
	
	public static String getCcnPassiveId(String ccnId) {
		
		String key = JedisConstant.genCcnPassiveKey(ccnId);
		return JedisDao.getValue(key);
	}

	/**
	 * 设置通信点被谁接待
	 * 
	 * @param ccnId
	 * @param opeCcnId
	 * @return
	 */
	public static Boolean setCcnPassiveId(String ccnId, String opeCcnId) {
		
		String key = JedisConstant.genCcnPassiveKey(ccnId);
		JedisDao.setKV(key,opeCcnId);
		return true;
	}

	public static Boolean delCcnPassiveId(String ccnId) {
		
		String key = JedisConstant.genCcnPassiveKey(ccnId);
		JedisDao.remove(key);
		return true;
	}

	//##########对话内容#########################
	/**
	 * 对话内容列表操作 get
	 * 
	 * @param userId
	 * @return
	 */
	public static List<String> getDialogueList(String uccnId, String cccnId) {
		
		String key = JedisConstant.getDialogueListKey(uccnId, cccnId);
		List<String> list = JedisDao.lrangeAll(key);
		return list;
	}

	public static Boolean addDialogueList(String uccnId, String cccnId,
			String message) {
		
		String key = JedisConstant.getDialogueListKey(uccnId, cccnId);
		return JedisDao.rpush(key, message);
	}

	public static Boolean delDialogueList(String uccnId, String cccnId) {
		
		String key = JedisConstant.getDialogueListKey(uccnId, cccnId);
		JedisDao.remove(key);
		return true;
	}

	//##########对话保存#########################
	/**
	 * 队尾弹出需要保存的消息
	 */
	public static String rpopSaveDialogue() {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			String value = jedis.rpop(JedisConstant.SAVE_DIALOGUE_LIST);
			return value;
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	/**
	 * 队头插入需要保存的消息
	 */
	public static Boolean lpushSaveDialogue(String value) {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			Long id = jedis.lpush(JedisConstant.SAVE_DIALOGUE_LIST, value);
			logger.info("redis lpush key:" + JedisConstant.SAVE_DIALOGUE_LIST+ " value: " + value);
			return (id >= 0);
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	//##########离线客户列表#########################
	public static Set<String> getOffLineUserSet() {
		
		return JedisDao.zrangeAll(JedisConstant.OFF_LINE_USER_SET);
	}

	public static Boolean addOffLineUserSet(String userId) {
		
		return JedisDao.zaddTimestamp(JedisConstant.OFF_LINE_USER_SET, userId);
	}

	public static Boolean remOffLineUserSet(String userId) {
		
		return JedisDao.zrem(JedisConstant.OFF_LINE_USER_SET, userId);
	}

	public static Boolean isInOffLineUserSet(String userId) {
		
		Long score = JedisDao.zscore(JedisConstant.OFF_LINE_USER_SET, userId);
		return (score != null);
	}

	//##########客户等待列表#########################
	/**
	 * 获取客户等待列表
	 * 
	 * @return
	 */
	public static Set<String> getCustomerWaitSet() {
		
		return JedisDao.zrangeAll(JedisConstant.CUSTOMER_WAIT_SET);
	}

	/**
	 * 添加连接点至客户等待列表
	 * 
	 * @param ccnId
	 * @return
	 */
	public static Boolean addCustomerWaitSet(String ccnId) {
		
		return JedisDao.zaddTimestamp(JedisConstant.CUSTOMER_WAIT_SET, ccnId);
	}

	public static Boolean remCustomerWaitSet(String ccnId) {
		
		return  JedisDao.zrem(JedisConstant.CUSTOMER_WAIT_SET, ccnId);
	}

	public static Integer sizeCustomerWaitSet() {
		
		return JedisDao.zcard(JedisConstant.CUSTOMER_WAIT_SET);
	}

	/**
	 * 获取等待列表中的最后一个（但是不会删除掉）
	 * @return
	 */
	public static String popCustomerWaitSet() {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			Integer start = sizeCustomerWaitSet() - 1;
			Set<String> set = jedis.zrange(JedisConstant.CUSTOMER_WAIT_SET,start, -1);
			if (CollectionUtils.isEmpty(set)) {
				return null;
			}
			Iterator<String> it = set.iterator();
			if (!it.hasNext()) {
				return null;
			}
			String member = it.next();
			return member;
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	public static void delCustomerWaitSet(String member) {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			JedisDao.zrem(JedisConstant.CUSTOMER_WAIT_SET, member);
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static Long getCustomerWaitScore(String member) {
		
		return JedisDao.zscore(JedisConstant.CUSTOMER_WAIT_SET, member);
	}

	/**
	 * 获取客户等待时间（单位：秒）
	 * @param member
	 * @return
	 */
	public static Integer getCustomerWaitTime(String member) {
		
			Long diff = JedisDao.zscore(JedisConstant.CUSTOMER_WAIT_SET, member);
			if (diff == null) {
				return null;
			}
			diff = diff / 1000;
			return diff.intValue();
	}

	//##########对话信息#########################
	
	public static Boolean setDialogueInfo(String customerId, String userCcnId,
			DialogueInfo dInfo) {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			String keyStr = JedisConstant.DIALOGUE_INFO + customerId;
			userCcnId = (StringUtils.isBlank(userCcnId)) ? "-1" : userCcnId;
			String value = JsonUtil.toJson(dInfo);
			Long result = jedis.hset(keyStr, userCcnId, value);
			jedis.expire(keyStr, 12*60*60);
			if (result == null) {
				return false;
			}
			return (result >= 0);
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	public static Boolean delDialogueInfo(String customerId, String userCcnId) {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			String key = JedisConstant.DIALOGUE_INFO + customerId;
			userCcnId = (StringUtils.isBlank(userCcnId)) ? "-1" : userCcnId;
			jedis.hdel(key, userCcnId);
			Long size = jedis.hlen(key);
			if (size <= 0) {
				JedisDao.remove(key);
			}
			return true;
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}
	
	public static void setDialogueLasts(String customerId,String userCcnId){
		
		String key = JedisConstant.getLastsKey(customerId, userCcnId);
		JedisDao.setKVT(key, "1", 12*60*60);
	}
	
	public static String getDialogueLasts(String customerId,String userCcnId){
		
		String key = JedisConstant.getLastsKey(customerId, userCcnId);
		return JedisDao.getValue(key);
	}

	/**
	 * 获取对话信息
	 * @param customerId
	 * @param userCcnId
	 * @return
	 */
	public static DialogueInfo getDialogueInfo(String customerId,String userCcnId) {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			String key = JedisConstant.DIALOGUE_INFO + customerId;
			userCcnId = (StringUtils.isBlank(userCcnId)) ? "-1" : userCcnId;
			Boolean isExist = jedis.hexists(key, userCcnId);
			String value = jedis.hget(key, userCcnId);
			if (!isExist || StringUtils.isEmpty(value)) {
				DialogueInfo dInfo = new DialogueInfo();
				dInfo.setCustomerId(Long.valueOf(customerId));
				dInfo.setUserCcnId(userCcnId);
				
				jedis.hset(key, userCcnId, JsonUtil.toJson(dInfo));
				jedis.expire(key, 12*60*60);
				return dInfo;
			}
			DialogueInfo dInfo = (DialogueInfo) JsonUtil.getObjFromJson(value,DialogueInfo.class);
			return dInfo;
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	//##########其他方法#########################

	/**
	 * 分配任务
	 * 
	 * @param userIds
	 * @return
	 */
	public static String allocateCcnId() {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			List<String> userIds = getSwitchList();
			if (CollectionUtils.isEmpty(userIds)) {
				return null;
			}
			Integer max = 0;
			String allocateUserId = null;
			for (String userId : userIds) {
				Integer result = getMaxReceiveCount(userId)
						- getReceiveCount(userId);

				if (result > max) {
					max = result;
					allocateUserId = userId;
				}
			}
			return getUserCcnList(allocateUserId).get(0);
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;

	}
	
	/**
	 * 分配任务
	 * 
	 * @param userIds
	 * @return
	 */
	public static String allocateCcnIdByStyleId(Integer styleId){
		
		Jedis jedis = JedisDao.getJedis();
		try {
			List<Integer> userIds = CacheMan.getOnlineUserIdsByStyleId(styleId);
			if (CollectionUtils.isEmpty(userIds)) {
				return null;
			}
			Integer max = 0;
			String allocateUserId = null;
			for (Integer id : userIds) {
				String userId = id.toString();
				Integer result = getMaxReceiveCount(userId)
						- getReceiveCount(userId);

				if (result > max) {
					max = result;
					allocateUserId = userId;
				}
			}
			return getUserCcnList(allocateUserId).get(0);
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;

	}
	
	/**
	 * 分配任务
	 * 
	 * @param userIds
	 * @return
	 */
	public static String allocateCcnIdByStyleId(List<Integer> userIds,Integer styleId){
		
		Jedis jedis = JedisDao.getJedis();
		try {
			if (CollectionUtils.isEmpty(userIds)) {
				return null;
			}
			Integer max = 0;
			String allocateUserId = null;
			for (Integer id : userIds) {
				String userId = id.toString();
				Integer result = getMaxReceiveCount(userId)
						- getReceiveCount(userId);

				if (result > max) {
					max = result;
					allocateUserId = userId;
				}
			}
			return getUserCcnList(allocateUserId).get(0);
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;

	}
	
	/**
	 * 判断用户接待数量是否已满
	 * @param userId
	 * @return
	 */
	public static Boolean judgeFull(String userId){
		
		Integer result = getMaxReceiveCount(userId)- getReceiveCount(userId);
		
		return (result == null || result <=0 );
		
	}
	
	/**
	 * 根据用户id获取剩余接待数量
	 * @param userId
	 * @return
	 */
	public static Integer getLastReceiveCount(String userId){
		
		Integer result = getMaxReceiveCount(userId)- getReceiveCount(userId);
		
		result = (result == null) ? 0 : result;
		
		return result;
		
	}

	/**
	 * 获取所有在线用户（对话框未关闭,去重）
	 * 
	 * @return
	 */
	public static List<String> getOnlineUserIds() {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			String keyPattern = JedisConstant.genCcnKey(JedisConstant.USER_TYPE, "*");
			Set<String> keys = jedis.keys(keyPattern);
			if (null == keys || keys.size() < 1) {
				return null;
			}
			String[] keyArray = new String[keys.size()];
			keyArray = (String[]) keys.toArray(keyArray);
			// 去重
			Set<String> set = new HashSet<String>();
			set.addAll(jedis.mget(keyArray));
			return new ArrayList<String>(set);
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;

	}

	public static List<String> getSwitchList() {
		
		List<String> result = getOnlineUserIds();
		if (CollectionUtils.isEmpty(result)) {
			result = new ArrayList<String>();
		}
		result.removeAll(getOffLineUserSet());
		return result;
	}

	public static Integer getReceiveCount(String userId) {
		
		Jedis jedis = JedisDao.getJedis();
		try {
			List<String> ccnIds = getUserCcnList(userId);
			if (CollectionUtils.isEmpty(ccnIds)) {
				return 0;
			}
			Integer result = 0;
			for (String ccnId : ccnIds) {
				Integer part = getCurrentReceiveCount(ccnId);
				result += part;
			}
			return result;
		} catch (Exception e) {
			logger.error("JedisTalkDao::getCnnUserId:" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	//测试使用
	public static void main(String[] args) {
		
	}

}
