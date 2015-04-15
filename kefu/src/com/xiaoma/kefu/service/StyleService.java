package com.xiaoma.kefu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.StyleDao;
import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.util.PageBean;

/**
 **********************************
* @Description: 风格	业务实现类
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:21:23
**********************************
 */
@Service
public class StyleService {
	
	@Autowired
	private StyleDao styleDaoImpl;

	public List<Style> findByNameLike(String styleName) {
		return styleDaoImpl.findByNameLike(styleName);
	}
	
	/**
	 * 查询所有风格
	* @Description: TODO
	* @param conditions	查询条件
	* @param pageBean	分页
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public void getResult(Map<String, String> conditions, PageBean<Style> pageBean) {
		styleDaoImpl.findByCondition(conditions,pageBean);
		
	}
	
	/**
	 * 根据主键id,获取风格
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Style get(Integer id) {
		return styleDaoImpl.findById(Style.class, id);
	}
	
	/**
	 * 创建风格
	* @Description: TODO
	* @param style
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer create(Style style) {
		return (Integer) styleDaoImpl.add(style);
	}
	
	/**
	 * 更新风格
	* @Description: TODO
	* @param style
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer update(Style style) {
		return styleDaoImpl.update(style);
	}
	
	/**
	 * 校验风格名称	
	* @Description: TODO
	* @param style	
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer validateName(Style style) {
		return styleDaoImpl.validateName(style);
	}

}