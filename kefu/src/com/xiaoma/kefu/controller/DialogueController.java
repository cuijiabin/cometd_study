package com.xiaoma.kefu.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.redis.JedisDao;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueService;
import com.xiaoma.kefu.util.CookieUtil;
import com.xiaoma.kefu.util.DesUtil;
import com.xiaoma.kefu.util.PropertiesUtil;

@Controller
@RequestMapping(value = "dialogue")
public class DialogueController {

	@Autowired
	private DialogueService dialogueService;

	@Autowired
	private CustomerService customerService;

	private Jedis jedis = JedisDao.getJedis();

	private Logger logger = Logger.getLogger(DialogueController.class);

	/**
	 * 默认对话框
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index.action", method = RequestMethod.GET)
	public String index(HttpSession session, Model model) {
		return "/dialogue/index";
	}

	@RequestMapping(value = "conopen.action", method = RequestMethod.GET)
	public String customerOnOpen(HttpServletRequest request,HttpSession session, Model model, String Message, Long start)throws Exception {
		
		Long customerId = null;
		// 读取cookie信息
		Cookie[] cookies = request.getCookies();
		Cookie cookie = CookieUtil.getCustomerFromCookies(cookies);
		if (cookie == null) {
			customerId = customerService.getMaxCustomerId();
		} else {
			String id = DesUtil.encrypt(cookie.getValue(),PropertiesUtil.getProperties(CacheName.SECRETKEY));
			customerId = Long.valueOf(id);
			// 过期时间五年
			cookie.setMaxAge(5 * 365 * 24 * 60 * 60);
		}

		// 进入等待队列
		jedis.lrange("", start, -1);
		return "/dialogue/index";
	}

	@RequestMapping(value = "conclose.action", method = RequestMethod.GET)
	public String customerOnClose(HttpSession session, Model model) {
		return "/dialogue/index";
	}

	@RequestMapping(value = "consend.action", method = RequestMethod.POST)
	public String customerOnSend(HttpServletRequest request,
			HttpSession session, Model model, String Message, Long start)
			throws Exception {

		return "/resultjson";
	}

	@RequestMapping(value = "conreceive.action", method = RequestMethod.POST)
	public String customerOnReceive(HttpServletRequest request,
			HttpSession session, Model model, Integer clientType) {

		return "/resultjson";
	}

	// ######################## 客服行为
	@RequestMapping(value = "uonopen.action", method = RequestMethod.GET)
	public String userOnOpen(HttpSession session, Model model) {
		return "/dialogue/index";
	}

	/**
	 * 不支持websocket再次请求
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "uonclose.action", method = RequestMethod.GET)
	public String userOnClose(HttpSession session, Model model) {
		return "/dialogue/index";
	}

	@RequestMapping(value = "uonsend.action", method = RequestMethod.POST)
	public String userOnSend(HttpServletRequest request, HttpSession session,
			Model model, String Message, Long start) throws Exception {

		Long customerId = null;
		// 读取cookie信息
		Cookie[] cookies = request.getCookies();
		Cookie cookie = CookieUtil.getCustomerFromCookies(cookies);
		if (cookie == null) {
			customerId = customerService.getMaxCustomerId();
		} else {
			String id = DesUtil.encrypt(cookie.getValue(),
					PropertiesUtil.getProperties(CacheName.SECRETKEY));
			customerId = Long.valueOf(id);
			// 过期时间五年
			cookie.setMaxAge(5 * 365 * 24 * 60 * 60);
		}

		// 进入等待队列
		jedis.lrange("", start, -1);

		return "/resultjson";
	}

	@RequestMapping(value = "uonreceive.action", method = RequestMethod.POST)
	public String userOnReceive(HttpServletRequest request,
			HttpSession session, Model model, Integer clientType) {

		return "/resultjson";
	}

}
