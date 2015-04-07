package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.CustomerDao;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.util.StringHelper;

/**
 * @author frongji
 * @time 2015年4月1日下午4:57:03
 *
 */
@Repository("customerDaoImpl")
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao{
	
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
	    * 条件查询
	    */
		
		@SuppressWarnings("unchecked")
		@Override	
	    public List<Customer> getCustomerByConditions(Integer start, Integer offset ,String customerName,String phone) {
			
			//参数检查
			start = (start == null)? 0 :start;
			offset = (offset == null)? 20 :offset;
			
			Session session = getSession();
			
			String hql = "from Customer c where 1=1 ";
			if(StringHelper.isNotEmpty(customerName)){
				hql += " and c.customerName like '"+"%"+customerName+"%"+"'";
			}
			if(StringHelper.isNotEmpty(phone)){
				hql += " and c.phone like '"+"%"+phone+"%"+"'";
			}
			Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
			return (List<Customer>) query.list();
		}

	
	/**
	 * 添加一条
	 */
	@Override
	public boolean createNewCustomer(Customer customer){
		
		try {
			 add(customer);
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
	public boolean updateCustomer(Customer customer) {
		try {
			update(customer);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	


	
    /**
     * 查询一条
     */
	@Override
	public Customer getCustomerById(Long id) {
		if(id==null)
		{
			return null;
		}
		return findById(Customer.class,id);
	}

	@Override
	public Long getMaxCustomerId() {
		Session session = getSession();
		String hql = "select max(id) from Customer  ";

		Query query = session.createSQLQuery(hql);

		return ((Number) query.uniqueResult()).longValue();
	}
}
