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
	 * 缩略图路径文件格式
	 */
	public static final String MIN_EXTENSION=".png";
	
	/**
	 * 聊天及记录结果展示中-聊天内容字段code
	 */
	public static final String CHAT_CONTENT = "chatContent";
	
	/**
	 * 邀请框第一个元素名称
	 */
	public static final String FIRST_ELEMENT_NAME = "外框";
	
	/**
	 * 风格div文件 存放目录
	 */
	public static final String JS_DIV_PATH = "style";
	
	/**
	 * 风格div文件 模板目录
	 */
	public static final String TEMPLATE_PATH = "style/template";
	
	/**
	 * js文件前缀
	 */
	public static final String JS_NAME = "loadKF";
	
	/**
	 * 风格div的模板文件
	 */
	public static final String JS_DIV_TEMPLATE = "template.js";
	
	/**
	 * 初始化图片	访客端界面 右上广告图
	 */
	public static final String PIC_TEMPLATE_CLIENT_YS = "clientYs.png";
	
	/**
	 * 初始化图片	访客端界面 右下广告图
	 */
	public static final String PIC_TEMPLATE_CLIENT_YX = "clientYx.png";
	
	/**
	 * 初始化图片	图标PC版
	 */
	public static final String PIC_TEMPLATE_PC_SERVICE = "pcServiceIcon.png";
	
	/**
	 * 初始化图片	图标 移动版
	 */
	public static final String PIC_TEMPLATE_YD_SERVICE = "ydServiceIcon.png";
	
	/**
	 * 初始化图片	邀请框pc版
	 */
	public static final String PIC_TEMPLATE_PC_INVITE = "pcInviteIcon.png";
	
	/**
	 * 初始化图片	邀请框移动版
	 */
	public static final String PIC_TEMPLATE_YD_INVITE = "ydInviteIcon.png";
	
	/**
	 * 对话框	缩略图
	 */
	public static final String PIC_TEMPLATE_CLIENT = "client.png";
	
	
	/**
	 * 角色的字典表Code
	 * *********************************
	* @Description: TODO
	* @author: wangxingfei
	* @createdAt: 2015年4月9日上午10:10:16
	**********************************
	 */
	public enum RoleName{
		yuangong,zhuguan
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
		,元素背景图("group"),元素背景图预览保存("group_pvw");
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
		top("${top}"),left("${left}"),onlinePic("${onlinePic}"),offlinePic("${offlinePic}"),
		isDisplay("${isDisplay}"),position("${position}"),buttonId("${buttonId}"),
		width("${width}"),height("${height}"),backgroundImg("${backgroundImg}"),
		index("${index}"),onclick("${onclick}"),target("${target}"),openUrl("${openUrl}");
		private String value;
		DivFieldName(String value){
			this.value = value;
		}
		public String getCode(){
			return this.value;
		}
	}
	
	
	/**
	 * 客服图标 DIV PC_在线 模板
	 */
	public static final String DIV_ICON_PC_ON =
			" <div id=\\\"w-kfrbox\\\" style=\\\"${isDisplay};${position};${top};${left};${width};${height};\\\"> "
			+	" <img src=\\\"${onlinePic}\\\" onclick=\\\"gotoKF('${buttonId}')\\\" style=\\\"cursor:pointer\\\" /> "
			+	" <img style=\\\"position:absolute;right:0px;top:0px;cursor:pointer;\\\" src=\\\"http://oc2.xiaoma.com/img/upload/53kf/zdyivt/zdyivt_53kf_1414986002.gif\\\" onclick=\\\"iconType=2;hiddenKfbox();\\\"> "
			+ " </div> "	;
	
	/**
	 * 客服图标 DIV PC_离线 模板
	 */
	public static final String DIV_ICON_PC_OFF =
			" <div id=\\\"w-kfrbox\\\" style=\\\"${isDisplay};${position};${top};${left};${width};${height};\\\"> "
			+	" <img src=\\\"${offlinePic}\\\" onclick=\\\"gotoKF('${buttonId}')\\\" style=\\\"cursor:pointer\\\" /> "
			+	" <img style=\\\"position:absolute;right:0px;top:0px;cursor:pointer;\\\" src=\\\"http://oc2.xiaoma.com/img/upload/53kf/zdyivt/zdyivt_53kf_1414986002.gif\\\" onclick=\\\"iconType=2;hiddenKfbox();\\\" /> "
			+ " </div> "	;
	
	/**
	 * 客服图标 DIV 移动_在线 模板
	 */
	public static final String DIV_ICON_YD_ON =
			" <div id=\\\"w-mkfrbox\\\" style=\\\"${isDisplay};${position};${top};${left};${width};${height};z-index:7999;overflow:hidden;\\\"> "
			+	" <div id=\\\"w-mkfrbox-cnt\\\" style=\\\"${width};${height};background-image:url(${onlinePic});background-repeat:no-repeat;z-index:9001;cursor:pointer;overflow:hidden;\\\" onclick=\\\"gotoKF('${buttonId}')\\\"></div> "
			+ " </div> "	;
	
	/**
	 * 客服图标 DIV 移动_离线 模板
	 */
	public static final String DIV_ICON_YD_OFF =
			" <div id=\\\"w-mkfrbox\\\" style=\\\"${isDisplay};${position};${top};${left};${width};${height};z-index:7999;overflow:hidden;\\\"> "
			+	" <div id=\\\"w-mkfrbox-cnt\\\" style=\\\"background-image:url(${offlinePic});background-repeat:no-repeat;z-index:9001;cursor:pointer;overflow:hidden;\\\" onclick=\\\"gotoKF('${buttonId}')\\\"></div> "
			+ " </div> "	;
			
	/**
	 * 邀请框最顶层 模板 ,注意没有结束标签,要自己加
	 */
	public static final String DIV_TEMPLATE_INVITE = 
			" <div id=\\\"w-kfcbox\\\" style=\\\"${position};${top};${left};${width};${height};z-index:7998;overflow:hidden;\\\"> " ;
			
	/**
	 * 邀请框中 第一个元素(外框) 的模板 ,注意没有结束标签,要自己加
	 */
	public static final String DIV_TEMPLATE_ELE_FIRST =
			" <div id=\\\"w-kfbox-cnt\\\" style=\\\"position:relative;${width};${height};background-image:url(${backgroundImg});background-repeat:no-repeat;z-index:${index};cursor:pointer;overflow:hidden;\\\" ${onclick} > " ;
			
	/**
	 * 邀请框中 除第一个元素外,其他元素的模板
	 */
	public static final String DIV_TEMPLATE_ELE_OTHER = 
			" <div style=\\\"position:absolute;${top};${left};${width};${height};background-image:url(${backgroundImg});background-repeat:no-repeat;z-index:${index};cursor:pointer;overflow:hidden;\\\" ${onclick} ></div> " ;
	
	/**
	 * 邀请框中 元素的超链接标签
	 */
	public static final String DIV_TEMPLATE_ELE_A = 
			" <a href=\\\"${openUrl}\\\" target=\\\"${target}\\\" style=\\\"cursor:pointer;text-decoration:none;\\\"> ";
	
	
	/**
	 * 手机 邀请框最顶层 模板 ,注意没有结束标签,要自己加
	 */
	public static final String DIV_TEMPLATE_INVITE_YD = 
			" <div id=\\\"w-mkfcbox\\\" style=\\\"${position};${top};${left};z-index:7998;overflow:hidden;\\\"> " ;
	
	/**
	 * 手机 邀请框中 第一个元素(外框) 的模板 ,注意没有结束标签,要自己加
	 */
	public static final String DIV_TEMPLATE_ELE_FIRST_YD =
			" <div id=\\\"w-mkfbox-cnt\\\" style=\\\"position:relative;z-index:${index};overflow:hidden;\\\"> " 
			+ " <img id=\\\"w-mkfcbox-img\\\" style=\\\"${width};cursor:pointer;\\\" src=\\\"${backgroundImg}\\\" ${onclick} alt=\\\"\\\" /> "
			;
			
	/**
	 * 手机 邀请框中 除第一个元素外,其他元素的模板
	 */
	public static final String DIV_TEMPLATE_ELE_OTHER_YD = 
			" <div style=\\\"position:absolute;${top};${left};${width};${height};background-image:url(${backgroundImg});background-repeat:no-repeat;z-index:${index};cursor:pointer;overflow:hidden;\\\" ${onclick} ></div> " ;
	
	
	/**
	 * div结束标签
	 */
	public static final String DIV_END = " </div> " ;
	
	/**
	 * a结束标签
	 */
	public static final String A_END = " </a> " ;
			

}
