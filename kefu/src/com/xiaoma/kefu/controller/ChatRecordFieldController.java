package com.xiaoma.kefu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

	
}
