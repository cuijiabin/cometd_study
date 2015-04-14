package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.FunctionDao;
import com.xiaoma.kefu.model.Function;

@Repository
public class FunctionDaoImpl  extends BaseDaoImpl<Function> implements FunctionDao{

	@SuppressWarnings("rawtypes")
	@Override
	public List findBylevel(int level) {
		Session session = getSession();
		String hql = "from Function c where c.funcLevel="+level;
		Query query = session.createQuery(hql);
		return query.list();
	
	}

	@Override
	public List findTree(int tid) {
		Session session = getSession();
		String hql="";
		if(tid!=6){
		     hql = "from Function c where c.id="+tid+" or c.pId ="+tid;
		}else{
			 hql = "from Function c where c.id="+tid+" or c.pId ="+tid+" or c.pId in (select d.id from Function d where d.pId="+tid+")";
		}
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Override
	public List findAll() {
		Session session = getSession();
		String hql = "from Function c where 1=1";
		Query query = session.createQuery(hql);
		return query.list();
	}



}
