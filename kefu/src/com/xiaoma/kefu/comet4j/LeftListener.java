package com.xiaoma.kefu.comet4j;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.DropEvent;
import org.comet4j.core.listener.DropListener;

import com.xiaoma.kefu.redis.JedisConstant;
import com.xiaoma.kefu.redis.JedisDao;
import com.xiaoma.kefu.redis.JedisTalkDao;

/**
 * @description LeftListener
 * @author cuijiabin
 */
public class LeftListener extends DropListener {
	
	private Logger logger = Logger.getLogger(LeftListener.class);
	
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
				
				logger.info("前端用户customerId："+userId+" ,退出联接; 通信点id："+ccnId+" ,关联客服通信点id："+opeCcnId
						+" ,客服id: "+JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, opeCcnId));
				// 写入cookie
				// DesUtil.encrypt(userId,PropertiesUtil.getProperties(CacheName.SECRETKEY));
				
				//通知客更新后台列表
				CometEngine engine = (CometEngine) anEvent.getTarget();
		        CometConnection ccn = engine.getConnection(opeCcnId);
		        
				//通知数据
				NoticeData nd = new NoticeData(Constant.ON_CLOSE, null);
		        engine.sendTo(Constant.CHANNEL, ccn, nd); 
				
			}else{
				userId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, ccnId);
				JedisTalkDao.remCcnList( JedisConstant.USER_TYPE, ccnId);
				JedisTalkDao.remUserCcnList(userId, ccnId);
				JedisTalkDao.delCnnUserId(JedisConstant.USER_TYPE, ccnId);
				
				
				List<String> receiveCnnIds = JedisTalkDao.getCcnReceiveList(ccnId);
				for(String rCnnId : receiveCnnIds){
					JedisTalkDao.delCcnPassiveId(rCnnId);
				}
				//清空
				String ccnReceiveListKey = JedisConstant.genCcnReceiveListKey(ccnId);
				String currentReceiveCountKey = JedisConstant.genCurrentReceiveCountKey(ccnId);
				JedisDao.getJedis().del(ccnReceiveListKey,currentReceiveCountKey);
				
				logger.info("客服userId："+userId+" ,退出联接; 通信点id："+ccnId+" ,清空接待列表key："+ccnReceiveListKey
						+" ,清空接待列表数量key: "+currentReceiveCountKey);
			}
			
		}
		
		return true;
		
	}
}