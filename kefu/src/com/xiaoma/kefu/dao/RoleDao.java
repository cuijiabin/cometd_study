package com.xiaoma.kefu.dao;

import java.util.List;
import java.util.Map;

import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.util.PageBean;

public interface RoleDao extends BaseDao<Role> {
	/**
	 * User DAO interface
	 * 
	 * @author tanzhipeng
	 * 
	 */

	public void findByCondition(Map<String, String> conditions,
			PageBean<Role> pageBean);

	/**
	 * 获取全部数据
	 * 
	 * @return
	 */
	public Integer getAllRoleCount();

	public List<Role> getRoleOrderById(Integer start, Integer offset);

	public Integer checkRole(Role role);

	public List<Role> findRole();
}
