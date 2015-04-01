package com.xiaoma.kefu.dao;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.model.User;
@Repository("userDaoImpl")
public class UserDaoImpl extends HibBaseDao<User> implements UserDao {
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

}
