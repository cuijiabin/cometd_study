package com.xiaoma.kefu.dao.impl;

import java.io.Serializable;
import java.util.List;

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
		String hql = "select count(1) from Customer  ";

	 Query query = session.createSQLQuery(hql);
	 
	 return ((Number)query.uniqueResult()).intValue();
		
 	}
	
	@SuppressWarnings("unchecked")
	@Override	
    public List<Customer> getCustomerOrderById(Integer start, Integer offset) {
		
		//参数检查
		start = (start == null)? 0 :start;
		offset = (offset == null)? 20 :offset;
		
		Session session = getSession();
		String hql = "from Customer limit order by id asc";
		Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
		
		return (List<Customer>) query.list();
	}
	
	/**
	 * 添加一条
	 */
	@Override
	public Integer createNewCustomer(Customer customer){
		
		try {
			Serializable id = add(customer);
			return (Integer)id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * 修改
	 * @param 
	 * @return
	 */
	@Override
	public boolean updateCustomer(Customer customer) {
		try {
			update(customer);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	


	

	
}
