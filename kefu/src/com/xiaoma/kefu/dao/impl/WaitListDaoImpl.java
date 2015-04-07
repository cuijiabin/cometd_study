package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.WaitListDao;
import com.xiaoma.kefu.model.WaitList;

/**
 *  等待菜单列表	daoImpl
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月3日下午4:42:35
**********************************
 */
@Repository("waitListDaoImpl")
public class WaitListDaoImpl extends BaseDaoImpl<WaitList> implements WaitListDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<WaitList> findByNameLike(String waitListName) {
		Session session = getSession();
		String hql = "from WaitList t where t.name LIKE :waitListName ";
		Query query = session.createQuery(hql);
		query.setString("waitListName", "%" + waitListName + "%");
		return	query.list();
	}

	
}
