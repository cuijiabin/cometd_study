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
	
	
	/**
	 * 图标,邀请框 div 里面的字段名称 和 对应模板中的 变量名称
	 * *********************************
	* @Description: TODO
	* @author: wangxingfei
	* @createdAt: 2015年4月24日下午2:45:15
	**********************************
	 */
	public enum DivFieldName{
		top("${top}"),left("${left}");
		private String value;
		DivFieldName(String value){
			this.value = value;
		}
		public String getCode(){
			return this.value;
		}
	}
	
	
	/**
	 * 客服图标 DIV 模板
	 */
	public static final String DIV_TEMPLATE_ICON =
				" <div id=\\\"w-kfrbox\\\" style=\\\"position:fixed;top:200px;right:10px;\\\"> "
				+	" <div style=\\\"position:absolute;${top};${left};overflow:hidden;width:11px;height:11px;background-image:url(http://oc2.xiaoma.com/img/upload/53kf/zdyivt/zdyivt_53kf_1414986002.gif);background-repeat:no-repeat;z-index:2;cursor:pointer;\\\"></div> "
				+	" <img src=\\\"http://pics2.xiaoma.com/xiaoma/sem/float/kc_rtel_05.png\\\" onclick=\\\"window.open('http://oc2.xiaoma.com/new/client.php?arg=53kf&amp;style=3&amp;l=zh-cn&amp;charset=utf-8&amp;lytype=0&amp;referer=http%3A%2F%2Fkecheng.xiaoma.com%2F&amp;isvip=bcf14bbb85a346c2fb52e8cea8822cce&amp;identifier=&amp;keyword=http%3A//kecheng.xiaoma.com/&amp;tfrom=1&amp;tpl=crystal_blue','_blank','height=573,width=803,top=200,left=200,status=yes,toolbar=no,menubar=no,resizable=yes,scrollbars=no,location=no,titlebar=no')\\\" style=\\\"cursor:pointer\\\"> "
				+ " </div> "	
			;
	
}
