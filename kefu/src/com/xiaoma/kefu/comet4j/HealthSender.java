package com.xiaoma.kefu.comet4j;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

/**
 * @description HealthSender
 * @author cuijiabin
 */
public class HealthSender implements Runnable {
	private static final CometEngine engine = CometContext.getInstance()
			.getEngine();
	private static final HealthDTO healthDto = new HealthDTO();
	private static final long startup = System.currentTimeMillis();

	public void run() {
		while (true) {
			try {
				Thread.sleep(5000L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			long totalMemory = Runtime.getRuntime().totalMemory();
			long freeMemory = Runtime.getRuntime().freeMemory();
			long maxMemory = Runtime.getRuntime().maxMemory();
			long usedMemory = totalMemory - freeMemory;
			Integer connectorCount = Integer.valueOf(engine.getConnections()
					.size());
			healthDto.setConnectorCount(connectorCount.toString());
			healthDto.setFreeMemory(freeMemory);
			healthDto.setMaxMemory(maxMemory);
			healthDto.setTotalMemory(totalMemory);
			healthDto.setUsedMemory(usedMemory);
			long dif = System.currentTimeMillis() - startup;
			long day_mill = 86400000L;
			long hour_mill = 3600000L;
			Long day = Long.valueOf(dif / day_mill);
			Long hour = Long.valueOf(dif % day_mill / hour_mill);
			String str = day.toString() + "天 " + hour.toString() + "小时";
			healthDto.setStartup(str);
			engine.sendToAll("talker", healthDto);
		}
	}
}