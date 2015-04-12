package com.xiaoma.kefu.comet4j;

import org.apache.commons.lang.StringUtils;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.DropEvent;
import org.comet4j.core.listener.DropListener;

import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisTalkDao;

/**
 * @description LeftListener
 * @author cuijiabin
 */
public class LeftListener extends DropListener {
	public boolean handleEvent(DropEvent anEvent) {
		CometConnection conn = anEvent.getConn();
		if (conn != null) {
			
			String ccnId = conn.getId();
			String userId = JedisTalkDao.getCurrentUserId(JedisConstant.CUSTOMER_TYPE, ccnId);
			
			//移除缓存
			if(StringUtils.isNotBlank(userId)){
				JedisTalkDao.delCurrentUser(JedisConstant.CUSTOMER_TYPE, ccnId);
				JedisTalkDao.delCcnList(JedisConstant.CUSTOMER_TYPE, ccnId);
				
				JedisTalkDao.delUserCcnList(userId, ccnId);
				Integer count = JedisTalkDao.countUserCcnList(userId);
				if(count <= 0){
					JedisTalkDao.delCurrentUserList(JedisConstant.CUSTOMER_TYPE, userId);
				}
				
			}else{
				userId = JedisTalkDao.getCurrentUserId(JedisConstant.USER_TYPE, ccnId);
				JedisTalkDao.delCurrentUser(JedisConstant.USER_TYPE, ccnId);
				JedisTalkDao.delCcnList(JedisConstant.USER_TYPE, ccnId);
				
				JedisTalkDao.delUserCcnList(userId, ccnId);
				Integer count = JedisTalkDao.countUserCcnList(userId);
				//暂时不用
				if(count <= 0){
					JedisTalkDao.delCurrentUserList(JedisConstant.USER_TYPE, userId);
				}
			}
			
			//广播
			CometEngine engine = (CometEngine) anEvent.getTarget();
			//TODO
		}
		return true;
	}
}