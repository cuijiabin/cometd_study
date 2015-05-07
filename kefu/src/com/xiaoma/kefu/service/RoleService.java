package com.xiaoma.kefu.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DepartmentDao;
import com.xiaoma.kefu.dao.FunctionDao;
import com.xiaoma.kefu.dao.RoleDao;
import com.xiaoma.kefu.dao.RoleDeptDao;
import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.model.RoleDept;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.PageBean;

/**
 * 
 * @author yangxiaofeng
 *
 */
@Service
public class RoleService {
	@Autowired RoleDao roleDaoImpl;
	@Autowired DepartmentDao deptDaoImpl;
	@Autowired RoleDeptDao roleDeptDao;
	@Autowired UserDao userDaoImpl;
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
	 * 分页查询
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public void getResult(Map<String, String> conditions,PageBean<Role> pageBean){
		roleDaoImpl.findByCondition(conditions,pageBean);	
	}

	/**
	 * 添加
	 */
	public Integer createNewUser(Role role) {
		role.setCreateDate(new Date());
		role.setIsDel(0);
		int roleId=(Integer) roleDaoImpl.add(role);
		List<Department> list = deptDaoImpl.findDept();
		for (Department dept : list) {
			RoleDept rd=new RoleDept();
			rd.setRoleId(roleId);
			rd.setDeptId(dept.getId());
			rd.setCreateDate(new Date());
			rd.setIsDel(0);
			roleDeptDao.add(rd);
		}
		return roleId;
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
		List<User> user=userDaoImpl.getUsertByRoleId(id);
		if(user.isEmpty() || user==null){
		Role role =roleDaoImpl.findById(Role.class, id);
		role.setIsDel(1);
           return roleDaoImpl.update(role);
		}
		return 3;
	}
	
   /**
    * 检查角色是否存在
    * @param role
    * @return
    */
	public Integer checkRole(Role role) {
		
		return roleDaoImpl.checkRole(role);
	}
	
    /**
     * 查询出所有角色
     * @return
     */
	public List<Role> findRole() {
		
		return (List<Role>) roleDaoImpl.findRole();
	}
}
