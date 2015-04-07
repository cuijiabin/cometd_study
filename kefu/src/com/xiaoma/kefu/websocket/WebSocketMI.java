package com.xiaoma.kefu.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import com.xiaoma.kefu.common.DialogueUniqueTag;

public class WebSocketMI extends MessageInbound {
	
	
	
	//当前连接的用户
	private final DialogueUniqueTag uniqueTag;
	public DialogueUniqueTag getUniqueTag() {
		return uniqueTag;
	}
	
	public WebSocketMI(DialogueUniqueTag uniqueTag) {
		this.uniqueTag = uniqueTag;
	}
	


	//建立连接的触发的事件
	@Override
	protected void onOpen(WsOutbound outbound) {
		System.out.println("打开连接");
		
		//向连接池添加当前的连接对象
		WebSocketMIPool.addMessageInbound(this);
		
	}

	@Override
	protected void onClose(int status) {
		// 触发关闭事件，在连接池中移除连接
		WebSocketMIPool.removeMessageInbound(this);
		
	}

	@Override
	protected void onBinaryMessage(ByteBuffer message) throws IOException {
		throw new UnsupportedOperationException("Binary message not supported.");
	}

	//客户端发送消息到服务器时触发事件
	@Override
	protected void onTextMessage(CharBuffer message) throws IOException {
		System.out.println("接收消息！");
		
		
//		String contentFormat = ContentFormatUtil.generateMessage(message, this);
//		
//		//向自己发送消息
//		WebSocketMIPool.sendMessageToUser(this.customer.getUniqueTag(), contentFormat);
//		
//		//向对方发送消息
//		WebSocketMIPool.sendMessageToUser(this.customer.getToUniqueTag(), contentFormat);
	    
	}
}
