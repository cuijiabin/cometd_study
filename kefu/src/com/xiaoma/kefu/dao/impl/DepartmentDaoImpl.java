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

import com.xiaoma.kefu.dao.DepartmentDao;
import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

@Repository("deptDaoImpl")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements
		DepartmentDao {
	private static Logger logger = Logger.getLogger(DepartmentDaoImpl.class);

	/**
	 * 分页条件查询
	 * 
	 * @param conditions
	 * @param pageBean
	 */
	public void findByCondition(Map<String, String> conditions,
			PageBean<Department> pageBean) {
		List<String> relation = new ArrayList<String>();
		List<Criterion> role = new ArrayList<Criterion>();// 条件
		List<Order> orders = new ArrayList<Order>();// 排序
		if (conditions != null) {
			if (StringHelper.isNotEmpty(conditions.get("name"))) {
				role.add(Restrictions.like("name", "%"
						+ conditions.get("name").trim() + "%"));
			}
		}
		role.add(Restrictions.eq("isDel", 0));
		orders.add(Order.asc("sortNum"));
		find(Department.class, relation, role, null, orders, pageBean);
	}

	/**
	 * 查询所有数据
	 */
	@Override
	public Integer getAllDeptCount() {

		Session session = getSession();
		String hql = "select count(1) from Department c where c.isDel<>1 ";
		Query query = session.createQuery(hql);
		return ((Number) query.uniqueResult()).intValue();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getDeptOrderById(Integer start, Integer offset) {
		// 参数检查
		start = (start == null) ? 0 : start;
		offset = (offset == null) ? 20 : offset;

		Session session = getSession();
		String hql = "from Department where isDel<>1 order by sortNum asc";
		Query query = session.createQuery(hql).setFirstResult(start)
				.setMaxResults(offset);

		return (List<Department>) query.list();
	}

	@Override
	public Integer checkDept(Department dept) {
		Session session = getSession();
		StringBuffer hqlBf = new StringBuffer(
				"select count(a.id) from Department a where a.isDel<>1");
		if (StringUtils.isNotBlank(dept.getName())) {
			hqlBf.append(" and a.name  = '" + dept.getName() + "' ");
		}
		Query query = session.createQuery(hqlBf.toString());
		Object obj = query.uniqueResult();
		return Integer.parseInt(obj.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findDept() {
		Session session = getSession();
		String hql = "from Department where isDel<>1 ";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Override
	public Integer getMaxNum() {
		Session session = getSession();
		String hql = "select max(sortNum) from Department where isDel<>1 ";
		Query query = session.createQuery(hql);
		return ((Number) query.uniqueResult()).intValue();

	}
}
