package com.xiaoma.kefu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.BusiGroupDao;
import com.xiaoma.kefu.model.BusiGroup;

/**
 **********************************
* @Description: 业务分组	业务实现类
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:21:23
**********************************
 */
@Service
public class BusiGroupService {
	
	@Autowired
	private BusiGroupDao busiGroupDaoImpl;
	@Autowired
	private BusiGroupDetailService busiGroupDetailService;//分组明细
	
	/**
	 * 获取风格下的 业务分组list
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public List<BusiGroup> findByStyleId(Integer styleId){
		return busiGroupDaoImpl.findByStyleId(styleId);
	}
	
	/**
	 * 校验名称是否存在
	* @Description: TODO
	* @param group
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer validateName(BusiGroup group) {
		return busiGroupDaoImpl.validateName(group);
	}
	
	/**
	 * 创建 分组
	* @Description: TODO
	* @param group
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer create(BusiGroup group) {
		group.setCreateDate(new Date());
		return (Integer) busiGroupDaoImpl.add(group);
	}
	
	/**
	 * 根据id查询
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public BusiGroup get(Integer id) {
		return busiGroupDaoImpl.findById(BusiGroup.class, id);
	}
	
	/**
	 * 更新
	* @Description: TODO
	* @param toUpdate
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer update(BusiGroup toUpdate) {
		return busiGroupDaoImpl.update(toUpdate);
	}
	
	/**
	 * 删除分组(包含明细)
	* @Description: TODO
	* @param group
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public int delete(BusiGroup group) {
		busiGroupDetailService.deleteByGroupId(group.getId());
		return busiGroupDaoImpl.delete(group);
	}
	

}