package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.RemindTypeDao;
import com.xiaoma.kefu.model.RemindType;

@Repository
public class RemindTypeDaoImpl extends BaseDaoImpl<RemindType> implements
		RemindTypeDao {
	private static Logger logger = Logger.getLogger(RemindTypeDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<RemindType> findRemindByUesrId(Integer id) {
		Session session = getSession();
		String hql = "from RemindType c where c.userId =" + id;
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Override
	public void deleteRemindByUserId(Integer id) {
	      try{
	  		Session session = getSession();
	  		String hql = "delete from RemindType b where b.userId =" + id;
	  		Query query = session.createQuery(hql);
	  		query.executeUpdate();

	        }catch(Exception e){
	      	  logger.error(e.getMessage());
	        }
	  	}	

}
