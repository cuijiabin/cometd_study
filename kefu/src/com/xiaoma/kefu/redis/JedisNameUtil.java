package com.xiaoma.kefu.redis;

public class JedisNameUtil {
	
	/**
	 * 系统用户
	 */
	public static final String USER = "user:";
	
	
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
	
}
