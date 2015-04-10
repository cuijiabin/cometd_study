package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DepartmentDao;
import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.util.PageBean;

/**
 * 
 * @author yangxiaofeng
 *
 */
@Service
public class DepartmentService {
	@Autowired DepartmentDao deptDaoImpl;
	
	/**
	 * 查询所有
	 * @param currentPage
	 * @param pageRecorders
	 * @return
	 */
	public PageBean<Department> getResult(Integer currentPage,Integer pageRecorders) {

		Integer totalCount = deptDaoImpl.getAllDeptCount();
		PageBean<Department> result = new PageBean<Department>();

		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);

		Integer start = result.getStartRow();

		List<Department> list = deptDaoImpl.getDeptOrderById(start, pageRecorders);
		result.setObjList(list);

		return result;
	}

	/**
	 * 添加
	 */
	public Integer createNewDept(Department dept) {
        dept.setIsDel(0);
		return (Integer) deptDaoImpl.add(dept);
	}

	  /**
	    * 在弹出的对话框显示详细信息
	    */
	public Department getDeptById(Integer id) {
		return deptDaoImpl.findById(Department.class, id);
	}
	
	/**
	 * 修改
	 * @param toUpdateDepartment
	 * @return
	 */

	public Integer updateDept(Department dept) {
		Department dep=deptDaoImpl.findById(Department.class, dept.getId());
		Department newDept = new Department();
		  newDept.setId(dept.getId());
		  newDept.setName(dept.getName());
		  newDept.setUserCount(dep.getUserCount());
		  newDept.setSortNum(dep.getSortNum());
		  newDept.setIsDel(dep.getIsDel());
	    return deptDaoImpl.update(newDept); 
	}
	/**
	 * 假删除
	 * @param id
	 * @return
	 */
	public Integer deleteDeptById(Integer id){
		Department dept =deptDaoImpl.findById(Department.class, id);
		dept.setIsDel(1);
           return deptDaoImpl.update(dept);
	}

	public Integer checkDept(Department dept) {
		
		return deptDaoImpl.checkDept(dept);
	}

	@SuppressWarnings("unchecked")
	public List<Department> findDept() {
		
		return (List<Department>) deptDaoImpl.findDept();
	}
/**
 * 查找部们的排序
 * @return
 */
	public Integer getMaxNum() {
		
		return deptDaoImpl.getMaxNum();
	}
}
