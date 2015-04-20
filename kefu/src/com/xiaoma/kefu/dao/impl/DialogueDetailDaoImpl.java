package com.xiaoma.kefu.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.DialogueDetailDao;
import com.xiaoma.kefu.model.DialogueDetail;

@Repository("dialogueDetailDaoImpl")
public class DialogueDetailDaoImpl extends BaseDaoImpl<DialogueDetail>
		implements DialogueDetailDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<DialogueDetail> getLastRecordsByCustomerId(Long customerId) {
		
		Session session = getSession();
		
		String sql = "SELECT id FROM dialogue g where g.customerId =:customerId ORDER BY g.beginDate desc";
	    SQLQuery sqlQuery = session.createSQLQuery(sql);
	    sqlQuery.setLong("customerId", customerId).setMaxResults(1);
	    BigInteger dialogueId = (BigInteger) sqlQuery.uniqueResult();
	    
		String hql = "from DialogueDetail d where d.dialogueId =:dialogueId order by d.createDate";
		Query query = session.createQuery(hql);
		query.setBigInteger("dialogueId", dialogueId);
		
		return query.list();
	}

}
