package com.xiaoma.kefu.util;

/**
 * 系统常量
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月7日下午6:04:04
**********************************
 */
public class SysConst {
	/**
	 * 聊天记录导出 存放目录
	 */
	public static final String EXP_TALK_PATH="expTalk";
	
	/**
	 * 聊天及记录结果展示中-聊天内容字段code
	 */
	public static final String CHAT_CONTENT = "chatContent";
	
	/**
	 * 角色的ID
	 * *********************************
	* @Description: TODO
	* @author: wangxingfei
	* @createdAt: 2015年4月9日上午10:10:16
	**********************************
	 */
	public enum RoleNameId{
		员工(4);
		private Integer value;
		RoleNameId(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	/**
	 * 运算符	
	 * *********************************
	* @Description: TODO
	* @author: wangxingfei
	* @createdAt: 2015年4月13日下午5:08:53
	**********************************
	 */
	public enum CompareEnum{
		XY("<"),EQ("="),DY(">");
		private String value;
		CompareEnum(String value){
			this.value = value;
		}
		public String getCode(){
			return this.value;
		}
	}
	
	
}
