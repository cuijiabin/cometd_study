package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Role;



public interface RoleDao {
	/**
	 * 获取全部数据
	 * @return
	 */
	public Integer getAllRoleCount();

	public List<Role> getRoleOrderById(Integer start, Integer offset);
	/**
	 * create a new role
	 * @param user
	 * @return
	 */
	public abstract boolean createNewRole(Role role);

	/**
	 * get Role by id
	 * @param id
	 * @return
	 */
	public Role getRoleByRoleId(Integer id);

	/**
	 * 修改
	 * @param role
	 * @return
	 */
	public boolean updateUser(Role role);
	
	  /**
     * 删除
     * @param id
     * @return
     */
	public boolean deleteRoleById(Integer id);

	public Integer checkRole(Role role);

	public List<Role> findRole();
}
