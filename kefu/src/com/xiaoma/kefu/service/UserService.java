package com.xiaoma.kefu.service;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.PageBean;


/**
 * User DAO service class
 * @author  yangxiaofeng
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDaoImpl;

	public User login(String name, String password){
		return userDaoImpl.findUser(name, password);
	}
	/**
	 * 查询所有用户
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public PageBean<User> getResult(Integer currentPage,Integer pageRecorders){
			
		Integer totalCount = userDaoImpl.getAllUserCount();
		PageBean<User> result = new PageBean<User>();
		
		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);
		
		Integer start = result.getStartRow();
		
	      List<User> list =userDaoImpl.getUsertOrderById(start,pageRecorders);
		result.setObjList(list);
		
		return result;
	}
	
	/**
	 * 条件查询
	 */
public PageBean<User> getResultByuserNameOrPhone(Integer currentPage,Integer pageRecorders,String userName ,String phone){
	
	Integer totalCount = userDaoImpl.getUserCountByuserNameOrPhone(userName,phone);
	PageBean<User> result = new PageBean<User>();
	
	result.setCurrentPage(currentPage);
	result.setPageRecorders(pageRecorders);
	result.setTotalRows(totalCount);
	
	Integer start = result.getStartRow();
	
	  List<User> list =userDaoImpl.getUserByuserNameOrPhone(start,pageRecorders,userName ,phone);
	result.setObjList(list);
	
	return result;
     }

     
    /**
     * 精确查询
     */
public Integer checkUser (User user){
		
	Integer totalCount = userDaoImpl.checkUser(user);
	
	return totalCount;
	
	}

	/**
	 * 查询出team leader
	 * @return
	 */
		public List<User> findLeader() {
			
			return userDaoImpl.getLeader();
		}

		/**
		 * 添加
		 */
       public boolean createNewUser(User user){
          return userDaoImpl.createNewUser(user);
       }
      
       /**
	    * 在弹出的对话框显示详细信息
	    */
	   public User getUserById(Integer id){
		   return userDaoImpl.getUserByUserId(id);
	   }
	   
	   /**
	    * 修改
	 * @throws UnsupportedEncodingException 
	    */
	   public boolean updateUser(User user) throws UnsupportedEncodingException{
			String password = new String(DigestUtils.md5Hex(user.getPassword().getBytes("UTF-8")));
			User toUpdateUser = userDaoImpl.getUserByUserId(user.getId());
		    toUpdateUser.setLoginName(user.getLoginName());
		    toUpdateUser.setPassword(user.getPassword());
		    toUpdateUser.setCardName(user.getCardName());
		    toUpdateUser.setDeptId(user.getDeptId());
		    toUpdateUser.setRoleId(user.getRoleId());
		    toUpdateUser.setStatus(user.getStatus());
		    toUpdateUser.setListenLevel(user.getListenLevel());
		    toUpdateUser.setEmail(user.getEmail());
		    toUpdateUser.setCreateDate(user.getCreateDate());
		  return userDaoImpl.updateUser(toUpdateUser); 
	   }
	   
	    /**
		 * 删除(假删除)
		 */
	public boolean deleteUserById(Integer id){
		boolean flag = true;
		User user =  userDaoImpl.getUserByUserId(id);
		
		if (user!=null) {
			user.setStatus(0);
			flag = userDaoImpl.updateUser(user);
		}
		return flag;
	}
       /**
        * 查询出客服经理
        * @return
        */
		public List<User> findManager() {
			
			return userDaoImpl.getManager();
		}
	public boolean leaveUser(String ids) {
		if(ids.length()>2){
			String[] array = ids.split(",");
			boolean flg=false;
			for (String str : array) {
			   User leup = userDaoImpl.getUserByUserId(Integer.parseInt(str));
			       leup.setStatus(2);
			       leup.setEndDate(new Date());
			       flg = userDaoImpl.updateUser(leup);
			}
			return flg;
		}else{
			   User leup = userDaoImpl.getUserByUserId(Integer.parseInt(ids));
		       leup.setStatus(2);
		       leup.setEndDate(new Date());
		        return userDaoImpl.updateUser(leup);
		}
	}
	public boolean tradeUser(String ids, Integer deptId) {
		if(ids.length()>2){
			String[] array = ids.split(",");
			boolean flg=false;
			for (String str : array) {
			   User leup = userDaoImpl.getUserByUserId(Integer.parseInt(str));
			       leup.setDeptId(deptId);
			       flg = userDaoImpl.updateUser(leup);
			}
			return flg;
		}else{
			   User leup = userDaoImpl.getUserByUserId(Integer.parseInt(ids));
			   leup.setDeptId(deptId);
		        return userDaoImpl.updateUser(leup);
		}
	}
	public PageBean<User> getResultDept(Integer currentPage, Integer pageRecorders, Integer deptId) {
			
		Integer totalCount = userDaoImpl.getDeptUserCount(deptId);
		PageBean<User> result = new PageBean<User>();
		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);
		Integer start = result.getStartRow();
	      List<User> list =userDaoImpl.getUsertByDeptId(start,pageRecorders,deptId);
		result.setObjList(list);
		
		return result;
	}

}
