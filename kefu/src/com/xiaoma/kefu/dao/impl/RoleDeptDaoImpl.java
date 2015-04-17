package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.RoleDeptDao;
import com.xiaoma.kefu.model.RoleDept;

@Repository
public class RoleDeptDaoImpl extends BaseDaoImpl<RoleDept> implements
		RoleDeptDao {

	private static Logger logger = Logger.getLogger(RoleDeptDaoImpl.class);

	@Override
	public RoleDept findRoleDeptBy(Integer roleId, Integer deptId) {
		Session session = getSession();
		String hql = "from RoleDept c where c.roleId =" + roleId
				+ " and c.deptId =" + deptId;
		Query query = session.createQuery(hql);
		 List list = query.list();
		 if(list.isEmpty()){
			 return null;
		 }
		 return (RoleDept) list.get(0);
	}

	@Override
	public Integer deleteRD(Integer id) {
      try{
		Session session = getSession();
		String hql = "delete from RoleDeptFunc b where b.roleDeptId =" + id;
		Query query = session.createQuery(hql);
		query.executeUpdate();
		return 1;
      }catch(Exception e){
    	  logger.error(e.getMessage());
      }
      return 0;
	}

}
