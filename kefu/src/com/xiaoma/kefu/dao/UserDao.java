package com.xiaoma.kefu.dao;

import com.xiaoma.kefu.model.User;


/**
 * User DAO interface
 * @author tanzhipeng
 *
 */
public interface UserDao extends BaseDao<User>{
	/**
	 * find user by username and password
	 * @param name
	 * @param password
	 * @return
	 */
	public abstract User findUser(String name, String password);
	
}
