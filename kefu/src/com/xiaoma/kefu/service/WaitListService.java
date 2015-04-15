package com.xiaoma.kefu.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.WaitListDao;
import com.xiaoma.kefu.model.WaitList;


/**
 * 等待菜单列表	业务处理类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月3日下午3:27:45
**********************************
 */
@Service
public class WaitListService {
	
	@Autowired
	private WaitListDao waitListDaoImpl;
	
	/**
	 * 根据名称 模糊查询
	* @Description: TODO
	* @param waitListName
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public List<WaitList> findByNameLike(String waitListName) {
		return waitListDaoImpl.findByNameLike(waitListName);
	}
	
	/**
	 * 根据主键id查询
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public WaitList get(Integer id) {
		return waitListDaoImpl.findById(WaitList.class, id);
	}
	


}
