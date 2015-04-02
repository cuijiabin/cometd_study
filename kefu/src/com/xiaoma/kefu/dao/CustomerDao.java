package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Customer;

/**
 * @author frongji
 * @time 2015年4月1日下午4:55:41
 *
 */
public interface CustomerDao {
    
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
	public Long createNewCustomer(Customer customer);
	
	public boolean updateCustomer(Customer customer);

	public Long getMaxCustomerId();
}
