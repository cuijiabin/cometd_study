package com.xiaoma.kefu.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisDao;
import com.xiaoma.kefu.redis.JedisNameUtil;
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


	/**
	 * 默认对话框
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index.action", method = RequestMethod.GET)
	public String index(HttpSession session, Model model) {
		return "/dialogue/comet";
	}
	
	@RequestMapping(value = "chat.action", method = RequestMethod.GET)
	public String chat(HttpSession session, Model model) {
		
		return "/dialogue/chat";
	}
	
	@RequestMapping(value = "customerChat.action", method = RequestMethod.GET)
	public String customerChat(HttpSession session, Model model) {
		
		return "/dialogue/customerChat";
	}
	
	@RequestMapping(value = "user.action", method = RequestMethod.GET)
	public String user(HttpSession session, Model model) {
		return "/dialogue/userChat";
	}

	@RequestMapping(value = "conopen.action", method = RequestMethod.GET)
	public String customerOnOpen(HttpServletRequest request,HttpSession session, Model model, String Message, Long start)throws Exception {
		
		
		//1.深成或者获取 customer
		String ip = CookieUtil.getIpAddr(request);
				
		Customer customer = customerService.genCustomer(request);
		Long customerId = customer.getId();
		
		//2.进入等待队列
		jedis.rpush(JedisNameUtil.WAIT_CUSTOMER_LIST, customer.getId().toString());

		//3.机器回答
		String dialogueKey =JedisNameUtil.genDialogueKey(customerId);
		jedis.rpush(dialogueKey,"机器对话！");
		
		//4.返回相应信息
		return "/dialogue/index";
	}

	@RequestMapping(value = "conclose.action", method = RequestMethod.GET)
	public String customerOnClose(HttpSession session, Model model,Long customerId) {
		//清除等待队列
		jedis.lrem(JedisNameUtil.WAIT_CUSTOMER_LIST, 0, customerId.toString());
		
		String dialogueKey =JedisNameUtil.genDialogueKey(customerId);
		
		//获取全部对话
		List<String> dialogues = jedis.lrange(dialogueKey, 0, -1);
		
		//存入数据库
		
		//清楚redis
		jedis.del(dialogueKey);
				
		return "/dialogue/index";
	}

	@RequestMapping(value = "consend.action", method = RequestMethod.POST)
	public String customerOnSend(HttpServletRequest request,HttpSession session, Model model, String Message, Long customerId)
			throws Exception {
		//参数坚持
		String dialogueKey =JedisNameUtil.genDialogueKey(customerId);
		jedis.lpush(dialogueKey,Message);
		return "/resultjson";
	}

	@RequestMapping(value = "conreceive.action", method = RequestMethod.POST)
	public String customerOnReceive(HttpServletRequest request,Model model, Long start,Long customerId) {
		//参数坚持
		start = (start == null) ? 0L :start;
		String dialogueKey =JedisNameUtil.genDialogueKey(customerId);
		List<String> dialogues = jedis.lrange(dialogueKey, start, -1);
		start = jedis.llen(dialogueKey);
		
		model.addAttribute("dialogues", dialogues);
		model.addAttribute("start", start);

		return "/resultjson";
	}

	// ######################## 客服行为
	@RequestMapping(value = "uonopen.action", method = RequestMethod.GET)
	public String userOnOpen(HttpSession session, Model model) {
		
		//1.从缓存中获取user
		User user = (User) session.getAttribute("currentUser");
		//2.
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
