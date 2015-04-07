package com.xiaoma.kefu.websocket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import com.xiaoma.kefu.common.DialogueUniqueTag;
import com.xiaoma.kefu.common.DialogueUtil;

//如果要接收浏览器的ws://协议的请求就必须实现WebSocketServlet这个类
@WebServlet(urlPatterns = { "/message/*"})
public class WebSocketMessageServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;
	
	public static int ONLINE_USER_COUNT	= 1;
	
	

	//跟平常Servlet不同的是，需要实现createWebSocketInbound，在这里初始化自定义的WebSocket连接对象
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,HttpServletRequest request) {
    	
    	
    	DialogueUniqueTag uniqueTag = DialogueUtil.genUniqueTag(request);
    	
    	System.out.println("进入/message/");
    	
        return new WebSocketMI(uniqueTag);
    }
}
