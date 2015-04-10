package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Role;



public interface RoleDao extends BaseDao<Role>{
	/**
	 * 获取全部数据
	 * @return
	 */
	public Integer getAllRoleCount();

	public List<Role> getRoleOrderById(Integer start, Integer offset);

	public Integer checkRole(Role role);

	public List<Role> findRole();
}
