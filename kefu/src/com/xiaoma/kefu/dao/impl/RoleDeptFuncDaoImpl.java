package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.RoleDeptFuncDao;
import com.xiaoma.kefu.model.RoleDeptFunc;

@Repository
public class RoleDeptFuncDaoImpl extends BaseDaoImpl<RoleDeptFunc> implements
		RoleDeptFuncDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDeptFunc> getFuncByRD(Integer id) {
		Session session = getSession();
		String hql = "from RoleDeptFunc c where c.roleDeptId =" + id;
		Query query = session.createQuery(hql);
		return query.list();
	}

}
