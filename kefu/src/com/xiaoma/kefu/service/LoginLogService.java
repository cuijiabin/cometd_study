package com.xiaoma.kefu.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.LoginLogDao;
import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.util.PageBean;

/**
 * LoginLog DAO service class
 * 
 * @author hanyu
 * 
 */
@Service
public class LoginLogService {

	@Autowired
	private LoginLogDao loginLogDaoImpl;

	/**
	 * 分页查询
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public void getResult(Map<String, String> conditions,PageBean<LoginLog> pageBean){
		loginLogDaoImpl.findByCondition(conditions,pageBean);	
	}


}
