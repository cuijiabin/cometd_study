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

	@Override
	public int updateIsDisplay(ChatRecordField crf) {
		Session session = getSession();
	    Query query = session.createQuery("update ChatRecordField cr set cr.isDisplay = :isDisplay where id = :id "); 
	    query.setInteger("isDisplay", crf.getIsDisplay());
	    query.setInteger("id", crf.getId());
	    return query.executeUpdate();  
	}
	
	/**
	* @Description: 获取默认展示的字段
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ChatRecordField> findCommonDefault() {
		Session session = getSession();
		String hql = "from ChatRecordField cr where cr.userId = 1 and cr.isDefault = 1 order by cr.sortId ";
		Query query = session.createQuery(hql);
		return	query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChatRecordField> findCommon() {
		Session session = getSession();
		String hql = "from ChatRecordField cr where cr.userId = 1 order by cr.sortId ";
		Query query = session.createQuery(hql);
		return	query.list();
	}

	@Override
	public int deleteByUserId(Integer userId) {
		Session session = getSession();
	    Query query = session.createQuery("delete ChatRecordField cr where cr.userId = :userId "); 
	    query.setInteger("userId", userId);
	    return query.executeUpdate();  
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChatRecordField> findByUserId(Integer userId) {
		Session session = getSession();
		String hql = "from ChatRecordField cr where cr.userId = :userId order by cr.sortId ";
		Query query = session.createQuery(hql);
		query.setInteger("userId", userId);
		return	query.list();
	}
	
	/**
	 * 更新是否显示
	* @Description: TODO
	* @param crf
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月12日
	 */
	@Override
	public int updateDisplay(ChatRecordField crf){
		Session session = getSession();
	    Query query = session.createQuery("update ChatRecordField t set t.isDisplay = :isDisplay,t.updateDate=:updateDate where id = :id "); 
	    query.setInteger("isDisplay", crf.getIsDisplay());
	    query.setDate("updateDate", crf.getUpdateDate());
	    query.setInteger("id", crf.getId());
	    return query.executeUpdate();  
	}


}
