package com.xiaoma.kefu.dao;

import java.util.Map;

import com.xiaoma.kefu.model.OperateLog;
import com.xiaoma.kefu.util.PageBean;


/**
 * User DAO interface
 * @author hanyu
 *
 */
public interface OperateLogDao extends BaseDao<OperateLog>{
	public void findByCondition(Map<String, String> conditions,PageBean<OperateLog> pageBean);
}
