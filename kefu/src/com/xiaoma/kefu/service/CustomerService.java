package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.CustomerDao;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author frongji
 * @time 2015年4月1日下午5:33:36
 *
 */

@Service
public class CustomerService {
   
	@Autowired
	private CustomerDao customerDaoImpl;
	
	/**
	 * 条件查询
	 */
   public PageBean<Customer> getResultNameOrPhone(Integer currentPage,Integer pageRecorders,String customerName ,String phone){
	
	Integer totalCount = customerDaoImpl.getAllCustomerCount();
	PageBean<Customer> result = new PageBean<Customer>();
	
	result.setCurrentPage(currentPage);
	result.setPageRecorders(pageRecorders);
	result.setTotalRows(totalCount);
	
	Integer start = result.getStartRow();
	
	  List<Customer> list =customerDaoImpl.getCustomerByConditions(start,pageRecorders,customerName ,phone);
	result.setObjList(list);
	
	return result;
    }

		/**
		 * 添加
		 */
		public boolean createNewCustomer(Customer customer){
		   return customerDaoImpl.createNewCustomer(customer);
		}
		
		
		/**
		* 修改
		*/
		public boolean updateCustomer(Customer customer){
		  return customerDaoImpl.updateCustomer(customer); 
		}
		
		/**
		 * 删除
		 */
		public boolean deleteCustomerById(Long id){
		boolean flag = true;
		Customer customer = customerDaoImpl.getCustomerById(id);
		if (customer!=null) {
		customer.setStatus(0);
		flag = customerDaoImpl.updateCustomer(customer);
		   }
		    return flag;
		  }

	/**
	 * 查询一条
	 */
		public Customer getCustomerById(long id){
			return customerDaoImpl.getCustomerById(id);
		}
}
