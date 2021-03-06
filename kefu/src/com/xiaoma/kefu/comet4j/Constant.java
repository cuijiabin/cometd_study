package com.xiaoma.kefu.comet4j;

/**
 * 长连接对话参数
 * @author cuijiabin
 *
 */
public class Constant {
	public static final String CHANNEL = "dialogue";
	public static final String ON_OPEN = "on_open";
	public static final String ON_CLOSE = "on_close";
	public static final String ON_MESSAGE = "on_message";
	public static final String UPDATE_LIST = "update_list";
	public static final String END_DIALOGUE = "end_dialogue";
	
	//客服不在
	public static final String NO_USER = "no_user";
	//客服繁忙
	public static final String USER_BUSY = "user_busy";
	
	//客户转接通知
	public static final String ON_SWITCH_CUSTOMER = "on_switch_customer";
	public static final String ON_SWITCH_FROM = "on_switch_from";
	public static final String ON_SWITCH_TO = "on_switch_to";

}