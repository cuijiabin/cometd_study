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
			
			String userId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, ccnId);
			
			//移除缓存
			if(StringUtils.isNotBlank(userId)){
				JedisTalkDao.remCcnList( JedisConstant.CUSTOMER_TYPE, ccnId);
				JedisTalkDao.remUserCcnList(userId, ccnId);
				JedisTalkDao.delCnnUserId(JedisConstant.CUSTOMER_TYPE, ccnId);
				
				String opeCcnId = JedisTalkDao.getCcnPassiveId(ccnId);
				JedisTalkDao.remCcnReceiveList(ccnId, opeCcnId);
				JedisTalkDao.decrCurrentReceiveCount(opeCcnId);
				
				JedisTalkDao.delCcnPassiveId(ccnId);
				// 写入cookie
				// DesUtil.encrypt(userId,PropertiesUtil.getProperties(CacheName.SECRETKEY));
				
				//通知客更新后台列表
				CometEngine engine = (CometEngine) anEvent.getTarget();
		        CometConnection ccn = engine.getConnection(opeCcnId);
		        
				//通知数据
				NoticeData nd = new NoticeData(Constant.ON_CLOSE, null);
		        engine.sendTo(Constant.CHANNEL, ccn, nd); 
				
			}else{
				JedisTalkDao.remCcnList( JedisConstant.USER_TYPE, ccnId);
				JedisTalkDao.remUserCcnList(userId, ccnId);
				JedisTalkDao.delCnnUserId(JedisConstant.USER_TYPE, ccnId);
			}
			
		}
		
		return true;
		
	}
}