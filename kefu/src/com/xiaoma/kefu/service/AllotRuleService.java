package com.xiaoma.kefu.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.AllotRuleDao;
import com.xiaoma.kefu.model.AllotRule;

/**
 * 分配机制	业务实现类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:50:53
**********************************
 */
@Service
public class AllotRuleService {
	
	@Autowired
	private AllotRuleDao allotRuleDaoImpl;
	
	/**
	 * 创建
	* @param allotRule
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public Integer create(AllotRule allotRule){
		allotRule.setCreateDate(new Date());
		return (Integer) allotRuleDaoImpl.add(allotRule);
	}
	
	/**
	 * 根据风格id查找
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public AllotRule getByStyleId(Integer styleId) {
		return allotRuleDaoImpl.findByStyleId(styleId);
	}
	
	/**
	 * 根据主键id查找
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public AllotRule get(Integer id) {
		return allotRuleDaoImpl.findById(AllotRule.class, id);
	}
	
	/**
	 *更新
	* @Description: TODO
	* @param clientStyle
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public Integer update(AllotRule allotRule) {
		allotRule.setUpdateDate(new Date());
		return allotRuleDaoImpl.update(allotRule);
	}


}