package com.xiaoma.kefu.websocket;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.xiaoma.kefu.common.DialogueUniqueTag;

public class WebSocketMIPool {

	//保存连接的MAP容器
	private static final Map<String,WebSocketMI > connections = new ConcurrentHashMap<String,WebSocketMI>();
	
	/**
	 * 向连接池中添加连接
	 * @param inbound
	 */
	public static void addMessageInbound(WebSocketMI inbound){
		
		DialogueUniqueTag uniqueTag = inbound.getUniqueTag();
		String tag = uniqueTag.getUniqueTag();
		
		if(uniqueTag.getType() == DialogueUniqueTag.USER_TYPE){
			String sendTag = uniqueTag.getSendUniqueTag();
			WebSocketMI cus = connections.get(sendTag);
			cus.getUniqueTag().setUserId(uniqueTag.getUserId());
		}
		
		//用户进入连接池
		connections.put(tag, inbound);
	}
	
	/**
	 * 获取所有的在线用户ip地址
	 */
	public static Set<String> getOnlineUser(){
		return connections.keySet();
	}
	
	/**
	 * 用户移除连接
	 * 
	 * @param inbound
	 */
	public static void removeMessageInbound(WebSocketMI inbound){
		
		DialogueUniqueTag uniqueTag = inbound.getUniqueTag();
		String tag = uniqueTag.getUniqueTag();
		
		connections.remove(tag);
	}
	
	/**
	 * 向特定的用户发送数据
	 * 
	 * @param user 
	 * @param message
	 */
	public static void sendMessageToUser(String user,String message){
		try {
			
			WebSocketMI inbound = connections.get(user);
			if(inbound != null){
				inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
