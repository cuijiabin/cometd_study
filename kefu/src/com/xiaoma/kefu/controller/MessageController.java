package com.xiaoma.kefu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Message;
import com.xiaoma.kefu.service.MessageService;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author frongji
 * @time 2015年4月13日下午2:56:21
 *
 */
@Controller
@RequestMapping(value="message")
public class MessageController {
       
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(MapEntity conditions, @ModelAttribute("pageBean") PageBean<Message> pageBean) {

		try {
			messageService.getResult(conditions.getMap(), pageBean);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "customer/customer";
			else
				return "customer/customerList";
		} catch (Exception e) {
			return "/error500";
		}

	}
	
}
