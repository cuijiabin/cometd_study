package com.xiaoma.kefu.websocket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import com.xiaoma.kefu.common.DialogueUniqueTag;
import com.xiaoma.kefu.common.DialogueUtil;
import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.service.CustomerService;

//如果要接收浏览器的ws://协议的请求就必须实现WebSocketServlet这个类
@WebServlet(urlPatterns = { "/message/*"},asyncSupported=true)
public class WebSocketMessageServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;
	
	public static int ONLINE_USER_COUNT	= 1;
	
	private CustomerService customerService = (CustomerService)SpringContextUtil.getBean("customerService");
	

	//跟平常Servlet不同的是，需要实现createWebSocketInbound，在这里初始化自定义的WebSocket连接对象
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,HttpServletRequest request) {
    	
    	//spring service 需要手动注入
    	
    	try {
    		
    		//目前还是存在问题！
    		 Customer customer = customerService.genCustomer(request);
    		 System.out.println("customer:"+customer.toString());
    		 
    		 DialogueUniqueTag uniqueTag = DialogueUtil.genUniqueTag(request);
    	     uniqueTag.setCustomerId(customer.getId());
    	     
    	     return new WebSocketMI(uniqueTag);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	return null;
       
    }
}
