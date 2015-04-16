package com.xiaoma.kefu.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.RoleDeptDao;
import com.xiaoma.kefu.dao.RoleDeptFuncDao;
import com.xiaoma.kefu.model.RoleDept;
import com.xiaoma.kefu.model.RoleDeptFunc;
@Repository
public class RoleDeptFuncDaoImpl extends BaseDaoImpl<RoleDeptFunc> implements RoleDeptFuncDao {
	private static Logger logger = Logger.getLogger(RoleDeptFuncDaoImpl.class);

}
