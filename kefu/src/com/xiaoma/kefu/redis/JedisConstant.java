package com.xiaoma.kefu.redis;

import org.apache.commons.lang.StringUtils;

/**
 * 只存放与对话有关的redis常量
 * 
 * @author cuijiabin
 *
 */
public class JedisConstant {

	// #######################对话专用
	/**
	 * 客户类型
	 */
	public static final Integer CUSTOMER_TYPE = 1;

	/**
	 * 客服类型
	 */
	public static final Integer USER_TYPE = 2;

	/**
	 * 通信点id
	 */
	public static final String CCN_ID = "ccn_id:";

	/**
	 * 当前通信点(ccnId+type --> userId)
	 */
	public static final String CURRENT_CCN_TYPE = "current_ccn_type:";

	/**
	 * 用户的通信点列表(userId --> zset[ccnId])
	 */
	public static final String USER_CCN_LIST = "user_ccn_list:";

	/**
	 * 当前通信点列表(userType --> zset[ccnId])
	 */
	public static final String CURRENT_CCN_LIST = "current_ccn_list:";

	// #######################与接待有关系

	/**
	 * 最大接待量（userId --> num）
	 */
	public static final String MAX_RECEIVE_COUNT = "max_receive_count:";

	/**
	 * 用户通信点列表(ccnId --> zset[ccnId])
	 */
	public static final String CCN_RECEIVE_LIST = "ccn_receive_list:";

	/**
	 * 被接收通信点
	 */
	public static final String CCN_PASSIVE = "ccn_passive:";

	/**
	 * 对话关系 (ccnId +type <--> ccnId)
	 */
	public static final String TALKER_TYPE = "talker_type:";

	/**
	 * 对话列表( 客服ccnId+客户ccnId --> list)
	 */
	public static final String DIALOGUE_LIST = "dialogue_list:";

	/**
	 * 待保存对话队列
	 */
	public static final String SAVE_DIALOGUE_LIST = "save_dialogue_list";

	/**
	 * 缓存客服信息
	 */
	public static final String USER_INFO = "user_info:";

	/**
	 * 缓存客户信息
	 */
	public static final String CUSTOMER_INFO = "customer_info:";

	/**
	 * 离线用户列表
	 */
	public static final String OFF_LINE_USER_SET = "off_line_user_set";

	// #######生成key方法
	/**
	 * 生成通信点链接的key 区分类型
	 * 
	 * @param type
	 * @param ccnId
	 * @return
	 */
	public static String genCcnKey(Integer type, String ccnId) {

		return CURRENT_CCN_TYPE + type + CCN_ID + ccnId;
	}

	/**
	 * 生成用户通信点列表key
	 * 
	 * @param userId
	 * @return
	 */
	public static String genUserCcnListKey(String userId) {

		return USER_CCN_LIST + userId;
	}

	/**
	 * 生成当前通信点点列表的key
	 * 
	 * @param type
	 * @return
	 */
	public static String genCcnListKey(Integer type) {

		return CURRENT_CCN_LIST + type;
	}

	// ##################################################
	/**
	 * 生成最大接待数量的key
	 * 
	 * @param userId
	 * @return
	 */
	public static String genMaxReceiveCountKey(String userId) {

		return MAX_RECEIVE_COUNT + userId;
	}

	/**
	 * 生成通信点接待列表
	 * 
	 * @param ccnId
	 * @return
	 */
	public static String genCcnReceiveListKey(String ccnId) {

		return CCN_RECEIVE_LIST + ccnId;
	}

	/**
	 * 生成被接收通信点
	 */
	public static String genCcnPassiveKey(String ccnId) {

		return CCN_PASSIVE + ccnId;
	}

	/**
	 * 生成对话关系的key
	 * 
	 * @param type
	 * @return
	 */
	public static String genTalkerRelationKey(Integer type, String ccnId) {

		return TALKER_TYPE + type + CCN_ID + ccnId;
	}

	/**
	 * 生成对话列表的key
	 * 
	 * @param userId
	 * @return
	 */
	public static String getDialogueListKey(String uccnId, String cccnId) {

		return DIALOGUE_LIST + uccnId + CCN_ID + cccnId;
	}

	/**
	 * 检查对话列key表格式是否正确
	 * 
	 * @param dialogueListKey
	 * @return
	 */
	public static Boolean checkDialogueListKey(String dialogueListKey) {

		if (StringUtils.isBlank(dialogueListKey)) {
			return false;
		}

		int start = dialogueListKey.indexOf(DIALOGUE_LIST);
		if (start < 0) {
			return false;
		}

		int end = dialogueListKey.indexOf(CCN_ID);
		if (end <= start + DIALOGUE_LIST.length()) {
			return false;
		}

		return true;
	}

	public static String getUccnIdFromKey(String dialogueListKey) {
		int start = dialogueListKey.indexOf(DIALOGUE_LIST)
				+ DIALOGUE_LIST.length();
		int end = dialogueListKey.indexOf(CCN_ID);

		return dialogueListKey.substring(start, end);
	}

	public static String getCccnIdFromKey(String dialogueListKey) {

		int start = dialogueListKey.indexOf(CCN_ID) + CCN_ID.length();

		return dialogueListKey.substring(start);
	}

	public static void main(String[] args) {
	}

}
