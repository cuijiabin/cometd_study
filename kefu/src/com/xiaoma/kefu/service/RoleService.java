package com.xiaoma.kefu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.RoleDao;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.util.PageBean;

/**
 * 
 * @author yangxiaofeng
 *
 */
@Service
public class RoleService {
	@Autowired RoleDao roleDaoImpl;
	
	/**
	 * 查询所有
	 * @param currentPage
	 * @param pageRecorders
	 * @return
	 */
	public PageBean<Role> getResult(Integer currentPage,
			Integer pageRecorders) {

		Integer totalCount = roleDaoImpl.getAllRoleCount();
		PageBean<Role> result = new PageBean<Role>();

		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);

		Integer start = result.getStartRow();

		List<Role> list = roleDaoImpl.getRoleOrderById(start, pageRecorders);
		result.setObjList(list);

		return result;
	}

	/**
	 * 添加
	 */
	public Integer createNewUser(Role role) {
		role.setCreateDate(new Date());
		role.setIsDel(0);
		return (Integer) roleDaoImpl.add(role);
	}

	  /**
	    * 在弹出的对话框显示详细信息
	    */
	public Role getRoleById(Integer id) {
		return roleDaoImpl.findById(Role.class, id);
	}
	
	/**
	 * 修改
	 * @param toUpdateRole
	 * @return
	 */

	public Integer updateRole(Role role) {
	         role.setCreateDate(new Date());
	         role.setIsDel(0);
			  return roleDaoImpl.update(role); 
	}
	/**
	 * 假删除
	 * @param id
	 * @return
	 */
	public Integer deleteRoleById(Integer id){
		Role role =roleDaoImpl.findById(Role.class, id);
		role.setIsDel(1);
           return roleDaoImpl.update(role);
	}

	public Integer checkRole(Role role) {
		
		return roleDaoImpl.checkRole(role);
	}

	@SuppressWarnings("unchecked")
	public List<Role> findRole() {
		
		return (List<Role>) roleDaoImpl.findRole();
	}
}
