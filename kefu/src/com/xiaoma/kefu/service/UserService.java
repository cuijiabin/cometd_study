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

import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.PageBean;


/**
 * User DAO service class
 * @author  yangxiaofeng
 *
 */
@Service("userService")
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
//	public PageBean<User> getResult(Integer currentPage,Integer pageRecorders){
//			
//		Integer totalCount = userDaoImpl.getAllUserCount();
//		PageBean<User> result = new PageBean<User>();
//		
//		result.setCurrentPage(currentPage);
//		result.setPageRecorders(pageRecorders);
//		result.setTotalRows(totalCount);
//		
//		Integer start = result.getStartRow();
//		
//	      List<User> list =userDaoImpl.getUsertOrderById(start,pageRecorders);
//		result.setObjList(list);
//		
//		return result;
//	}
	
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
	 * 分页查询
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public void getResult(Map<String, String> conditions,PageBean<User> pageBean){
		userDaoImpl.findByCondition(conditions,pageBean);	
	}
     
    /**
     * 精确查询
     */
   public Integer checkUser (User user){
		
	Integer totalCount = userDaoImpl.checkUser(user);
	
	return totalCount;
	
	}


		/**
		 * 添加
		 * @throws ParseException 
		 */
       public Integer createNewUser(User user) throws ParseException{
    	  user.setOnLineStatus(1);
    	  user.setStatus(1);
          return (Integer) userDaoImpl.add(user);
       }
      
       /**
	    * 在弹出的对话框显示详细信息
	    */
	   public User getUserById(Integer id){
		   return userDaoImpl.findById(User.class,id);
	   }
	   
	   /**
	    * 修改
	 * @throws UnsupportedEncodingException 
	    */
	   public Integer updateUser(User user) throws UnsupportedEncodingException{
		   User toUpdateUser = userDaoImpl.findById(User.class,user.getId());
		   if(user.getPassword()!=""||user.getPassword()!=null){
			String password = new String(DigestUtils.md5Hex(user.getPassword().getBytes("UTF-8")));
			  toUpdateUser.setPassword(password);
		   }else{
			   toUpdateUser.setPassword(toUpdateUser.getPassword());
		   }
		    toUpdateUser.setLoginName(user.getLoginName());
		    toUpdateUser.setCardName(user.getCardName());
		    toUpdateUser.setDeptId(user.getDeptId());
		    toUpdateUser.setRoleId(user.getRoleId());
		    toUpdateUser.setStatus(toUpdateUser.getStatus());
		    toUpdateUser.setListenLevel(user.getListenLevel());
		    toUpdateUser.setEmail(user.getEmail());
		    toUpdateUser.setCreateDate(user.getCreateDate());
		  return userDaoImpl.update(toUpdateUser); 
	   }
	   
	    /**
		 * 删除(假删除)
		 */
	public Integer deleteUserById(String ids){
		int val=0;
		if(ids.length()>2){
			String[] array = ids.split(",");
			for (String str : array) {
			     User user= new User();
			      user.setId(Integer.parseInt(str));
			      val=userDaoImpl.delete(user);
			}
			if(val==1){
				return 1;
			}else{
			  return 0;
			}
		}else{
			  User user= new User();
		      user.setId(Integer.parseInt(ids));
		      return userDaoImpl.delete(user);
		}
	}

//员工离职
	public Integer leaveUser(String ids,Integer status) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String time = sdf.format(new Date());
	int val=0;
		if(ids.length()>2){
			String[] array = ids.split(",");
			for (String str : array) {
			   User leup = userDaoImpl.findById(User.class,Integer.parseInt(str));
			       leup.setStatus(status);
			       leup.setEndDate(time);
			       val=userDaoImpl.update(leup);
			}
			if(val==1){
				return 1;
			}else{
			  return 0;
			}
		}else{
			   User leup = userDaoImpl.findById(User.class,Integer.parseInt(ids));
		       leup.setStatus(status);
		       leup.setEndDate(time);
		        return userDaoImpl.update(leup);
		}
	}
	//员工转换部门
	public Integer tradeUser(String ids, Integer deptId) {
		if(ids.length()>2){
			String[] array = ids.split(",");
			for (String str : array) {
			   User leup = userDaoImpl.findById(User.class,Integer.parseInt(str));
			       leup.setDeptId(deptId);
			       return userDaoImpl.update(leup);
			}
			return 0;
		}else{
			   User leup = userDaoImpl.findById(User.class,Integer.parseInt(ids));
			   leup.setDeptId(deptId);
		        return userDaoImpl.update(leup);
		}
	}
	//通过部门查询员工
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
