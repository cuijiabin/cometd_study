package com.xiaoma.kefu.dao;

import java.util.List;
import java.util.Map;

import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.model.WaitList;
import com.xiaoma.kefu.util.PageBean;

/**
 * *********************************
* @Description: 风格	dao
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:24:16
**********************************
 */
public interface StyleDao extends BaseDao<Style>{
	
	/**
	 * 根据名称模糊查询
	* @Description: TODO
	* @param styleName
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public List<WaitList> findByNameLike(String styleName);
	
	/**
	 * 查询所有风格
	* @Description: TODO
	* @param conditions	查询条件
	* @param pageBean	分页
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public void findByCondition(Map<String, String> conditions,
			PageBean<Style> pageBean);
	
	/**
	 * 校验风格名称
	* @Description: TODO
	* @param style
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer validateName(Style style);
	
}
