package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Dialogue;




/**
 * *********************************
* @Description: 对话信息	dao
* @author: wangxingfei
* @createdAt: 2015年4月3日上午10:01:54
**********************************
 */
public interface DialogueDao extends BaseDao<Dialogue> {
	
	/**
	 * 更新为删除状态, 用于逻辑删除
	* @Description: TODO
	* @param dialogue
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public int update2Del(Dialogue dialogue) ;
	
	/**
	 * 更新为正常状态,用于回收站还原
	* @Description: TODO
	* @param dialogue
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public int update2Restore(Dialogue dialogue);
	/**
	 * 根据客户Id获取所有客户对话
	 * @param customerId
	 * @return 
	 * @Author: hanyu
	 */
	public List<Dialogue> findDialogByCustomerId(Long customerId);
	
	public Dialogue getLastBycustomerIdAndUserId(Long customerId,Integer userId);
	
}
