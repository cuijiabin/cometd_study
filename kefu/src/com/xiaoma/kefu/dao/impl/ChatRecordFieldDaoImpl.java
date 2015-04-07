package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.ChatRecordFieldDao;
import com.xiaoma.kefu.model.ChatRecordField;

/**
 * *********************************
* @Description: 聊天记录结果字段配置	daoImpl
* @author: wangxingfei
* @createdAt: 2015年4月3日上午10:02:34
**********************************
 */
@Repository("chatRecordFieldDaoImpl")
public class ChatRecordFieldDaoImpl extends BaseDaoImpl<ChatRecordField> implements ChatRecordFieldDao {


	@SuppressWarnings("unchecked")
	@Override
	public List<ChatRecordField> findDisplay() {
		Session session = getSession();
		String hql = "from ChatRecordField cr where cr.isDisplay = 1 order by cr.sortId ";
		Query query = session.createQuery(hql);
		return	query.list();
	}

	@Override
	public ChatRecordField findById(Integer id) {
		return findById(ChatRecordField.class, id);
	}

	@Override
	public int updateIsDisplay(ChatRecordField crf) {
		Session session = getSession();
	    Query query = session.createQuery("update ChatRecordField cr set cr.isDisplay = :isDisplay where id = :id "); 
	    query.setInteger("isDisplay", crf.getIsDisplay());
	    query.setInteger("id", crf.getId());
	    return query.executeUpdate();  
	}

	@Override
	public List<ChatRecordField> findAll() {
		return findAll(ChatRecordField.class);
	}

}
