package com.xiaoma.kefu.redis;

public class JedisNameUtil {
	
	/**
	 * 系统用户
	 */
	public static final String USER = "user:";
	
	public static final String CCId = "CometConnectionId:";
	
	/**
	 * 在线客服列表
	 */
	public static final String ONLINE_USER_LIST = "online_user_list";
	
	/**
	 * 繁忙客服列表
	 */
	public static final String BUSY_USER_LIST = "busy_user_list";
	
	/**
	 * 空闲客服列表
	 */
	public static final String IDLE_USER_LIST = "idle_user_list";
	
	/**
	 * 客户等待列表
	 */
	public static final String WAIT_CUSTOMER_LIST = "wait_customer_list";
	
	
	/**
	 * 临时对话标识
	 */
	public static final String TEMPORARY_DIALOGUE = "temporary_dialogue:";
	
	/**
	 * 部门信息
	 */
	public static final String DEPARTMENT = "department:";
	
	public static String getUserKey(Integer id){
		
		return USER+id;
	}
	
	public static String getDepartmentKey(Integer id){
		
		return DEPARTMENT+id;
	}
	
	/**
	 * 生成对话标记 （统一使用）
	 * @param customerId
	 * @return
	 */
	public static String genDialogueKey(Long customerId){
		
		return TEMPORARY_DIALOGUE+customerId;
	}
	
}
