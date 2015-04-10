package com.xiaoma.kefu.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.CustomerDao;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;


/**
 * @author frongji
 * @time 2015年4月1日下午4:57:03
 *
 */
@Repository("customerDaoImpl")
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao{
	
	  private static Logger logger   = Logger.getLogger(CustomerDaoImpl.class);
	
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
	    public List<Customer> getCustomerByConditions(Integer start, Integer offset ,String customerName,String phone,Long customerId) {
			
			//参数检查
			start = (start == null)? 0 :start;
			offset = (offset == null)? 20 :offset;
			
			Session session = getSession();
			
			String hql = "from Customer c where 1=1 and c.status<>1";
			if(customerId !=null)
			{
				hql += " and c.id ="+customerId;
			}
			if(StringHelper.isNotEmpty(customerName)){
				hql += " and c.customerName like ' "+"%"+customerName+"%"+"'";// "'"连接的是'
			}
			if(StringHelper.isNotEmpty(phone)){
				hql += " and c.phone like '"+"%"+phone+"%"+"'";
			}
			
			Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
			return (List<Customer>) query.list();
		}
        /**
         * 查询
         * @param conditions
         * @param pageBean
         */
		@Override
		public void findByCondition(Map<String, String> conditions,
				PageBean<Customer> pageBean) {
		      
			List<String> relation = new ArrayList<String>();
			List<Criterion> role = new ArrayList<Criterion>();// 条件
			List<Order> orders = new ArrayList<Order>();// 排序
			if (conditions != null) {
				if (StringHelper.isNotEmpty(conditions.get("customerName"))) {
					role.add(Restrictions.like("customerName",
							"%" + conditions.get("customerName").trim() + "%"));
				}
			
				if (StringHelper.isNotEmpty(conditions.get("userId"))&& !"0".equals(conditions.get("deptId"))) {
					role.add(Restrictions.eq("userId", conditions.get("userId")));
				}
				if (conditions.get("startDate") != null
						&& !conditions.get("startDate").isEmpty()
						&& conditions.get("endDate") != null
						&& !conditions.get("endDate").isEmpty()) {
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {
						role.add(Restrictions.between(
								"createDate",
								format.parse(conditions.get("startDate")
										+ " 0:00:00"),
								format.parse(conditions.get("endDate")
										+ " 23:59:59")));
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage());
					}
				}
			}
			orders.add(Order.asc("createDate"));
			find(Customer.class, relation, role, null, orders, pageBean);
			logger.info("search Customer by conditions!");
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

	@Override
	public Long insert(Customer customer) {
		try {
			Long id = (Long) add(customer);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Customer getByIp(String ip) {
		Session session = getSession();
		String hql = "from Customer c where c.ip= '"+ip+"'";
		Query query = session.createQuery(hql);
		return (Customer) query.uniqueResult();
	}
}
