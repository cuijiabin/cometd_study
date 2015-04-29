package com.xiaoma.kefu.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiaoma.kefu.comet4j.DialogueInfo;
import com.xiaoma.kefu.util.JsonUtil;

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
		
		logger.info("redis del key:" + key);

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

		return (id >= 0);
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
	
	public static Integer getCcnListSize(Integer type) {

		String key = JedisConstant.genCcnListKey(type);

		Jedis jedis = JedisDao.getJedis();

		Long size = jedis.zcard(key);

		return size.intValue();
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
		
		return (id >= 0);
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

		String key = JedisConstant.genCcnReceiveListKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		Long size = jedis.zcard(key);

		return size.intValue();
	}
	
	// #################################
    
	public static List<String> getCcnReceiveList(String ccnId) {

		String key = JedisConstant.genCcnReceiveListKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		Set<String> set = jedis.zrange(key, 0, -1);

		return new ArrayList<String>(set);
	}
	
	public static Long getCcnReceiveScore(String ccnId,String member) {

		String key = JedisConstant.genCcnReceiveListKey(ccnId);

		Jedis jedis = JedisDao.getJedis();
		Double socre = jedis.zscore(key, member);
        if(socre == null){
        	return null;
        }

		return socre.longValue();
	}

	/**
	 * 为通信点 添加接待通信点
	 * @param ccnId
	 * @param opeCcnId
	 * @return
	 */
	public static Boolean addCcnReceiveList(String ccnId, String opeCcnId) {

		String key = JedisConstant.genCcnReceiveListKey(ccnId);

		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.zadd(key, System.currentTimeMillis(), opeCcnId);
		
		logger.info("redis zadd key:" + key +" value: "+ opeCcnId);

		return (id >= 0);
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

	/**
	 * 设置通信点被谁接待
	 * @param ccnId
	 * @param opeCcnId
	 * @return
	 */
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
		
		logger.info("redis del key:" + key);

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
	
	// #################################
	/**
	 * 对话内容列表操作 get
	 * 
	 * @param userId
	 * @return
	 */
	public static List<String> getDialogueList(String uccnId, String cccnId) {

		String key = JedisConstant.getDialogueListKey(uccnId,cccnId);

		Jedis jedis = JedisDao.getJedis();

		List<String> list = jedis.lrange(key, 0, -1);

		return list;
	}

	public static Boolean addDialogueList(String uccnId, String cccnId,String message) {

		String key = JedisConstant.getDialogueListKey(uccnId,cccnId);

		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.rpush(key, message);
		
		logger.info("redis rpush key:" + key +" value: "+ message);

		return (id >= 0);
	}

	public static Boolean delDialogueList(String uccnId, String cccnId) {

		String key = JedisConstant.getDialogueListKey(uccnId,cccnId);

		Jedis jedis = JedisDao.getJedis();

		Long replay = jedis.del(key);
		
		logger.info("redis del key:" + key);

		return (replay > 0);
	}
	
	// #################################
	/**
	 * 队尾弹出需要保存的消息
	 */
	public static String rpopSaveDialogue() {

		Jedis jedis = JedisDao.getJedis();

		return jedis.rpop(JedisConstant.SAVE_DIALOGUE_LIST);
	}

	/**
	 * 队头插入需要保存的消息
	 */
	public static Boolean lpushSaveDialogue(String value) {

		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.lpush(JedisConstant.SAVE_DIALOGUE_LIST, value);
		
		logger.info("redis lpush key:" + JedisConstant.SAVE_DIALOGUE_LIST +" value: "+ value);

		return (id >= 0);
	}

	// ######################
	public static Set<String> getOffLineUserSet(){
		
		Jedis jedis = JedisDao.getJedis();
		
		return jedis.zrange(JedisConstant.OFF_LINE_USER_SET, 0, -1);
	}
	public static Boolean addOffLineUserSet(String userId){
		
		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.zadd(JedisConstant.OFF_LINE_USER_SET, System.currentTimeMillis(), userId);
		
		logger.info("redis zadd key:" + JedisConstant.OFF_LINE_USER_SET +" value: "+ userId);

		return (id >= 0);
	}
	
	public static Boolean remOffLineUserSet(String userId){
		
		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.zrem(JedisConstant.OFF_LINE_USER_SET, userId);
		
		logger.info("redis zrem key:" + JedisConstant.OFF_LINE_USER_SET +" value: "+ userId);

		return (id >= 0);
	}
	
	public static Boolean isInOffLineUserSet(String userId){
		Jedis jedis = JedisDao.getJedis();
		Double score = jedis.zscore(JedisConstant.OFF_LINE_USER_SET, userId);
		
		return (score != null);
	}
	
	//######################
	/**
	 * 获取客户等待列表
	 * @return
	 */
	public static Set<String> getCustomerWaitSet(){
		
		Jedis jedis = JedisDao.getJedis();
		
		return jedis.zrange(JedisConstant.CUSTOMER_WAIT_SET, 0, -1);
	}
	
	/**
	 * 添加连接点至客户等待列表
	 * @param ccnId
	 * @return
	 */
	public static Boolean addCustomerWaitSet(String ccnId){
		
		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.zadd(JedisConstant.CUSTOMER_WAIT_SET, System.currentTimeMillis(), ccnId);
		
		logger.info("redis zadd key:" + JedisConstant.CUSTOMER_WAIT_SET +" value: "+ ccnId);

		return (id >= 0);
	}
	
	public static Boolean remCustomerWaitSet(String ccnId){
		
		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.zrem(JedisConstant.CUSTOMER_WAIT_SET, ccnId);
		
		logger.info("redis zrem key:" + JedisConstant.CUSTOMER_WAIT_SET +" value: "+ ccnId);

		return (id >= 0);
	}
	
	public static Integer sizeCustomerWaitSet(){
		Jedis jedis = JedisDao.getJedis();
		Long card = jedis.zcard(JedisConstant.CUSTOMER_WAIT_SET);
		if(null == card){
			return 0;
		}
		
		return card.intValue();
	}
	public static String popCustomerWaitSet(){
		
		Jedis jedis = JedisDao.getJedis();
		Integer start = sizeCustomerWaitSet()-1;
		Set<String> set = jedis.zrange(JedisConstant.CUSTOMER_WAIT_SET, start, -1);
		if(CollectionUtils.isEmpty(set)){
			return null;
		}
		
		Iterator<String> it = set.iterator();
		if(!it.hasNext()){
			return null;
		}
		
		String member = it.next();
		//jedis.zrem(JedisConstant.CUSTOMER_WAIT_SET, member);
		
		return member;
	}
	
	public static void delCustomerWaitSet(String member){
		
		Jedis jedis = JedisDao.getJedis();
		jedis.zrem(JedisConstant.CUSTOMER_WAIT_SET, member);
	}
	
	public static Long getCustomerWaitScore(String member) {

		Jedis jedis = JedisDao.getJedis();
		Double socre = jedis.zscore(JedisConstant.CUSTOMER_WAIT_SET, member);
        if(socre == null){
        	return null;
        }

		return socre.longValue();
	}
	
	public static Integer getCustomerWaitTime(String member) {

		Jedis jedis = JedisDao.getJedis();
		Double socre = jedis.zscore(JedisConstant.CUSTOMER_WAIT_SET, member);
        if(socre == null){
        	return null;
        }
        Long diff = System.currentTimeMillis() - socre.longValue();
        diff = diff/1000;
        
		return diff.intValue();
	}
	// ######################
	public static Boolean setDialogueInfo(String customerId, String userCcnId,DialogueInfo dInfo){
		
		String keyStr = JedisConstant.DIALOGUE_INFO+customerId;
		userCcnId = (StringUtils.isBlank(userCcnId)) ? "-1" : userCcnId;
		
		String value = JsonUtil.toJson(dInfo);
		Jedis jedis = JedisDao.getJedis();
		Long result = jedis.hset(keyStr, userCcnId, value);
		
		if(result == null){
			return false;
		}
		
		return (result >=0);
	}
	
	public static Boolean delDialogueInfo(String customerId, String userCcnId){
		
		String key = JedisConstant.DIALOGUE_INFO+customerId;
		userCcnId = (StringUtils.isBlank(userCcnId)) ? "-1" : userCcnId;
		
		Jedis jedis = JedisDao.getJedis();
		jedis.hdel(key, userCcnId);
		
		Long size = jedis.hlen(key);
		if(size <= 0){
			jedis.del(key);
		}
		return true;
	}
	
	public static DialogueInfo getDialogueScore(String customerId,String userCcnId){
		
		String keyStr = JedisConstant.DIALOGUE_INFO+customerId;
		userCcnId = (StringUtils.isBlank(userCcnId)) ? "-1" : userCcnId;
		
		Jedis jedis = JedisDao.getJedis();
		String value = null ;
		try{
			 value = jedis.hget(keyStr, userCcnId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		if(StringUtils.isEmpty(value)){
			DialogueInfo dInfo = new DialogueInfo();
			dInfo.setCustomerId(Long.valueOf(customerId));
			dInfo.setUserCcnId(userCcnId);
			
			return dInfo;
		}
		
		DialogueInfo dInfo = (DialogueInfo) JsonUtil.getObjFromJson(value, DialogueInfo.class);
		
		return dInfo;
	}

	// ######################补充服务

	/**
	 * 分配任务
	 * 
	 * @param userIds
	 * @return
	 */
	public static String allocateCcnId() {
		
		List<String> userIds = getSwitchList();
		if(CollectionUtils.isEmpty(userIds)){
			return null;
		}
		Integer max = 0;
		String allocateUserId = null;
	    for(String userId : userIds){
	    	Integer result = getMaxReceiveCount(userId)-getReceiveCount(userId);
	    	
	    	if(result > max){
	    		max = result;
	    		allocateUserId = userId;
	    	}
	    }
		
		return  getUserCcnList(allocateUserId).get(0);

	}
	
	/**
	 * 获取所有在线用户（对话框未关闭,去重）
	 * 
	 * @return
	 */
	public static List<String> getOnlineUserIds(){
		
		String keyPattern = JedisConstant.genCcnKey(JedisConstant.USER_TYPE, "*");
		Jedis jedis = JedisDao.getJedis();
		Set<String> keys = jedis.keys(keyPattern);
		if(null == keys || keys.size() < 1){
			return null;
		}
		
		String[] keyArray = new String[keys.size()];
		keyArray = (String[]) keys.toArray(keyArray);
		
		//去重
		Set<String> set = new HashSet<String>();
		set.addAll(jedis.mget(keyArray));
		
		return new ArrayList<String>(set);
		
	}
	
	public static List<String> getSwitchList(){
		List<String> result = getOnlineUserIds();
		if(CollectionUtils.isEmpty(result)){
			result = new ArrayList<String>();
		}
		result.removeAll(getOffLineUserSet());
		return result;
	}
	
	public static Integer getReceiveCount(String userId){
		List<String> ccnIds = getUserCcnList(userId);
		if(CollectionUtils.isEmpty(ccnIds)){
			return 0;
		}
		Integer result = 0;
		for(String ccnId : ccnIds){
			Integer part = getCurrentReceiveCount(ccnId);
			result += part;
		}
		
		return result;
	}
	
	public static void main(String[] args) {
//		addCustomerWaitSet("100000044");
//		addCustomerWaitSet("100000045");
//		addCustomerWaitSet("100000046");
		
		System.out.println(popCustomerWaitSet());
		System.out.println(popCustomerWaitSet());
		System.out.println(popCustomerWaitSet());
		System.out.println(popCustomerWaitSet());
	}

}
