package com.xiaoma.kefu.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dao.DepartmentDao;
import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.PageBean;

/**
 * User DAO service class
 * 
 * @author yangxiaofeng
 * 
 */
@Service("userService")
public class UserService {

	@Autowired
	private UserDao userDaoImpl;
	@Autowired
	private DepartmentDao deptDao;

	public User login(String name) {
		return userDaoImpl.findUser(name);
	}

	/**
	 * 查询所有用户
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	// public PageBean<User> getResult(Integer currentPage,Integer
	// pageRecorders){
	//
	// Integer totalCount = userDaoImpl.getAllUserCount();
	// PageBean<User> result = new PageBean<User>();
	//
	// result.setCurrentPage(currentPage);
	// result.setPageRecorders(pageRecorders);
	// result.setTotalRows(totalCount);
	//
	// Integer start = result.getStartRow();
	//
	// List<User> list =userDaoImpl.getUsertOrderById(start,pageRecorders);
	// result.setObjList(list);
	//
	// return result;
	// }

	/**
	 * 条件查询
	 */
	public PageBean<User> getResultByuserNameOrPhone(Integer currentPage,
			Integer pageRecorders, String userName, String phone) {

		Integer totalCount = userDaoImpl.getUserCountByuserNameOrPhone(
				userName, phone);
		PageBean<User> result = new PageBean<User>();

		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);

		Integer start = result.getStartRow();

		List<User> list = userDaoImpl.getUserByuserNameOrPhone(start,
				pageRecorders, userName, phone);
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
	public void getResult(Map<String, String> conditions,
			PageBean<User> pageBean) {
		userDaoImpl.findByCondition(conditions, pageBean);
	}

	/**
	 * 精确查询
	 */
	public Integer checkUser(User user) {

		Integer totalCount = userDaoImpl.checkUser(user);

		return totalCount;

	}

	/**
	 * 添加
	 * 
	 * @throws ParseException
	 */
	public Integer createNewUser(User user) throws ParseException {
		user.setOnLineStatus(1);
		user.setStatus(1);
		user.setIsLock(0);
		Integer succ=(Integer) userDaoImpl.add(user);
		List<Department> dlist =deptDao.getDeptById(user.getDeptId());
		Department dept = dlist.get(0);
		dept.setUserCount(dept.getUserCount()-1);
		deptDao.update(dept);
		 return succ;
	}

	/**
	 * 在弹出的对话框显示详细信息
	 */
	public User getUserById(Integer id) {

		// User user = (User) JedisDao.getObject(JedisConstant.USER_INFO+id);
		// if(user == null){
		User user = userDaoImpl.findById(User.class, id);
		// JedisDao.setKO(JedisConstant.USER_INFO+id, user);
		// }
		return user;
	}

	/**
	 * 修改或者重置密码
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unused")
	public Integer updateUser(String pass, User user)
			throws UnsupportedEncodingException {
		User toUpdateUser = userDaoImpl.findById(User.class, user.getId());

		if (user.getPassword() == "" || user.getPassword() == null) {
			toUpdateUser.setPassword(toUpdateUser.getPassword());
			toUpdateUser.setIsLock(toUpdateUser.getIsLock());
		} else if (!pass.equals("1") && pass!=null) {
			String password = new String(DigestUtils.md5Hex(pass
					.getBytes("UTF-8")));
			toUpdateUser.setPassword(password);
			toUpdateUser.setIsLock(toUpdateUser.getIsLock());
		} else if(pass.equals("1")){
			toUpdateUser.setPassword(toUpdateUser.getPassword());
			toUpdateUser.setIsLock(1);
		}else{
			String password = new String(DigestUtils.md5Hex(user.getPassword()
					.getBytes("UTF-8")));
			toUpdateUser.setPassword(password);
			toUpdateUser.setIsLock(toUpdateUser.getIsLock());
		}
		toUpdateUser.setLoginName(user.getLoginName());
		toUpdateUser.setCardName(user.getCardName());
		toUpdateUser.setDeptId(user.getDeptId());
		toUpdateUser.setRoleId(user.getRoleId());
		toUpdateUser.setDeptName(user.getDeptName());
		toUpdateUser.setRoleName(user.getRoleName());
		toUpdateUser.setStatus(toUpdateUser.getStatus());
		toUpdateUser.setListenLevel(user.getListenLevel());
		toUpdateUser.setEmail(user.getEmail());
		toUpdateUser.setMaxListen(user.getMaxListen());
		toUpdateUser.setCreateDate(user.getCreateDate());
		Integer succ = userDaoImpl.update(toUpdateUser);
		CacheMan.remove(CacheName.USERFUNCTION, user.getId());
		return succ;
		
	}

	/**
	 * 删除(假删除)
	 */
	public Integer deleteUserById(String ids) {
		int val = 0;
		if (ids.length() > 2) {
			String[] array = ids.split(",");
			for (String str : array) {
				if (Integer.parseInt(str) == 0)
					continue;
				User user = new User();
				user.setId(Integer.parseInt(str));
				val = userDaoImpl.delete(user);
			}
			if (val == 1) {
				return 1;
			} else {
				return 0;
			}
		} else {
			User user = new User();
			user.setId(Integer.parseInt(ids));
			return userDaoImpl.delete(user);
		}
	}

	// 员工离职
	public Integer leaveUser(String ids, Integer status) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		int val = 0;
		if (ids.length() > 2) {
			String[] array = ids.split(",");
			for (String str : array) {
				if (Integer.parseInt(str) == 0)
					continue;
				User leup = userDaoImpl.findById(User.class,
						Integer.parseInt(str));
				leup.setStatus(status);
				leup.setEndDate(time);
				val = userDaoImpl.update(leup);
				List<Department> dlist =deptDao.getDeptById(leup.getDeptId());
				Department dept = dlist.get(0);
				if(status==2){
				    dept.setUserCount(dept.getUserCount()-1);
				}else{
					dept.setUserCount(dept.getUserCount()+1);
				}
				deptDao.update(dept);
			}
			if (val == 1) {
				return 1;
			} else {
				return 0;
			}
		} else {
			User leup = userDaoImpl.findById(User.class, Integer.parseInt(ids));
			leup.setStatus(status);
			leup.setEndDate(time);
			Integer succ = userDaoImpl.update(leup);
			List<Department> dlist =deptDao.getDeptById(leup.getDeptId());
			Department dept = dlist.get(0);
			if(status==2){
			    dept.setUserCount(dept.getUserCount()-1);
			}else{
				dept.setUserCount(dept.getUserCount()+1);
			}
			deptDao.update(dept);
			if (succ == 1) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	// 员工转换部门
	public Integer tradeUser(String ids, Integer deptId) {
		if (ids.length() > 2) {
			String[] array = ids.split(",");
			Integer succ=0;
			for (String str : array) {
				if (Integer.parseInt(str) == 0)
					continue;
				User leup = userDaoImpl.findById(User.class,Integer.parseInt(str));
				leup.setDeptId(deptId);
				List<Department> dlist =deptDao.getDeptById(deptId);
				Department dept = dlist.get(0);
				dept.setUserCount(dept.getUserCount()+1);
				deptDao.update(dept);
			    succ = userDaoImpl.update(leup);
			}
			if(succ==1){
				return succ;
			}
			return 0;
		} else {
			User leup = userDaoImpl.findById(User.class, Integer.parseInt(ids));
			leup.setDeptId(deptId);
			List<Department> dlist =deptDao.getDeptById(deptId);
			Department dept = dlist.get(0);
			dept.setUserCount(dept.getUserCount()+1);
			deptDao.update(dept);
			return userDaoImpl.update(leup);
		}
	}

	// 通过部门查询员工
	public List<User> getResultDept(Integer deptId) {

		List<User> list = userDaoImpl.getUsertByDeptId(deptId);
		return list;
	}

}
