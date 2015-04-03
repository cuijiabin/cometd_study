package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DepartmentDao;
import com.xiaoma.kefu.model.Department;
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
	public boolean createNewDept(Department dept) {
		// TODO Auto-generated method stub
		return deptDaoImpl.createNewDept(dept);
	}

	  /**
	    * 在弹出的对话框显示详细信息
	    */
	public Department getDeptById(Integer id) {
		return deptDaoImpl.getDeptByDeptId(id);
	}
	
	/**
	 * 修改
	 * @param toUpdateDepartment
	 * @return
	 */

	public boolean updateDept(Department dept) {
			  return deptDaoImpl.updateUser(dept); 
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteDeptById(Integer id){
           return deptDaoImpl.deleteDeptById(id);
	}

	public Integer checkDept(Department dept) {
		
		return deptDaoImpl.checkDept(dept);
	}

	@SuppressWarnings("unchecked")
	public List<Department> findDept() {
		
		return (List<Department>) deptDaoImpl.findDept();
	}
}
