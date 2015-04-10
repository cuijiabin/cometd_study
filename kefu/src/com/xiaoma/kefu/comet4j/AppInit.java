package com.xiaoma.kefu.comet4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

/**
 * @description AppInit
 * @author cuijiabin
 */
public class AppInit implements ServletContextListener {
	public void contextInitialized(ServletContextEvent arg0) {
		CometContext cc = CometContext.getInstance();
		CometEngine engine = cc.getEngine();
		cc.registChannel("talker");

		engine.addConnectListener(new JoinListener());
		engine.addDropListener(new LeftListener());

		Thread healthSender = new Thread(new HealthSender(), "HealthSender");
		healthSender.setDaemon(true);
		healthSender.start();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}
}