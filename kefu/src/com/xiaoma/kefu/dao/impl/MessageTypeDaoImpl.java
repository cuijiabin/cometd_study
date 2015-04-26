package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.MessageTypeDao;
import com.xiaoma.kefu.model.MessageType;

/**
 * @author frongji
 * @time 2015年4月8日下午3:40:09
 *
 */
@Repository("messageTypeDaoImpl")
public class MessageTypeDaoImpl extends BaseDaoImpl<MessageType> implements MessageTypeDao {
  
	@Override
	public List<MessageType> findTree(int typeId,int userId) {
		Session session = getSession();
		String hql="";
		if(typeId==1){
			hql = "from MessageType m where m.typeId="+typeId+"order by sortId asc ";
		}else{
			hql = "from MessageType m where m.typeId="+typeId+" and userId="+userId+"order by sortId asc ";
		}
		Query query = session.createQuery(hql);
		return query.list();
	}
	
    /**
     * 查询一条
     */
	@Override
	public MessageType getMessageTypeById(Integer id) {
		if(id==null)
		{
			return null;
		}
		return findById(MessageType.class,id);
	}
	
	/**
	 * 添加一条
	 */
	@Override
	public boolean createNewMessageType(MessageType messageType){
		
		try {
			 add(messageType);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * 修改
	 * @param 
	 * @return
	 */
	@Override
	public boolean updateMessageType(MessageType messageType) {
		try {
			update(messageType);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 删除
	 */
	@Override
	public boolean deleteMessageTypeById(Integer id){
		MessageType messageType = this.getMessageTypeById(id);
		try {	
		delete(messageType);
		return true;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	/**
	 * 查询是否有子节点
	 */
	@Override
	public Integer checkChild(Integer id ){
		Session session = getSession();
		StringBuffer hqlBuffer = new StringBuffer(
				"select count(m.pId) from MessageType m  where  m.pId="+id+"  " );
	  Query query = session.createQuery(hqlBuffer.toString());
	  Object obj = query.uniqueResult();
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 查询子节点排序值为最大的 序号
	 */
	@Override
	public Integer checkChildMax(Integer id,Integer typeId ){
		Session session = getSession();

		String hql="SELECT MAX(m.sortId)  FROM message_type m  WHERE 1=1 AND m.pId = "+id+" AND m.typeId ="+typeId;
		SQLQuery sqlQuery = session.createSQLQuery(hql);

		Integer count = (Integer)sqlQuery.uniqueResult();
		return count;

	}

}
