package com.xiaoma.kefu.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.UserService;

@Controller
@RequestMapping(value = "user")
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	/**
	 * User login
	 * 
	 * @param name
	 * @param password
	 * @param session
	 */
	@RequestMapping(value = "login.action", method = RequestMethod.POST)
	public String login(HttpSession session, String name, String password,
			Model model) {
		Assert.notNull(name, "userName can not be null!");
		Assert.notNull(password, "password can not be null!");
		model.addAttribute("msg", "登录名或者密码为空!");
		User user = userService.login(name, password);
		model.addAttribute("msg", "登录名或者密码不正确!");
		if (user != null) {
			session.setAttribute("currentUser", user);
			return "/views/welcome";
		} else {
			logger.debug("login failed!username or password is incorrect.");
			return "/views/login";
		}
	}

	
}
