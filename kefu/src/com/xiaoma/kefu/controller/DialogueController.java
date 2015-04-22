package com.xiaoma.kefu.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import redis.clients.jedis.Jedis;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisDao;
import com.xiaoma.kefu.redis.JedisNameUtil;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.redis.SystemConfiguration;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueDetailService;
import com.xiaoma.kefu.service.DialogueService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.CookieUtil;
import com.xiaoma.kefu.util.DesUtil;

@Controller
@RequestMapping(value = "dialogue")
public class DialogueController {
	@Autowired
	private DialogueService dialogueService;
	
	@Autowired
	private DialogueDetailService dialogueDetailService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;

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
		return "/dialogue/comet";
	}

	@RequestMapping(value = "chat.action", method = RequestMethod.GET)
	public String chat(HttpSession session, Model model) {

		List<String> userIds = JedisTalkDao.getSwitchList();
//		userService.getUserById(id)
		return "/dialogue/chat";
	}
	
	/**
	 * 获取转接用户列表
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "switchList.action", method = RequestMethod.GET)
	public String switchList(HttpSession session, Model model) {
		
		return "/dialogue/switchList";
		
	}

	/**
	 * 客户对话框请求页 负责用户的创建与cookie的读写还有聊天历史记录！
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "customerChat.action", method = RequestMethod.GET)
	public String customerChat(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		
		String customerId = CookieUtil.getCustomerIdFromCookie(request);

		Long id = null;
		Boolean isNew = true;
		if (StringUtils.isNotBlank(customerId)) {
			try {
				id = Long.parseLong(customerId);
				isNew = false;
			} catch (Exception e) {
				logger.info("this is a new customer!");
			}
		}
		Customer customer = null;
		if(isNew){
			
		    customer = new Customer();
			//收集信息创建新客户
			String ip = CookieUtil.getIpAddr(request);
			customer.setIp(ip);
			id = customerService.insert(customer);
			
		}else{
		    customer = customerService.getCustomerById(id);
			List<DialogueDetail> list = dialogueDetailService.getLastRecordsByCustomerId(id);
			model.addAttribute("dialogueList", list);
		}
		
		// cookie操作
		Cookie cookie = CookieUtil.genCookieByCustomerId(id.toString());

		// 有效期五年
		cookie.setMaxAge(5 * 365 * 24 * 60 * 60);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		model.addAttribute("customer", customer);
		
		//留言框生成规则    --------------开始
		model.addAttribute("reply",dialogueService.findReplyWayList());
		model.addAttribute("message",dialogueService.findMessageObject());
		
		//获取客服部人员列表
		model.addAttribute("userList",userService.getResultDept(1));
		//留言框生成规则   ---------------结束

		return "/dialogue/customerChat";
	}

	@RequestMapping(value = "user.action", method = RequestMethod.GET)
	public String user(HttpSession session, Model model) {
		User user = (User) session.getAttribute(CacheName.USER);
		if (user == null)
			return "login";
		model.addAttribute(CacheName.USER, user);
		return "/dialogue/userChat";
	}

	@RequestMapping(value = "conopen.action", method = RequestMethod.GET)
	public String customerOnOpen(HttpServletRequest request,
			HttpSession session, Model model, String Message, Long start)
			throws Exception {

		// 1.深成或者获取 customer
		String ip = CookieUtil.getIpAddr(request);

		Customer customer = customerService.genCustomer(request);
		Long customerId = customer.getId();

		// 2.进入等待队列
		jedis.rpush(JedisNameUtil.WAIT_CUSTOMER_LIST, customer.getId()
				.toString());

		// 3.机器回答
		String dialogueKey = JedisNameUtil.genDialogueKey(customerId);
		jedis.rpush(dialogueKey, "机器对话！");

		// 4.返回相应信息
		return "/dialogue/index";
	}

	@RequestMapping(value = "conclose.action", method = RequestMethod.GET)
	public String customerOnClose(HttpSession session, Model model,
			Long customerId) {
		// 清除等待队列
		jedis.lrem(JedisNameUtil.WAIT_CUSTOMER_LIST, 0, customerId.toString());

		String dialogueKey = JedisNameUtil.genDialogueKey(customerId);

		// 获取全部对话
		List<String> dialogues = jedis.lrange(dialogueKey, 0, -1);

		// 存入数据库

		// 清楚redis
		jedis.del(dialogueKey);

		return "/dialogue/index";
	}

	@RequestMapping(value = "consend.action", method = RequestMethod.POST)
	public String customerOnSend(HttpServletRequest request,
			HttpSession session, Model model, String Message, Long customerId)
			throws Exception {
		// 参数坚持
		String dialogueKey = JedisNameUtil.genDialogueKey(customerId);
		jedis.lpush(dialogueKey, Message);
		return "/resultjson";
	}

	@RequestMapping(value = "conreceive.action", method = RequestMethod.POST)
	public String customerOnReceive(HttpServletRequest request, Model model,
			Long start, Long customerId) {
		// 参数坚持
		start = (start == null) ? 0L : start;
		String dialogueKey = JedisNameUtil.genDialogueKey(customerId);
		List<String> dialogues = jedis.lrange(dialogueKey, start, -1);
		start = jedis.llen(dialogueKey);

		model.addAttribute("dialogues", dialogues);
		model.addAttribute("start", start);

		return "/resultjson";
	}

	// ######################## 客服行为
	@RequestMapping(value = "uonopen.action", method = RequestMethod.GET)
	public String userOnOpen(HttpSession session, Model model) {

		// 1.从缓存中获取user
		User user = (User) session.getAttribute("currentUser");
		// 2.
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
			String id = DesUtil.encrypt(cookie.getValue(), SystemConfiguration
					.getInstance().getSecretKey());
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
