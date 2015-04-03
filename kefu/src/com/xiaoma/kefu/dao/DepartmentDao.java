package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Department;




public interface DepartmentDao {
	/**
	 * 获取全部数据
	 * @return
	 */
	public Integer getAllDeptCount();

	public List<Department> getDeptOrderById(Integer start, Integer offset);
	/**
	 * create a new Department
	 * @param user
	 * @return
	 */
	public abstract boolean createNewDept(Department dept);

	/**
	 * get Department by id
	 * @param id
	 * @return
	 */
	public Department getDeptByDeptId(Integer id);

	/**
	 * 修改
	 * @param dept
	 * @return
	 */
	public boolean updateUser(Department dept);
	
	  /**
     * 删除
     * @param id
     * @return
     */
	public boolean deleteDeptById(Integer id);

	public Integer checkDept(Department dept);

	public List<Department> findDept();
}
