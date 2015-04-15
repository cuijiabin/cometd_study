package com.xiaoma.kefu.thread;

import org.apache.log4j.Logger;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.service.LoginLogService;

public class UpdateLoginLogThread extends Thread {
	private Logger logger = Logger.getLogger(UpdateLoginLogThread.class);
	private Integer userId;
	public UpdateLoginLogThread(Integer userId) {
		this.userId = userId;
	}
	public void run() {
		try {
			if(userId != null){
				LoginLogService loginLogService = (LoginLogService) SpringContextUtil.getBean("loginLogService");
				loginLogService.updateLog(userId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}
}
