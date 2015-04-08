package com.xiaoma.kefu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaoma.kefu.service.MessageRecordsService;

/**
 * 留言信息 	controller
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月8日上午11:01:34
**********************************
 */
@Controller
@RequestMapping(value = "messageRecords")
public class MessageRecordsController {

	@Autowired
	private MessageRecordsService messageRecordsService;
	
	private Logger logger = Logger.getLogger(MessageRecordsController.class);
	

	
}
