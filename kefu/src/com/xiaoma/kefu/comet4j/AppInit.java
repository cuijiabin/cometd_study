package com.xiaoma.kefu.comet4j;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.thread.SaveDialogueThread;

/**
 * @description AppInit
 * @author cuijiabin
 */
public class AppInit implements ServletContextListener {
	
	private Logger logger = Logger.getLogger(AppInit.class);
	
	private UserService userService = (UserService) SpringContextUtil.getBean("userService");

	/**
	 * 初始化默认通道
	 * 
	 * 1.进入时监听 JoinListener
	 * 2.断开时监听 LeftListener
	 * 3.清理与设置缓存
	 * 4.保存对话信息线程启动
	 */

	public void contextInitialized(ServletContextEvent arg0) {
		CometContext cc = CometContext.getInstance();
		CometEngine engine = cc.getEngine();
		cc.registChannel(Constant.CHANNEL);
        
		//加入时监听
		engine.addConnectListener(new JoinListener());
		
		//删除时监听
		engine.addDropListener(new LeftListener());
		
		//启动时先默认删除所有缓存
		logger.info("启动时先默认删除所有缓存");
		CacheMan.removeAll();
		
		//添加用户最大接待量缓存
		List<User> users = userService.getAll();
		for(User user : users){
			if(user.getMaxListen() != null){
				JedisTalkDao.setMaxReceiveCount(user.getId().toString(), user.getMaxListen());
			}
		}
		
		//全程监听 保存对话内容
		Thread saveDialogueThread = new Thread(new SaveDialogueThread(), "SaveDialogueThread");
		saveDialogueThread.setDaemon(true);
		saveDialogueThread.start();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}