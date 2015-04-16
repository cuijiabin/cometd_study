package com.xiaoma.kefu.thread;

import org.apache.log4j.Logger;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.service.LoginLogService;
import com.xiaoma.kefu.util.StringHelper;

public class AddLoginLogThread extends Thread {
	private Logger logger = Logger.getLogger(AddLoginLogThread.class);
	private Integer userId;
	private String ip;
	public AddLoginLogThread(Integer userId,String ip) {
		this.userId = userId;
		this.ip = ip;
	}
	public void run() {
		try {
			if(userId != null && StringHelper.isNotEmpty(ip)){
				LoginLogService loginLogService = (LoginLogService) SpringContextUtil.getBean("loginLogService");
				loginLogService.addLog(userId,ip);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}
}
