package com.xiaoma.kefu.controller;

import java.util.List;

import net.sf.json.JSONArray;
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
@RequestMapping(value="messageType")
public class MessageTypeController {
    
	 @Autowired
	 private MessageTypeService messageTypeService;
	 
		//查询各个级别的树
	    @SuppressWarnings("static-access")
		@RequestMapping(value="main.action", method = RequestMethod.GET)
		public String main(Model model,Integer id) {
			if(id !=null){
			List list = messageTypeService.findTree(id);
			
			JSONArray json = new JSONArray().fromObject(list);
			System.out.println(json);
			model.addAttribute("json",json.toString());
			return "message/messageSet";
			}else{
				return "null";
			}
		}
	 
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
		 
		 return "message/publicMessageType";
	 }
	 
}
