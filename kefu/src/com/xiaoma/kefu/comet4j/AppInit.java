package com.xiaoma.kefu.comet4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

import com.xiaoma.kefu.thread.SaveDialogueThread;

/**
 * @description AppInit
 * @author cuijiabin
 */
public class AppInit implements ServletContextListener {

	/**
	 * 初始化默认通道
	 */

	public void contextInitialized(ServletContextEvent arg0) {
		CometContext cc = CometContext.getInstance();
		CometEngine engine = cc.getEngine();
		cc.registChannel(Constant.CHANNEL);
        
		//加入时监听
		engine.addConnectListener(new JoinListener());
		
		//删除时监听
		engine.addDropListener(new LeftListener());
        
		//全程监听 保存对话内容
		Thread saveDialogueThread = new Thread(new SaveDialogueThread(), "SaveDialogueThread");
		saveDialogueThread.setDaemon(true);
		saveDialogueThread.start();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}