package com.xiaoma.kefu.dao;

import java.util.List;
import java.util.Map;

import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author frongji
 * @time 2015年4月1日下午4:55:41
 *
 */
public interface CustomerDao extends BaseDao<Customer>{
    
	/**
	 * 获取全部数据
	 * @return
	 */
	public Integer getAllCustomerCount();

	public List<Customer> getCustomerOrderById(Integer start, Integer offset);
      
	/**
	 * 添加一条
	 * @param customer
	 * @return
	 */
	public boolean createNewCustomer(Customer customer);
	/**
	 * 修改一条
	 * @param customer
	 * @return
	 */
	public boolean updateCustomer(Customer customer);

	/**
	 * 查询一条
	 * @param id
	 * @return
	 */
	public Customer getCustomerById(Long id);
    
	/**
	 * 条件查询
	 * @param start
	 * @param offset
	 * @param customerName
	 * @param phone
	 * @return
	 */
	public List<Customer> getCustomerByConditions(Integer start, Integer offset,
			String customerName, String phone ,Long customerId);

	
	public Long getMaxCustomerId();
   
	/**
	 * 查询
	 * @param conditions
	 * @param pageBean
	 */
	public void findByCondition(Map<String, String> conditions,
			PageBean<Customer> pageBean);
}
