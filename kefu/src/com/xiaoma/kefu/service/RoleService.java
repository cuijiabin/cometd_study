package com.xiaoma.kefu.service;

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
	public boolean createNewUser(Role role) {
		// TODO Auto-generated method stub
		return roleDaoImpl.createNewRole(role);
	}

	  /**
	    * 在弹出的对话框显示详细信息
	    */
	public Role getRoleById(Integer id) {
		return roleDaoImpl.getRoleByRoleId(id);
	}
	
	/**
	 * 修改
	 * @param toUpdateRole
	 * @return
	 */

	public boolean updateRole(Role role) {
			  return roleDaoImpl.updateUser(role); 
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteRoleById(Integer id){
           return roleDaoImpl.deleteRoleById(id);
	}

	public Integer checkRole(Role role) {
		
		return roleDaoImpl.checkRole(role);
	}

	@SuppressWarnings("unchecked")
	public List<Role> findRole() {
		
		return (List<Role>) roleDaoImpl.findRole();
	}
}
