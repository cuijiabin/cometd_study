package com.xiaoma.kefu.dao;

import java.util.List;

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
	
	/**
	 * create a new user
	 * @param user
	 * @return
	 */
	public abstract boolean createNewUser(User user);
	
	 /**
	  * 查询用户(冯榕基)
	  * @return
	  */
	public Integer getAllUserCount();

	public List<User> getUsertOrderById(Integer start, Integer offset);
    /**
     *  条件查询
     * @param start
     * @param offset
     * @param userName
     * @param phone
     * @return
     */
	public List<User> getUserByuserNameOrPhone(Integer start, Integer offset,
			String userName, String phone);
	
	public Integer getUserCountByuserNameOrPhone(String userName, String phone);
   
	/**
	 * 根据id查询一条
	 * @param id
	 * @return
	 */
	public  User getUserByUserId(Integer id);
    /**
     * 修改
     * @param user
     * @return
     */
	public boolean updateUser(User user);
    /**
     * 删除
     * @param id
     * @return
     */
	public boolean deleteUserById(Integer id);

	public Integer checkUser(User user);

	//查询部门里的员工
	public abstract Integer getDeptUserCount(Integer deptId);
	public abstract List<User> getUsertByDeptId(Integer start,Integer pageRecorders, Integer deptId);

	
}
