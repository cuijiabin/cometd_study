package com.xiaoma.kefu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.MessageRecords;
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
	
	
	/**
	 * 展示留言详情
	* @Description: TODO
	* @param model
	* @param msgId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月12日
	 */
	@RequestMapping(value = "view.action", method = RequestMethod.GET)
	public String view(Model model,Integer msgId) {
		try {
			MessageRecords msg = messageRecordsService.findById(Integer.valueOf(msgId));
			model.addAttribute("msg", msg);
			return "/record/message/view";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MessageRecordsController.view出错了!");
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
}
