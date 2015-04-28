package com.xiaoma.kefu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.comet4j.DialogueInfo;
import com.xiaoma.kefu.comet4j.DialogueQuene;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.BlacklistService;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueDetailService;
import com.xiaoma.kefu.service.DialogueService;
import com.xiaoma.kefu.service.StyleService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.AddressUtil;
import com.xiaoma.kefu.util.CookieUtil;
import com.xiaoma.kefu.util.JsonUtil;
import com.xiaoma.kefu.util.ParseURLKeywordUtil;
import com.xiaoma.kefu.util.StudyMapUtil;

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
	
	@Autowired
	private BlacklistService blacklistService;
	
	@Autowired
	private StyleService styleService;

	private Logger logger = Logger.getLogger(DialogueController.class);
	
	/**
	 * 获取转接用户列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "switchList.action", method = RequestMethod.GET)
	public String switchList(HttpServletRequest request, HttpServletResponse response,HttpSession session, Model model, Long customerId) {

		User user = (User) session.getAttribute(CacheName.USER);
		List<String> strIds = JedisTalkDao.getSwitchList();
		List<Integer> userIds = JsonUtil.convertString2Integer(strIds);
		if(CollectionUtils.isNotEmpty(userIds) && user!= null){
			userIds.remove(user.getId());
		}
		List<User> users = userService.getUsersByIds(userIds);
		
		model.addAttribute("list", users);
		model.addAttribute("user", user);
		model.addAttribute("customerId", customerId);

		return "/dialogue/switch";
	}

	/**
	 * 查看客户历史对话(最新的一次)
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "history.action", method = RequestMethod.GET)
	public String historyList(HttpServletRequest request, HttpServletResponse response,HttpSession session, Model model, Long customerId) {

		List<DialogueDetail> list = dialogueDetailService.getLastRecordsByCustomerId(customerId);
		model.addAttribute("dialogueList", list);

		return "/dialogue/historyRecord";
	}
	
	/**
	 * 获取当前对话列表
	 * 
	 * @param request
	 * @param response
	 * @param ccnId
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "receiveList.action", method = RequestMethod.GET)
	public String customerList(HttpServletRequest request,HttpServletResponse response, String ccnId, Model model)
			throws IOException, NoSuchFieldException, SecurityException {
		
		List<DialogueQuene> list = new ArrayList<DialogueQuene>();
		List<String> ccnIds = JedisTalkDao.getCcnReceiveList(ccnId);
		
		if(CollectionUtils.isNotEmpty(ccnIds)){
			
			Map<String, Long> ccnIdCustomerMap = new HashMap<String, Long>();
			
			for (String cId : ccnIds) {
				String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, cId);
				if (customerId != null) {
					ccnIdCustomerMap.put(cId, Long.parseLong(customerId));
				}
			}
			List<Long> customerIds = new ArrayList<Long>(ccnIdCustomerMap.values());
			List<Customer> customers = new ArrayList<Customer>();
			Map<Long, Customer> customerMap = new HashMap<Long, Customer>();
			if(CollectionUtils.isNotEmpty(customerIds)){
				customers = customerService.findByIds(customerIds);
				customerMap = StudyMapUtil.convertList2Map(customers, Customer.class.getDeclaredField("id"));
			}
			
			for (String cId : ccnIds) {
				DialogueQuene dialogueQuene = new DialogueQuene();
				dialogueQuene.setCcnId(cId);
				
				Long customerId = ccnIdCustomerMap.get(cId);
				Customer customer = customerMap.get(customerId);
				if(customer != null){
					dialogueQuene.setCustomer(customer);
				}
				Long millTime = JedisTalkDao.getCcnReceiveScore(ccnId,cId);
				if(millTime != null){
					dialogueQuene.setEnterTime(new Date(millTime));
				}
				
				list.add(dialogueQuene);
			}
		}
		
		
		model.addAttribute("list", list);

		return "/dialogue/receiveList";

	}

	
	/**
	 * 客户对话框请求页 负责用户的创建与cookie的读写还有聊天历史记录！
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param refer 
	 * @param styleId -风格id
	 * @param btnCode - 按钮id
	 * @param landingPage - 着陆页
	 * @param consultPage - 咨询页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "customerChat.action", method = RequestMethod.GET)
	public String customerChat(HttpServletRequest request,HttpServletResponse response, Model model, String refer,Integer styleId, 
			String btnCode, String landingPage,String consultPage)throws Exception {

		String customerId = CookieUtil.getCustomerIdFromCookie(request);

		Long id = null;
		Boolean isNew = true; //判断是否是新用户标识
		Boolean isForbidden = false;
		
		String firstVisitSource = null;
		if (StringUtils.isNotBlank(customerId)) {
			try {
				id = Long.parseLong(customerId);
				Customer check = customerService.getCustomerById(id);
				if(check != null){
					isNew = false;
				}
			} catch (Exception e) {
				logger.info("this is a new customer!");
			}
		}
		
		if (isNew) {
			
			Customer newCus = new Customer();
			
			// 收集信息创建新客户
			String ip = CookieUtil.getIpAddr(request); //获取ip地址
			newCus.setIp(ip);
			
			String ipInfo = AddressUtil.getAddresses("ip=" + ip, "utf-8");
			newCus.setIpInfo(ipInfo);
			newCus.setStyleId(styleId);
			
			if(styleId != null){
				Style style = styleService.get(styleId);
				if(style != null) newCus.setStyleName(style.getName());
			}
			newCus.setCustomerType(1);
			newCus.setStatus(0); //0-正常 1-删除
			newCus.setFirstLandingPage(landingPage);
			
		    firstVisitSource = ParseURLKeywordUtil.getKeyword(refer); //搜索关键字
			newCus.setFirstVisitSource(firstVisitSource);
			
			id = customerService.insert(newCus);

		} else {
			isForbidden = blacklistService.judgeForbidden(id);
			List<DialogueDetail> list = dialogueDetailService.getLastRecordsByCustomerId(id);
			model.addAttribute("dialogueList", list);
		}
		
		Customer customer = customerService.getCustomerById(id);
		
		//添加临时对话信息
		DialogueInfo dInfo = JedisTalkDao.getDialogueScore(id.toString(),null);
		dInfo.setBtnCode(btnCode);
		dInfo.setConsultPage(consultPage);
		dInfo.setIp(customer.getIp());
		dInfo.setIpInfo(customer.getIpInfo());
		dInfo.setKeywords(firstVisitSource);
		dInfo.setStyleId(styleId);
		dInfo.setLandingPage(landingPage);
		if(StringUtils.isNotBlank(btnCode)){
			char c = btnCode.charAt(btnCode.length()-1);
			
			if('2' == c || '4' == c){
				dInfo.setOpenType(1);//图标
			}
			if('3' == c || '5' == c){
				dInfo.setOpenType(2);//对话邀请框
			}
			if('4' == c || '5' == c){
				dInfo.setDeviceType(2);//手机
			}
			if('1' == c || '2' == c || '3' == c){
				dInfo.setDeviceType(1);//PC
			}
			
		}
		
		JedisTalkDao.setDialogueInfo(id.toString(), "-1", dInfo);

		// cookie操作
		Cookie cookie = CookieUtil.genCookieByCustomerId(id.toString());

		// 有效期五年
		cookie.setMaxAge(5 * 365 * 24 * 60 * 60);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);

		model.addAttribute("customer", customer);
		model.addAttribute("isForbidden", isForbidden);
		
		
		// 留言框生成规则 --------------开始
		model.addAttribute("replyWay",dialogueService.findReplyWayList());
		model.addAttribute("replyType",dialogueService.findMessageObject());
		model.addAttribute("infoList",dialogueService.findInfoList());
		model.addAttribute("checkInfo",DictMan.getDictItem("d_sys_param", 8));
		
		//获取客服部人员列表
		model.addAttribute("userList",userService.getResultDept(1));
		// 留言框生成规则 ---------------结束

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

}
