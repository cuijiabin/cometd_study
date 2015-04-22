package com.xiaoma.kefu.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public User findUser(String name) {
		Session session = getSession();
		String hql = "from User u where u.loginName = :name and u.status = 1";
		Query query = session.createQuery(hql);
		query.setString("name", name);
		return (User) query.uniqueResult();
	}

	/**
	 * 查询所有用户
	 */
	@Override
	public Integer getAllUserCount() {

		Session session = getSession();
		String hql = "select count(1) from User where status<>2 ";
		Query query = session.createQuery(hql);

		return ((Number) query.uniqueResult()).intValue();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsertOrderById(Integer start, Integer offset) {

		// 参数检查
		start = (start == null) ? 0 : start;
		offset = (offset == null) ? 20 : offset;

		Session session = getSession();
		String hql = "from User u where u.status<>2 limit order by id asc";
		Query query = session.createQuery(hql).setFirstResult(start)
				.setMaxResults(offset);

		return (List<User>) query.list();
	}

	/**
	 * 条件查询
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByuserNameOrPhone(Integer start, Integer offset,
			String userName, String phone) {

		// 参数检查
		start = (start == null) ? 0 : start;
		offset = (offset == null) ? 20 : offset;

		Session session = getSession();

		String hql = "from User c where 1=1 and status<>2 ";
		if (StringHelper.isNotEmpty(userName)) {
			hql += " and c.userName like '" + "%" + userName + "%" + "'";
		}
		if (StringHelper.isNotEmpty(phone)) {
			hql += " and c.phone like '" + "%" + phone + "%" + "'";
		}
		Query query = session.createQuery(hql).setFirstResult(start)
				.setMaxResults(offset);
		return (List<User>) query.list();
	}

	/**
	 * 精确查询数据库中的信息
	 */
	@Override
	public Integer checkUser(User user) {
		Session session = getSession();
		StringBuffer hqlBf = new StringBuffer(
				"select count(a.id) from User a where 1 = 1");
		if (StringUtils.isNotBlank(user.getLoginName())) {
			hqlBf.append(" and a.loginName  = '" + user.getLoginName() + "' ");
		}
		if (StringUtils.isNotBlank(user.getPhone())) {
			hqlBf.append(" and a.phone = '" + user.getPhone() + "' ");
		}
		if (user.getId() != null && user.getId() > 0)
			hqlBf.append(" and a.id != " + user.getId() + " ");
		Query query = session.createQuery(hqlBf.toString());
		Object obj = query.uniqueResult();
		return Integer.parseInt(obj.toString());
	}

	@Override
	public Integer getUserCountByuserNameOrPhone(String userName, String phone) {
		Session session = getSession();
		StringBuffer hqlBf = new StringBuffer(
				"select count(1) from User where 1 = 1 and status<>2");
		if (StringUtils.isNotBlank(userName)) {
			hqlBf.append(" and userName  like '%" + userName + "%'");
		}
		if (StringUtils.isNotBlank(phone)) {
			hqlBf.append(" and phone like '%" + phone + "%'");
		}
		Query query = session.createQuery(hqlBf.toString());

		return ((Number) query.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsertByDeptId(Integer deptId) {
		Session session = getSession();
		String hql = "from User u where u.status<>2 and u.deptId =" + deptId
				+ " order by u.id asc";
		Query query = session.createQuery(hql);
		return (List<User>) query.list();
	}

	/**
	 * 分页查询
	 */
	public void findByCondition(Map<String, String> conditions,
			PageBean<User> pageBean) {
		List<String> relation = new ArrayList<String>();
		List<Criterion> role = new ArrayList<Criterion>();// 条件
		List<Order> orders = new ArrayList<Order>();// 排序
		if (conditions != null) {
			if (StringHelper.isNotEmpty(conditions.get("loginName"))) {
				role.add(Restrictions.like("loginName",
						"%" + conditions.get("loginName").trim() + "%"));
			}
			if (StringHelper.isNotEmpty(conditions.get("status"))
					&& !"0".equals(conditions.get("status"))) {
				role.add(Restrictions.eq("status",
						Integer.parseInt(conditions.get("status"))));
			}
		}
		orders.add(Order.asc("id"));
		find(User.class, relation, role, null, orders, pageBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsertByRoleId(Integer roleId) {
		Session session = getSession();
		String hql = "from User u where u.status<>2 and u.roleId =" + roleId
				+ " order by u.id asc";
		Query query = session.createQuery(hql);
		return (List<User>) query.list();
	}

	/**
	 * 根据部门查询所有用户，包括离职
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserAllBydeptId(Integer deptId) {
		Session session = getSession();
		String hql = "from User u where u.deptId =" + deptId
				+ " order by u.id asc";
		Query query = session.createQuery(hql);
		return (List<User>) query.list();
	}

}
