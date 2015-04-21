package com.xiaoma.kefu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DepartmentDao;
import com.xiaoma.kefu.dao.RoleDao;
import com.xiaoma.kefu.dao.RoleDeptDao;
import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.model.RoleDept;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.SysConst.RoleNameId;

/**
 * 
 * @author yangxiaofeng
 *
 */
@Service
public class DepartmentService {
	private Logger logger = Logger.getLogger(DepartmentService.class);
	@Autowired DepartmentDao deptDaoImpl;
	@Autowired RoleDao roleDaoImpl;
	@Autowired RoleDeptDao roleDeptDao;
	@Autowired UserDao userDaoImpl;
	
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
	 * 分页查询
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public void getResult(Map<String, String> conditions,PageBean<Department> pageBean){
		deptDaoImpl.findByCondition(conditions,pageBean);	
	}

	/**
	 * 添加
	 */
	public Integer createNewDept(Department dept) {
		int count = deptDaoImpl.getMaxNum();
		dept.setId(count+1);
        dept.setIsDel(0);
       int deptId = (Integer) deptDaoImpl.add(dept);
		List<Role> list = roleDaoImpl.findRole();
		for (Role role : list) {
			RoleDept rd=new RoleDept();
			rd.setRoleId(role.getId());
			rd.setDeptId(deptId);
			rd.setCreateDate(new Date());
			rd.setIsDel(0);
			roleDeptDao.add(rd);
		}	
		 return deptId;
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
		List<User> user = userDaoImpl.getUsertByDeptId(id);
		if(user.isEmpty()||user==null){
		Department dept =deptDaoImpl.findById(Department.class, id);
		dept.setIsDel(1);
           return deptDaoImpl.update(dept);
		}
		return 3;
	}

	public Integer checkDept(Department dept) {
		
		return deptDaoImpl.checkDept(dept);
	}

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
	/**
	 * 根据id查询部门里的所有员工包括离职的员工（用于记录查询）
	 * @param deptId
	 * @return
	 */
	public List<User> deptUserAll(Integer deptId) {
		try{
		   List<User> list = userDaoImpl.findUserAllBydeptId(deptId);
		   return list;
		}catch(Exception e){
			logger.error(e.getMessage());
		}
         return null;
	}
	
	public List<Department> deptByRole(Integer userId) {
		try{
			User user = userDaoImpl.findById(User.class, userId);
			if(user.getRoleId()== Integer.parseInt(DictMan.getDictItem("d_sys_Param", 13).getItemName())){
				List<Department> list=deptDaoImpl.getAllDept();
				return list;
			}else{
				List<Department> dept =deptDaoImpl.getDeptById(user.getDeptId());
				return dept;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
         return null;
	}
	
	/**
	 * 查询所有部门,及部门下在职员工
	 * (用于树形展示)
	* @param onlyRoleId	如果只需要挂了某个角色的用户,则传次参数, 如果需要全部则传null
	* @return	json格式 
	* 			id:1 	部门或用户id
	* 			pId:0 	部门pId=0, 用户pId=部门id
	* 			name:xx	部门或用户name,用户取cardName
	* 			type:1	用户type=1,部门type=2
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	public JSONArray getDeptUserTree(RoleNameId onlyRoleId){
		JSONArray json = new JSONArray();
		List<Department> deptList = findDept();//所有部门
		for(Department dept : deptList){
			//封装部门进去
			JSONObject deptJson = new JSONObject();
			deptJson.element("id", dept.getId()).element("pId", "0").element("name", dept.getName()).element("type", "2");
			json.add(deptJson);
			
			//查询部门下员工,只查在职状态
			List<User> userList = userDaoImpl.getUsertByDeptId(dept.getId());
			for(User user : userList){
				if(onlyRoleId!=null){//如果需要过滤角色
					if(user.getRoleId().equals(onlyRoleId.getCode())){//
						JSONObject userJson = new JSONObject();
						userJson.element("id", user.getId()).element("pId", dept.getId()).element("name", user.getCardName()).element("type", "1");
						json.add(userJson);
					}
				}else{//全部在职员工
					JSONObject userJson = new JSONObject();
					userJson.element("id", user.getId()).element("pId", dept.getId()).element("name", user.getCardName()).element("type", "1");
					json.add(userJson);
				}
			}
		}
		return json;
	}
	
}
