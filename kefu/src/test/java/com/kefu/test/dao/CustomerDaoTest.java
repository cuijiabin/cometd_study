package com.kefu.test.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.dao.CustomerDao;
import com.xiaoma.kefu.model.Customer;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class CustomerDaoTest {
	@Autowired  
    private CustomerDao  customerDao;
	
	@Test
	public void testCRUD(){
		Customer customer = new Customer();
		customer.setCustomerName("崔佳彬");
		customer.setRemark("test添加");
		
		customerDao.createNewCustomer(customer);
	}
}
