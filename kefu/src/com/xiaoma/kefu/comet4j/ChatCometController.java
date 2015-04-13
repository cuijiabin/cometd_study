package com.xiaoma.kefu.comet4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisDao;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.CustomerService;


@Controller
@RequestMapping(value = "chat")
public class ChatCometController {
	
	@Autowired
	private CustomerService customerService;
	
	private static final CometContext context = CometContext.getInstance();
	
	@RequestMapping(value = "talk.action", method = RequestMethod.POST)
	public void talk(HttpServletRequest request,HttpServletResponse response, String cmd) throws IOException{
	    CometEngine engine = context.getEngine();
	    

		if ("talk".equals(cmd)) {
			String id = request.getParameter("id");
			String name = JedisDao.getJedis().get("1###"+id);
			if(StringUtils.isBlank(name)){
				name = JedisDao.getJedis().get("2###"+id);
			}
			String text = request.getParameter("text");
			TalkDTO dto = new TalkDTO(id, name, text);
			
			CometConnection ccn = engine.getConnection(id);
			
			System.out.println(text);
			engine.sendTo("talker", ccn, dto);
            
			return;
		}

	}
	
	@RequestMapping(value = "list.action", method = RequestMethod.GET)
	public void list(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		CometEngine engine = context.getEngine();
		
		String id = request.getParameter("id");
		String userId = JedisTalkDao.getCurrentUserId(JedisConstant.CUSTOMER_TYPE, id);
			
			CometConnection ccn = engine.getConnection(id);
			
			System.out.println(userId);
			engine.sendTo("talker", ccn, userId);
			
	}
	
	@RequestMapping(value = "customerList.action", method = RequestMethod.GET)
	public void customerList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		CometEngine engine = context.getEngine();
		
		String id = request.getParameter("id");
		String userId = JedisTalkDao.getCurrentUserId(JedisConstant.USER_TYPE, id);
		
		List<String> customerIds = JedisTalkDao.getReceiveList(Integer.valueOf(userId));
			
		CometConnection ccn = engine.getConnection(id);
			
		System.out.println(customerIds);
		List<Long> ids = new ArrayList<Long>();
		for(String customerId : customerIds){
			ids.add(Long.parseLong(customerId));
		}
		
		List<Customer> customers = customerService.findByIds(ids);
		NoticeData nd = new NoticeData(Constant.LIST, customers);
		engine.sendTo("talker", ccn, nd);
			
	}
}
