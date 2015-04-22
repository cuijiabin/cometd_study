package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.KeyboardDao;
import com.xiaoma.kefu.model.Keyboard;

@Repository
public class KeyboardDaoImpl extends BaseDaoImpl<Keyboard> implements
		KeyboardDao {
	private static Logger logger = Logger.getLogger(KeyboardDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Keyboard> findByUesrId(Integer id) {
		Session session = getSession();
		String hql = "from Keyboard c where c.userId =" + id;
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Override
	public void deleteByUserId(Integer id) {
	      try{
	  		Session session = getSession();
	  		String hql = "delete from Keyboard b where b.userId =" + id;
	  		Query query = session.createQuery(hql);
	  		query.executeUpdate();

	        }catch(Exception e){
	      	  logger.error(e.getMessage());
	        }
	  	}	

}
