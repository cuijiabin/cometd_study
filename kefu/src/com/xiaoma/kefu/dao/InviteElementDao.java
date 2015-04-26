package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.InviteElement;

/**
 * 邀请框元素	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:24
**********************************
 */
public interface InviteElementDao extends BaseDao<InviteElement>{
	
	/**
	 * 根据邀请框id,获取元素list
	* @Description: TODO
	* @param inviteId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月17日
	 */
	public List<InviteElement> findByInviteId(Integer inviteId);
	
	/**
	 * 校验名称是否存在
	* @Description: TODO
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public Integer validateName(InviteElement inviteElement);
	
	/**
	 * 获取邀请框的第一个元素, 就是外框
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月26日
	 */
	public InviteElement findFirstEle(Integer inviteId);
	
	
}
