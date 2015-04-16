package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.WaitList;





/**
 *  等待菜单列表	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月3日下午4:42:35
**********************************
 */
public interface WaitListDao extends BaseDao<WaitList>{
	
	/**
	 * 根据名称,模糊查找
	* @Description: TODO
	* @param waitListName
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	public List<WaitList> findByNameLike(String waitListName);
	
	/**
	 * 查找风格下的 一级菜单
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	public List<WaitList> findOneLev(Integer styleId);
	
	/**
	 * 根据pid,查找二级菜单
	* @Description: TODO
	* @param pId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	public List<WaitList> findByPid(Integer pId);
	
}
