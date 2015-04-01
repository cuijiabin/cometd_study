package com.xiaoma.kefu.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.CustomerDao;
import com.xiaoma.kefu.dao.HibBaseDao;
import com.xiaoma.kefu.model.Customer;

/**
 * @author frongji
 * @time 2015年4月1日下午4:57:03
 *
 */
@Repository("customerDaoImpl")
public class CustomerDaoImpl extends HibBaseDao<Customer> implements CustomerDao{
	
	@Override
	public Integer getAllCustomerCount(){
		Session session = getSession();
		String hql = "select count(1) from Customer c where c.isDel!=1 ";

	 Query query = session.createSQLQuery(hql);
	 
	 return ((Number)query.uniqueResult()).intValue();
		
 	}
}
