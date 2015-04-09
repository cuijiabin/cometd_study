package com.xiaoma.kefu.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.List;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.common.DialogueCache;
import com.xiaoma.kefu.common.DialogueUniqueTag;
import com.xiaoma.kefu.common.DialogueUtil;
import com.xiaoma.kefu.redis.JedisDao;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.util.DesUtil;
import com.xiaoma.kefu.util.PropertiesUtil;

public class WebSocketMI extends MessageInbound {
	
	@Autowired
	private CustomerService customerService;
	
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
		
		//向连接池添加当前的连接对象
		WebSocketMIPool.addMessageInbound(this);
		
	}

	@Override
	protected void onClose(int status) {
		String key = DialogueCache.buildKey(this.uniqueTag);
		List<String> dialogueDetail = JedisDao.getJedis().lrange(key, 0, -1);
		for(String dialogue : dialogueDetail){
			System.out.println(dialogue);
		}
		
		//清除缓存
		JedisDao.getJedis().del(key);
		
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
		
		
		String contentFormat = DialogueUtil.generateMessage(message, this);
		String uniqueTag = this.uniqueTag.getUniqueTag();
		String sendUniqueTag = this.uniqueTag.getSendUniqueTag();
		
		if("getCookie".equals(contentFormat)){
			
			try {
				String id = DesUtil.encrypt(this.uniqueTag.getCustomerId().toString(),PropertiesUtil.getProperties(CacheName.SECRETKEY));
				
				WebSocketMIPool.sendMessageToUser(uniqueTag, id);
				
				return ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//redis操作
		String key = DialogueCache.buildKey(this.uniqueTag);
		String dialogueCache = DialogueCache.getDialogueCache(this.uniqueTag,contentFormat);
		JedisDao.getJedis().lpush(key,dialogueCache);
		
		//向自己发送消息
		WebSocketMIPool.sendMessageToUser(uniqueTag, contentFormat);
		
		//向对方发送消息
		WebSocketMIPool.sendMessageToUser(sendUniqueTag, contentFormat);
	    
	}
}
