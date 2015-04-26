package com.xiaoma.kefu.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.RoleDao;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
	/**
	 * 分页条件查询
	 */
	public void findByCondition(Map<String, String> conditions,
			PageBean<Role> pageBean) {
		List<String> relation = new ArrayList<String>();
		List<Criterion> role = new ArrayList<Criterion>();// 条件
		List<Order> orders = new ArrayList<Order>();// 排序
		if (conditions != null) {
			if (StringHelper.isNotEmpty(conditions.get("name"))) {
				role.add(Restrictions.like("name", "%"
						+ conditions.get("name").trim() + "%"));
			}
			if (StringHelper.isNotEmpty(conditions.get("id"))
					&& !"0".equals(conditions.get("id"))) {
				role.add(Restrictions.ne("id",
						Integer.parseInt(conditions.get("id"))));
			}
		}
		role.add(Restrictions.eq("isDel", 0));
		orders.add(Order.desc("createDate"));
		find(Role.class, relation, role, null, orders, pageBean);
	}

	/**
	 * 查询所有数据
	 */
	@Override
	public Integer getAllRoleCount() {

		Session session = getSession();
		String hql = "select count(1) from Role where isDel<>1 and id <>1";
		Query query = session.createQuery(hql);
		return ((Number) query.uniqueResult()).intValue();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoleOrderById(Integer start, Integer offset) {
		// 参数检查
		start = (start == null) ? 0 : start;
		offset = (offset == null) ? 20 : offset;

		Session session = getSession();
		String hql = "from Role where isDel<>1 and id<>1 order by id asc";
		Query query = session.createQuery(hql).setFirstResult(start)
				.setMaxResults(offset);

		return (List<Role>) query.list();
	}

	@Override
	public Integer checkRole(Role role) {
		Session session = getSession();
		StringBuffer hqlBf = new StringBuffer(
				"select count(a.id) from Role a where a.isDel<>1 and a.id<>1");
		if (StringUtils.isNotBlank(role.getName())) {
			hqlBf.append(" and a.name  = '" + role.getName() + "' ");
		}
		Query query = session.createQuery(hqlBf.toString());
		Object obj = query.uniqueResult();
		return Integer.parseInt(obj.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findRole() {
		Session session = getSession();
		String hql = "from Role where isDel<>1 and id<>1";
		Query query = session.createQuery(hql);
		return query.list();
	}
}
