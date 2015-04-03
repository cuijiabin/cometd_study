package com.xiaoma.kefu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "dialogue")
public class DialogueController {

	/**
	 * 返回默认对话框！
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index.action", method = RequestMethod.GET)
	public String index(HttpSession session, Model model) {
		return "/dialogue/index";
	}
}
