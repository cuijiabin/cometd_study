package com.xiaoma.kefu.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiaoma.kefu.comet4j.DialogueScore;

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

		return (id > 0);
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

		return (id > 0);
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

		return (id > 0);
	}
	
	public static Boolean remOffLineUserSet(String userId){
		
		Jedis jedis = JedisDao.getJedis();

		Long id = jedis.zrem(JedisConstant.OFF_LINE_USER_SET, userId);
		
		logger.info("redis zrem key:" + JedisConstant.OFF_LINE_USER_SET +" value: "+ userId);

		return (id > 0);
	}
	// ######################
	public static Boolean setDialogueScore(String key, DialogueScore dScore){
		
		JedisDao.setKO(JedisConstant.DIALOGUE_SCORE+key, dScore);
		return true;
	}
	
	public static Boolean delDialogueScore(String key){
		
		JedisDao.remove(JedisConstant.DIALOGUE_SCORE+key);
		return true;
	}
	public DialogueScore getDialogueScore(String key){
		
		Object obj = JedisDao.getObject(JedisConstant.DIALOGUE_SCORE+key);
		
		return (DialogueScore) obj;
	}

	// ######################补充服务

	/**
	 * 分配任务
	 * 
	 * @param userIds
	 * @return
	 */
	public static String allocateCcnId() {
		
		String key = JedisConstant.genCcnListKey(JedisConstant.USER_TYPE);

		Jedis jedis = JedisDao.getJedis();
		
		Long size = jedis.zcount(key, Long.MIN_VALUE, Long.MAX_VALUE);
		size = (size == 0) ? 0L : size-1;
		
		Set<String> set = jedis.zrange(key, Integer.parseInt(size.toString()), -1);
		Iterator<String> it = set.iterator();
		return it.next();

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

}
