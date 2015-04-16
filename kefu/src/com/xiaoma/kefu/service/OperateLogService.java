package com.xiaoma.kefu.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.OperateLogDao;
import com.xiaoma.kefu.model.OperateLog;
import com.xiaoma.kefu.util.PageBean;


/**
 * User DAO service class
 * @author  hanyu
 *
 */
@Service
public class OperateLogService {
	
	@Autowired
	private OperateLogDao operateLogDaoImpl;

	/**
	 * 分页查询
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public void getResult(Map<String, String> conditions,PageBean<OperateLog> pageBean){
		operateLogDaoImpl.findByCondition(conditions,pageBean);	
	}
	/**
	 * 保存log
	 * @param log
	 * @return
	 */
	public Integer addLog(OperateLog log){
		return (Integer)operateLogDaoImpl.add(log);	
	}
	

}
