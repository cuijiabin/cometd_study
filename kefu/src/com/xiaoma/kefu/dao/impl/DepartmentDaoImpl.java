package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.DepartmentDao;
import com.xiaoma.kefu.model.Department;

@Repository("deptDaoImpl")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao{
	/**
	 * 查询所有数据
	 */
	@Override
    public Integer getAllDeptCount() {
		
		Session session = getSession();
		String hql = "select count(1) from Department c where isDel<>1 ";
		Query query = session.createSQLQuery(hql);
		return ((Number)query.uniqueResult()).intValue();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getDeptOrderById(Integer start, Integer offset) {
		//参数检查
		start = (start == null)? 0 :start;
		offset = (offset == null)? 20 :offset;
		
		Session session = getSession();
		String hql = "from Department where isDel<>1 order by sortNum asc";
		Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
		
		return (List<Department>) query.list();
	}

	@Override
	public Integer checkDept(Department dept) {
		Session session = getSession();
		StringBuffer hqlBf = new StringBuffer("select count(a.id) from Department a where isDel<>1");
		if(StringUtils.isNotBlank(dept.getName())){
			hqlBf.append(" and a.name  = '"+dept.getName()+"' ");
		}
		Query query = session.createSQLQuery(hqlBf.toString());
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
		String hql = "select max(sortNum) from Department c where isDel<>1 ";
		Query query = session.createSQLQuery(hql);
		return ((Number)query.uniqueResult()).intValue();
		
	} 
}





