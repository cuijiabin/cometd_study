package com.xiaoma.kefu.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.RoleDeptDao;
import com.xiaoma.kefu.model.RoleDept;
@Repository
public class RoleDeptDaoImpl extends BaseDaoImpl<RoleDept> implements RoleDeptDao {
	private static Logger logger = Logger.getLogger(RoleDeptDaoImpl.class);

}
