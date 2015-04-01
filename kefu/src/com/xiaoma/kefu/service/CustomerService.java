package com.xiaoma.kefu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.CustomerDao;

/**
 * @author frongji
 * @time 2015年4月1日下午5:33:36
 *
 */

@Service
public class CustomerService {
   
	@Autowired
	private CustomerDao customerDaoImpl;
}
