package com.xiaoma.kefu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaoma.kefu.service.DialogueService;

@Controller
@RequestMapping(value = "dialogue")
public class DialogueController {

	private Logger logger = Logger.getLogger(DialogueController.class);
	
	@Autowired
	private DialogueService dialogueService;

	
}
