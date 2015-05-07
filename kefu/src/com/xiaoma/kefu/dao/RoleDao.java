package com.xiaoma.kefu.dao;

import java.util.List;
import java.util.Map;

import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.util.PageBean;
/**
 * 
 * @author yangxiaofeng
 *
 */
public interface RoleDao extends BaseDao<Role> {

    /**
     * 分页查询列表
     * @param conditions
     * @param pageBean
     */
	public void findByCondition(Map<String, String> conditions,
			PageBean<Role> pageBean);

/**
 * 老式分页查询列表
 * @return
 */
	public Integer getAllRoleCount();
	public List<Role> getRoleOrderById(Integer start, Integer offset);
	
	/**
	 * 检验是否有相同的角色存在
	 * @param role
	 * @return
	 */
	public Integer checkRole(Role role);
	
	/**
	 * 查询出所有的角色
	 * @return
	 */
	public List<Role> findRole();
}
