package com.xiaoma.kefu.comet4j;

import org.apache.commons.lang.StringUtils;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.DropEvent;
import org.comet4j.core.listener.DropListener;

import com.xiaoma.kefu.redis.JedisDao;

/**
 * @description LeftListener
 * @author cuijiabin
 */
public class LeftListener extends DropListener {
	public boolean handleEvent(DropEvent anEvent) {
		CometConnection conn = anEvent.getConn();
		if (conn != null) {
			
			
			
			String value = JedisDao.getJedis().get("1###"+conn.getId());
			if(StringUtils.isNotBlank(value)){
				value = JedisDao.getJedis().get("2###"+conn.getId());
			}
			
			//移除缓存
			JedisDao.getJedis().del("1###"+conn.getId(),"2###"+conn.getId());
			
			
			JoinDTO dto = new JoinDTO(conn.getId(), value);
			dto.setType("down");
			
			//广播
			((CometEngine) anEvent.getTarget()).sendToAll("talker", dto);
		}
		return true;
	}
}