package com.xiaoma.kefu.dao;

import java.util.List;
import java.util.Map;

import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.PageBean;

/**
 * User DAO interface
 * 
 * @author yangxiaofeng
 * 
 */
public interface UserDao extends BaseDao<User> {
	/**
	 * find user by username and password
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public abstract User findUser(String name, String password);

	/**
	 * 查询用户
	 * 
	 * @return
	 */
	public Integer getAllUserCount();

	public List<User> getUsertOrderById(Integer start, Integer offset);

	/**
	 * 条件查询
	 * 
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
	 * 检查用户是否存在
	 * 
	 * @param user
	 * @return
	 */
	public Integer checkUser(User user);

	// 查询部门里的员工
	public abstract List<User> getUsertByDeptId(Integer deptId);

	/**
	 * 分页查询
	 * 
	 * @param conditions
	 * @param pageBean
	 */
	public abstract void findByCondition(Map<String, String> conditions,
			PageBean<User> pageBean);
    /**
     * 查询角色下的员工
     * @param id
     * @return
     */
	public abstract List<User> getUsertByRoleId(Integer id);


}
