package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.StyleDao;
import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.model.WaitList;

/**
 * *********************************
* @Description: 风格	daoImpl
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:29:12
**********************************
 */
@Repository("styleDaoImpl")
public class StyleDaoImpl extends BaseDaoImpl<Style> implements StyleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<WaitList> findByNameLike(String styleName) {
		Session session = getSession();
		String hql = "from Style t where t.name LIKE :styleName ";
		Query query = session.createQuery(hql);
		query.setString("styleName", "%" + styleName + "%");
		return	query.list();
	}

}
