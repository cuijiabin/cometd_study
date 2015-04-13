package com.xiaoma.kefu.redis;

/**
 * 只存放与对话有关的redis常量
 * 
 * @author cuijiabin
 *
 */
public class JedisConstant {

	// #######################对话专用
	/**
	 * 当前用户
	 */
	public static final String CURRENT_USER_TYPE = "current_user_type:";

	/**
	 * 连接通道
	 */
	public static final String CCN_ID = "ccn_id:";

	/**
	 * 客户类型
	 */
	public static final Integer CUSTOMER_TYPE = 1;

	/**
	 * 客服类型
	 */
	public static final Integer USER_TYPE = 2;

	/**
	 * 用户列表(zset)
	 */
	public static final String CURRENT_USER_LIST = "current_user_list:";
	
	/**
	 * 用户连接列表(zset)
	 */
	public static final String USER_CCN_LIST = "user_ccn_list:";
	
	/**
	 * 连接列表(zset)
	 */
	public static final String CURRENT_CCN_LIST = "current_ccn_list:";

	/**
	 * 对话关系
	 */
	public static final String TALKER_TYPE = "talker_type:";

	/**
	 * 当前接待量
	 */
	public static final String CURRENT_RECEIVE_COUNT = "current_receive_count:";

	/**
	 * 最大接待量
	 */
	public static final String MAX_RECEIVE_COUNT = "max_receive_count:";

	/**
	 * 接待列表(zset)
	 */
	public static final String RECEIVE_LIST = "receive_list:";

	/**
	 * 对话列表
	 */
	public static final String DIALOGUE_LIST = "dialogue_list:";

	// #######生成key方法
	/**
	 * 获取当前用户id的key
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static String getCurrentUserKey(Integer type, String ccnId) {

		return CURRENT_USER_TYPE + type + CCN_ID + ccnId;
	}

	/**
	 * 获取当前连接点列表的key
	 * 
	 * @param type
	 * @return
	 */
	public static String getCcnListKey(Integer type) {

		return CURRENT_CCN_LIST + type;
	}
	
	/**
	 * 获取当前连接点列表的key
	 * 
	 * @param type
	 * @return
	 */
	public static String getUserCcnListKey(String userId) {

		return USER_CCN_LIST + userId;
	}
	
	/**
	 * 获取当前用户列表的key
	 * 
	 * @param type
	 * @return
	 */
	public static String getCurrentUserListKey(Integer type) {

		return CURRENT_USER_LIST + type;
	}

	/**
	 * 获取对话关系的key
	 * 
	 * @param type
	 * @return
	 */
	public static String getTalkerKey(Integer type, String ccnId) {

		return TALKER_TYPE + type + CCN_ID + ccnId;
	}

	/**
	 * 获取接待数量的key
	 * 
	 * @param userId
	 * @return
	 */
	public static String getCurrentReceiveCountKey(Integer userId) {

		return CURRENT_RECEIVE_COUNT + userId;
	}

	/**
	 * 获取最大接待数量的key
	 * 
	 * @param userId
	 * @return
	 */
	public static String getMaxReceiveCountKey(Integer userId) {

		return MAX_RECEIVE_COUNT + userId;
	}

	/**
	 * 获取接待列表的key
	 * 
	 * @param userId
	 * @return
	 */
	public static String getReceiveListKey(Integer userId) {

		return RECEIVE_LIST + userId;
	}
	
	/**
	 * 获取对话列表的key
	 * 
	 * @param userId
	 * @return
	 */
	public static String getDialogueListKey(String customerCcnId, String userCcnId) {

		return DIALOGUE_LIST + customerCcnId+ CCN_ID + userCcnId;
	}
}
