package com.xiaoma.kefu.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.DialogueDao;
import com.xiaoma.kefu.model.Dialogue;

/**
 * *********************************
* @Description: 聊天记录结果字段配置	daoImpl
* @author: wangxingfei
* @createdAt: 2015年4月3日上午10:02:34
**********************************
 */
@Repository("dialogueDaoImpl")
public class DialogueDaoImpl extends BaseDaoImpl<Dialogue> implements DialogueDao {
	
	/**
	 * 更新为删除状态, 用于逻辑删除
	* @Description: TODO
	* @param dialogue
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	@Override
	public int update2Del(Dialogue dialogue) {
		Session session = getSession();
	    Query query = session.createQuery("update Dialogue t set t.isDel = 1 where id = :id "); 
	    query.setLong("id", dialogue.getId());
	    return query.executeUpdate();  
	}
	
	/**
	 * 更新为正常状态,用于回收站还原
	* @Description: TODO
	* @param dialogue
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	@Override
	public int update2Restore(Dialogue dialogue) {
		Session session = getSession();
	    Query query = session.createQuery("update Dialogue t set t.isDel = 0 where id = :id "); 
	    query.setLong("id", dialogue.getId());
	    return query.executeUpdate();  
	}

}
