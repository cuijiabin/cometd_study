package com.xiaoma.kefu.dao;

import java.util.List;
import java.util.Map;

import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.util.PageBean;

public interface DepartmentDao extends BaseDao<Department> {

	/**
	 * User DAO interface
	 * 
	 * @author yangxiaofeng
	 * 
	 */
	public void findByCondition(Map<String, String> conditions,
			PageBean<Department> pageBean);

	/**
	 * 获取全部数据
	 * 
	 * @return
	 */
	public Integer getAllDeptCount();

	public List<Department> getDeptOrderById(Integer start, Integer offset);

	public Integer checkDept(Department dept);

	public List<Department> findDept();

	/**
	 * 排序id
	 * 
	 * @return
	 */
	public Integer getMaxNum();

	public List<Department> getAllDept();

	public List<Department> getDeptById(Integer deptId);
}
