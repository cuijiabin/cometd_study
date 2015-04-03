package com.xiaoma.kefu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaoma.kefu.service.WaitListService;

@Controller
@RequestMapping(value = "waitList")
public class WaitListController {

	private Logger logger = Logger.getLogger(WaitListController.class);
	
	@Autowired
	private WaitListService waitListService;

	
}
