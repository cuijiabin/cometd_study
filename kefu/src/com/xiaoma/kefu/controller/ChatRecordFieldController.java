package com.xiaoma.kefu.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.service.ChatRecordFieldService;

/**
 * *********************************
* @Description: 聊天记录结果字段配置
* @author: wangxingfei
* @createdAt: 2015年4月3日上午9:57:51
**********************************
 */
@Controller
@RequestMapping(value = "charRecordField")
public class ChatRecordFieldController {

	private Logger logger = Logger.getLogger(ChatRecordFieldController.class);

	@Autowired
	private ChatRecordFieldService chatRecordFieldService;
	
	/**
	* @Description: 保存自定义配置
	* @param session
	* @param date
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String saveRecord(HttpSession session,String date){
		//获取当前用户id
		chatRecordFieldService.saveRecord(1,date);
		return "";
	}
	
}
