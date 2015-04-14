package com.xiaoma.kefu.dao;

import java.util.Map;

import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.util.PageBean;


/**
 * User DAO interface
 * @author hanyu
 *
 */
public interface LoginLogDao extends BaseDao<LoginLog>{
	public void findByCondition(Map<String, String> conditions,	PageBean<LoginLog> pageBean);
}
