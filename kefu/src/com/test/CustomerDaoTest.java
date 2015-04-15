package com.test;

import org.junit.Ignore;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.dao.CustomerDao;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.redis.JedisDao;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class CustomerDaoTest {
	@Autowired  
    private CustomerDao  customerDao;
	
	@Test
	public void testCRUD(){
//		Customer customer = new Customer();
//		customer.setCustomerName("崔佳彬");
//		customer.setRemark("test添加");
//		
//		customerDao.createNewCustomer(customer);
		
//		Customer customer = customerDao.getById(1L);
//		
//		System.out.println(customer.getRemark());
//		
//		JedisDao.setKOT("customer:2", customer, 600);
		Customer customer = customerDao.findById(Customer.class,1L);
		
		System.out.println(customer.getRemark());
		
		JedisDao.setKOT("customer:2", customer, 600);
	}
	
	@Test
	public void testJedisCRUD(){
		
//		Customer customer = (Customer) JedisDao.getObject("customer:2");
//		
//		System.out.println(customer.getRemark());
		
//		List<Customer> list = customerDao.getCustomerByConditions(null, null, "你妹", null, 3L);
//		System.out.println(list);
		
	}
}
