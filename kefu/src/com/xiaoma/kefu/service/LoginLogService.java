package com.xiaoma.kefu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.controller.UserController;
import com.xiaoma.kefu.dao.LoginLogDao;
import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

/**
 * LoginLog DAO service class
 * 
 * @author hanyu
 * 
 */
@Service
public class LoginLogService {
	private Logger logger = Logger.getLogger(LoginLogService.class);
	@Autowired
	private LoginLogDao loginLogDaoImpl;

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public void getResult(Map<String, String> conditions,
			PageBean<LoginLog> pageBean) {
		loginLogDaoImpl.findByCondition(conditions, pageBean);
	}

	/**
	 * 保存log
	 * 
	 * @param log
	 * @return
	 */
	public Integer addLog(Integer userId, String ip) {
		try {
			if (userId == null || StringHelper.isEmpty(ip))
				return 0;
			User user = (User) CacheMan.getObject(CacheName.SUSER, userId,User.class);
			if (user == null)
				return 0;
			LoginLog log = new LoginLog();
			log.setUserId(user.getId());
			log.setCardName(user.getCardName());
			log.setDeptId(user.getDeptId());
			log.setIp(ip);
			log.setLoginName(user.getLoginName());
			log.setCreateDate(new Date());
			return (Integer) loginLogDaoImpl.add(log);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	/**
	 * 修改log
	 * 
	 * @param log
	 * @return
	 */
	public Integer updateLog(Integer userId) {
		try {
			List<LoginLog> list = loginLogDaoImpl.findByUserId(userId);
			if (list != null && list.size() > 0) {
				LoginLog log = list.get(0);
				log.setEndDate(new Date());
				return (Integer) loginLogDaoImpl.update(log);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

}
