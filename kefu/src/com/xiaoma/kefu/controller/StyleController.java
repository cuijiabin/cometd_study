package com.xiaoma.kefu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaoma.kefu.service.StyleService;

/**
 * *********************************
* @Description: 风格	controller
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:20:30
**********************************
 */
@Controller
@RequestMapping(value = "style")
public class StyleController {

	private Logger logger = Logger.getLogger(StyleController.class);

	@Autowired
	private StyleService styleService;
	
}
