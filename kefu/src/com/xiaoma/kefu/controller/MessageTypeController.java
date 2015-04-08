package com.xiaoma.kefu.controller;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.MessageType;
import com.xiaoma.kefu.service.MessageTypeService;


/**
 * @author frongji
 * @time 2015年4月8日下午3:32:41
 *   常用语分类  Controller class
 */
@Controller
@RequestMapping(value = "messageType")
public class MessageTypeController {
    
	 @Autowired
	 private MessageTypeService messageTypeService;
	 
	 /**
	  * 显示详细信息
	  * @param mode
	  * @return
	  */
	 @RequestMapping(value = "detail.action",method = RequestMethod.GET)
	 public String messageTypeDetail(Model mode ,Integer id){
		 
		 
		 MessageType messageType = messageTypeService.getMessageTypeById(id);
		
		 JSONObject jsonObject = JSONObject.fromObject(messageType);
         mode.addAttribute("result", jsonObject.toString());
		 
		 return "xxxx";
	 }
	 
}
