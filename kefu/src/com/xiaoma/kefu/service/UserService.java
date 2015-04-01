package com.xiaoma.kefu.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.model.User;


/**
 * User DAO service class
 * @author tanzhipeng
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDaoImpl;

	public User login(String name, String password){
		return userDaoImpl.findUser(name, password);
	}

}
