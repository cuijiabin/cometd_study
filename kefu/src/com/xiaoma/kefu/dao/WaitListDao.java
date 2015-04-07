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

	public List<WaitList> findByNameLike(String waitListName);
	
}
