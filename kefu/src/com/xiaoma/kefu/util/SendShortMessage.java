package com.xiaoma.kefu.util;

import java.util.HashMap;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.thread.ShortMessageThread;

/**
 * 
 * @author seven
 *
 */
@Component("sendShortMessage")
public class SendShortMessage {
	
	public void init(){
		
	}

	private final static int VALIDATE_CODE_LENGTH = 6; //验证码长度
	private String validcode_save_time="1"; //验证码保存时间 默认2分钟
	private String short_message_model="10252";// 短信模版id
	/*
	 * 验证码
	 */
	private String validCode;
	private String phoneNum;
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
	public String getValidcode_save_time() {
		return validcode_save_time;
	}

	public void setValidcode_save_time(String validcode_save_time) {
		this.validcode_save_time = validcode_save_time;
	}

	public String getShort_message_model() {
		return short_message_model;
	}

	public void setShort_message_model(String short_message_model) {
		this.short_message_model = short_message_model;
	}

	/**
	 * 向手机发送验证码，并且返回消息数组（验证码以及保存时间）
	 * 
	 * @param sendTo
	 * @return
	 */
	public boolean sendShortMessage(String [] params) {

		HashMap<String, Object> result = null;

		// 初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

		// ******************************注释*********************************************
		// *初始化服务器地址和端口 *
		// *沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		// *生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883"); *
		// *******************************************************************************
//		restAPI.init("sandboxapp.cloopen.com", "8883");
		restAPI.init("app.cloopen.com", "8883");
		// ******************************注释*********************************************
		// *初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN *
		// *ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
		// *参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。 *
		// *******************************************************************************
		restAPI.setAccount("aaf98f89486445e6014881872f2309c3", "0669ff0ab2df44e4aa40182aed1e34b0");

		// ******************************注释*********************************************
		// *初始化应用ID *
		// *测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID *
		// *应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		// *******************************************************************************
		restAPI.setAppId("aaf98f894a70a61d014a80a3f7760ab6");

		// ******************************注释****************************************************************
		// *调用发送模板短信的接口发送短信 *
		// *参数顺序说明： *
		// *第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号 *
		// *第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。 *
		// *系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
		// *第三个参数是要替换的内容数组。 *
		// **************************************************************************************************

		// **************************************举例说明***********************************************************************
		// *假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为 *
		// *result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"}); *
		// *则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入 *
		// *********************************************************************************************************************
//		setValidCode(code);
//		setPhoneNum(sendTo);
//		setValidcode_save_time(validcode_save_time);
		
//		result = restAPI.sendTemplateSMS(sendTo, "1", new String[] { code, "2" });
//		System.out.println("sendTo=="+phoneNum+",shor_message_model=="+short_message_model+",save_time=="+validcode_save_time);
		result = restAPI.sendTemplateSMS(phoneNum, short_message_model, params);

		
//		System.out.println("SDKTestGetSubAccounts result=" + result);
		if ("000000".equals(result.get("statusCode"))) {
			// 正常返回输出data包体信息（map）
			HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				Object object = data.get(key);
				System.out.println(key + " = " + object);
			}

			return true;
		} else {
			// 异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			return false;
		}

	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	public static String getValidateCode() {
		String code = "";
		String nanoTime = System.nanoTime() + "";
		int count = 0;
		for (int i = nanoTime.length() - 1; i >= 0; i--) {
			code += nanoTime.charAt(i);
			count++;
			if (count == VALIDATE_CODE_LENGTH) {
				break;
			}
		}
		return code;
	}

	public static void main(String[] args) {
		String code = getValidateCode();
		System.out.println("code:"+code);
		
		boolean flag = new SendShortMessage().sendShortMessage(new String[] { code, "1" });
		System.out.println(flag);
		System.out.println("4d9d08fed1f04ca484509e8ffe5886f6".length());
//		CacheMan.add(CacheName.SHORTMESSAGE,"18511372116","111111");
//		System.out.println(CacheMan.getObject(CacheName.SHORTMESSAGE,"18511372116"));
//		Thread smThread = new ShortMessageThread(CacheName.SHORTMESSAGE+"18511372116");
//		if(smThread != null){
//			try {
//				Thread.sleep(60000);
//				smThread.start();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
			
	}

}
