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
	 * 风格 图片根路径
	 */
	public static final String STYLE_PATH="style";
	
	/**
	 * 缩略图路径后缀
	 */
	public static final String MIN_PIC_SUFFIX="_min";
	
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
	
	/**
	 * 风格 上传图片的 保存文件名称
	 * 完整路径为 	root/STYLE_PATH/风格id/此枚举.xx
	 * *********************************
	* @Description: TODO
	* @author: wangxingfei
	* @createdAt: 2015年4月14日下午4:53:02
	**********************************
	 */
	public enum StylePicName{
		访问端右上("clientYs"),访问端右下("clientYx")
		,客服图标PC在线("servicePCon"),客服图标PC离线("servicePCoff")
		,客服图标移动在线("serviceYDon"),客服图标移动离线("serviceYDoff")
		,邀请框PC("invitePC"),邀请框移动("inviteYD")
		,元素背景图("group");
		private String value;
		StylePicName(String value){
			this.value = value;
		}
		public String getCode(){
			return this.value;
		}
	}
	
	/**
	 * 设备类型
	 * *********************************
	* @Description: TODO
	* @author: wangxingfei
	* @createdAt: 2015年4月17日下午2:46:32
	**********************************
	 */
	public enum DeviceType{
		PC(1),移动(2);
		private Integer value;
		DeviceType(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	/**
	 * 风格下5个界面的 类型ID
	 * *********************************
	* @Description: TODO
	* @author: wangxingfei
	* @createdAt: 2015年4月19日下午4:29:45
	**********************************
	 */
	public enum StyleIconType{
		访问端界面(1),客服图标(2),对话邀请框(3),手机端客服图标(4),手机端对话邀请框(5);
		private Integer value;
		StyleIconType(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	
}
