package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Department;




public interface DepartmentDao extends BaseDao<Department>{
	/**
	 * 获取全部数据
	 * @return
	 */
	public Integer getAllDeptCount();

	public List<Department> getDeptOrderById(Integer start, Integer offset);

	public Integer checkDept(Department dept);

	public List<Department> findDept();
/**
 * 排序id
 * @return
 */
	public Integer getMaxNum();
}
