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
	 * 获取全部数据的条数
	 * @return
	 */
	public Integer getAllCustomerCount(Map<String, String> conditions,String beginDate,String endDate, PageBean pageBean);

	public List<Customer> getCustomerOrderById(Integer start, Integer offset);
      
	/**
	 * 添加一条
	 * @param customer
	 * @return
	 */
	public boolean createNewCustomer(Customer customer);
	
	/**
	 * 添加一条
	 * @param customer
	 * @return
	 */
	public Long insert(Customer customer);
	
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
	 * 根据ip获取用户
	 * @param ip
	 * @return
	 */
	public Customer getByIp(String ip);
    


	
	public Long getMaxCustomerId();


   
    
	/**
	 * 查询
	 * @param conditions
	 * @param pageBean
	 */
	public List getCustomerByCon(Map<String, String> conditions,String beginDate,String endDate,PageBean pageBean);
    
	/**
	 * 导出报表
	 * @param conditions
	 * @param beginDate
	 * @param endDate
	 * @param pageBean
	 * @return
	 */
	public List getCustomerByConExl(
			Map<String, String> conditions, String beginDate, String endDate,
			PageBean pageBean);

}
