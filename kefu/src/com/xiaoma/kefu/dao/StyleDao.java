package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.model.WaitList;

/**
 * *********************************
* @Description: 风格	dao
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:24:16
**********************************
 */
public interface StyleDao extends BaseDao<Style>{

	public List<WaitList> findByNameLike(String styleName);
	
}
