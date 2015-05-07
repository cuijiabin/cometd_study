package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.BusiGroupDetail;

/**
 * 业务分组明细	dao
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月13日下午5:41:16
**********************************
 */
public interface BusiGroupDetailDao extends BaseDao<BusiGroupDetail>{
	
	/**
	 * 获取分组下的明细list
	* @Description: TODO
	* @param groupId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public List<BusiGroupDetail> findByGroupId(Integer groupId);
	
	/**
	 * 根据分组id,删除
	* @param id
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月20日
	 */
	public int deleteByGroupId(Integer groupId);
	
	/**
	 * 根据分组id,用户id,用户类型, 查找当前对象
	* @param busiGroupDetail
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	public BusiGroupDetail getByModel(BusiGroupDetail busiGroupDetail);
	
	/**
	 * 更新是否 接待客户
	* @param detail
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	public int updateIsRece(BusiGroupDetail detail);
	
	/**
	 * 根据用户和类型,删除分组明细信息
	* @param userId	用户或部门id
	* @param userType	1=用户 2=部门
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月22日
	 */
	public int deleteByUserOrDept(Integer userId,Integer userType);
	
	/**
	 * 根据风格id获取userId列表
	 * @param styleId
	 * @return
	 */
	public List<Integer> findUserIdsByStyleId(Integer styleId);
	
	/**
	 * 根据用户id获取有效风格id列表
	 * @param userId
	 * @return
	 */
	public List<Integer> getStyleIdsByuserId(Integer userId);
}
