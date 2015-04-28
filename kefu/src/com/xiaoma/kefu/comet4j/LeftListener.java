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
			
			String customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, ccnId);
			Boolean isCustomer = StringUtils.isNotBlank(customerId);
			
			//移除缓存
			if(isCustomer){
			     
				JedisTalkDao.remCcnList( JedisConstant.CUSTOMER_TYPE, ccnId);
				JedisTalkDao.remUserCcnList(customerId, ccnId);
				JedisTalkDao.delCnnUserId(JedisConstant.CUSTOMER_TYPE, ccnId);
				
				//获取通话对话点并删除
				String toUserCcnId = JedisTalkDao.getCcnPassiveId(ccnId);
				JedisTalkDao.delCcnPassiveId(ccnId);
				JedisTalkDao.remCcnReceiveList(toUserCcnId, ccnId);
				
				 DialogueInfo dInfo = JedisTalkDao.getDialogueScore(customerId,toUserCcnId);
			     dInfo.setCloseType(1);
			     JedisTalkDao.setDialogueInfo(customerId, ccnId, dInfo);
			     
				//保存会话
		        String key = JedisConstant.getDialogueListKey(toUserCcnId,ccnId);
		        JedisTalkDao.lpushSaveDialogue(key);
		        
		        //移除等待列表
		        JedisTalkDao.remCustomerWaitSet(ccnId);
				
				logger.info("前端用户customerId："+customerId+" ,退出联接; 通信点id："+ccnId+" ,关联客服通信点id："+toUserCcnId
						+" ,客服id: "+JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, toUserCcnId));
				
				//通知客更新后台列表
				CometEngine engine = (CometEngine) anEvent.getTarget();
		        CometConnection ccn = engine.getConnection(toUserCcnId);
		        
				//通知数据
				NoticeData nd = new NoticeData(Constant.ON_CLOSE, null);
		        engine.sendTo(Constant.CHANNEL, ccn, nd); 
		        
		        
				
			}else{
				String userId = JedisTalkDao.getCnnUserId(JedisConstant.USER_TYPE, ccnId);
				
				JedisTalkDao.remCcnList( JedisConstant.USER_TYPE, ccnId);
				JedisTalkDao.remUserCcnList(userId, ccnId);
				JedisTalkDao.delCnnUserId(JedisConstant.USER_TYPE, ccnId);
				
				List<String> receiveCnnIds = JedisTalkDao.getCcnReceiveList(ccnId);
				for(String rCnnId : receiveCnnIds){
					
					customerId = JedisTalkDao.getCnnUserId(JedisConstant.CUSTOMER_TYPE, rCnnId);
					DialogueInfo dInfo = JedisTalkDao.getDialogueScore(customerId,ccnId);
				    dInfo.setCloseType(2);
				    JedisTalkDao.setDialogueInfo(customerId, ccnId, dInfo);
				     
					JedisTalkDao.delCcnPassiveId(rCnnId);
					//保存会话
			        String key = JedisConstant.getDialogueListKey(ccnId,rCnnId);
			        JedisTalkDao.lpushSaveDialogue(key);
				}
				
				//清空
				String ccnReceiveListKey = JedisConstant.genCcnReceiveListKey(ccnId);
				JedisDao.getJedis().del(ccnReceiveListKey);
				
				logger.info("客服userId："+userId+" ,退出联接; 通信点id："+ccnId+" ,清空接待列表key："+ccnReceiveListKey);
				
				//通知前端用户对话结束！
				NoticeData nd = new NoticeData(Constant.ON_CLOSE, null);
				CometEngine engine = (CometEngine) anEvent.getTarget();
		        for(String rCnnId : receiveCnnIds){
		        	CometConnection ccn = engine.getConnection(rCnnId);
		        	 engine.sendTo(Constant.CHANNEL, ccn, nd);
				}
		        
			}
			
		}
		
		return true;
		
	}
}