package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.StringHelper;

@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	@Override
	public User findUser(String name, String password) {
		Session session = getSession();
		String hql = "from User u where u.loginName = :name and u.password = :password and u.status = 1";
		Query query = session.createQuery(hql);
		//设置参数,使用MD5对密码进行加密
		try {
			password = new String(DigestUtils.md5Hex(password.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		query.setString("name", name);
		query.setString("password", password);
		return (User) query.uniqueResult();
	}
    
	/**
	 * 添加一条
	 */
	@Override
	public boolean createNewUser(User user) {
		try {
			add(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * 查询所有用户
	 */
	@Override
     public Integer getAllUserCount() {
		
		Session session = getSession();
		String hql = "select count(1) from User where status<>2 ";
		Query query = session.createSQLQuery(hql);
		
		return ((Number)query.uniqueResult()).intValue();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override	
    public List<User> getUsertOrderById(Integer start, Integer offset) {
		
		//参数检查
		start = (start == null)? 0 :start;
		offset = (offset == null)? 20 :offset;
		
		Session session = getSession();
		String hql = "from User u where u.status<>2 limit order by id asc";
		Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
		
		return (List<User>) query.list();
	}
	
	   /**
	    * 条件查询
	    */
		
		@SuppressWarnings("unchecked")
		@Override	
	    public List<User> getUserByuserNameOrPhone(Integer start, Integer offset ,String userName,String phone) {
			
			//参数检查
			start = (start == null)? 0 :start;
			offset = (offset == null)? 20 :offset;
			
			Session session = getSession();
			
			String hql = "from User c where 1=1 and status<>2 ";
			if(StringHelper.isNotEmpty(userName)){
				hql += " and c.userName like '"+"%"+userName+"%"+"'";
			}
			if(StringHelper.isNotEmpty(phone)){
				hql += " and c.phone like '"+"%"+phone+"%"+"'";
			}
			Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
			return (List<User>) query.list();
		}
		
	  /**
	   * 精确查询数据库中的信息
	   */
		@Override
		public Integer checkUser(User user) {
			Session session = getSession();
			StringBuffer hqlBf = new StringBuffer("select count(a.id) from User a where 1 = 1 and a.status<>2");
			if(StringUtils.isNotBlank(user.getLoginName())){
				hqlBf.append(" and a.loginName  = '"+user.getLoginName()+"' ");
			}
			if(StringUtils.isNotBlank(user.getPhone())){
				hqlBf.append(" and a.phone = '"+user.getPhone()+"' ");
			}
			if(user.getId() != null && user.getId()>0)
				hqlBf.append(" and a.id != "+user.getId()+" ");
			Query query = session.createSQLQuery(hqlBf.toString());
			Object obj = query.uniqueResult();
			return Integer.parseInt(obj.toString());
		}
		
		@Override
		public Integer getUserCountByuserNameOrPhone(String userName, String phone) {
			Session session = getSession();
			StringBuffer hqlBf = new StringBuffer("select count(1) from User where 1 = 1 and status<>2");
			if(StringUtils.isNotBlank(userName)){
				hqlBf.append(" and userName  like '%"+userName+"%'");
			}
			if(StringUtils.isNotBlank(phone)){
				hqlBf.append(" and phone like '%"+phone+"%'");
			}
			Query query = session.createSQLQuery(hqlBf.toString());
			
			return ((Number)query.uniqueResult()).intValue();
		}
		
		/**
		 * 查询一条
		 */
		@Override
		public User getUserByUserId(Integer id) {
			
			if(id == null){
				return null;
			}
			return findById(User.class,id);
			
		}
		
		/**
		 * 修改
		 * @param 
		 * @return
		 */
		@Override
		public boolean updateUser(User user) {
			try {
				update(user);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
		/**
		 * 删除
		 */
		@Override
		public boolean deleteUserById(Integer id){
			User user = this.getUserByUserId(id);
			try {	
			delete(user);
			return true;
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			
		}

		@Override
		public Integer getDeptUserCount(Integer deptId) {
			Session session = getSession();
			String hql = "select count(1) from User where status<>2 and deptId ="+deptId;
			Query query = session.createSQLQuery(hql);
			return ((Number)query.uniqueResult()).intValue();
		}

		@Override
		public List<User> getUsertByDeptId(Integer start,Integer offset, Integer deptId) {
			//参数检查
			start = (start == null)? 0 :start;
			offset = (offset == null)? 20 :offset;
			
			Session session = getSession();
			String hql = "from User u where u.status<>2 and u.deptId ="+deptId+" limit order by id asc";
			Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
			
			return (List<User>) query.list();
		}

		@Override
		public List<User> getLeader() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<User> getManager() {
			// TODO Auto-generated method stub
			return null;
		}

}
