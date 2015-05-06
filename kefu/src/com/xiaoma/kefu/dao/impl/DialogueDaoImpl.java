package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.DialogueDao;
import com.xiaoma.kefu.model.Dialogue;
import com.xiaoma.kefu.service.DialogueService;

/**
 * *********************************
* @Description: 对话信息	daoImpl
* @author: wangxingfei
* @createdAt: 2015年4月3日上午10:02:34
**********************************
 */
@Repository("dialogueDaoImpl")
public class DialogueDaoImpl extends BaseDaoImpl<Dialogue> implements DialogueDao {
	private Logger logger = Logger.getLogger(DialogueDaoImpl.class);
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
	public List<Dialogue> findDialogByCustomerId(Long customerId){
		try {
			Session session = getSession();
		    Query query = session.createQuery("from Dialogue a where a.customerId = :customerId  order by a.beginDate desc"); 
		    query.setLong("customerId", customerId);
		    return query.list();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	@Override
	public Dialogue getLastBycustomerIdAndUserId(Long customerId, Integer userId) {
		Session session = getSession();
	    String hql = "from Dialogue d where d.customerId =:customerId and d.userId =:userId order by d.endDate desc";
		Query query = session.createQuery(hql);
		query.setLong("customerId", customerId);
		query.setInteger("userId", userId);
		query.setMaxResults(1);
		
		return (Dialogue) query.uniqueResult();
	}

}
