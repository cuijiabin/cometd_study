package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.BusiGroupDetailDao;
import com.xiaoma.kefu.model.BusiGroupDetail;

/**
 **********************************
* @Description: 业务分组明细	业务实现类
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:21:23
**********************************
 */
@Service
public class BusiGroupDetailService {
	
	@Autowired
	private BusiGroupDetailDao busiGroupDetailDaoDaoImpl;
	
	/**
	 * 获取分组下的明细list
	* @Description: TODO
	* @param groupId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public List<BusiGroupDetail> findByGroupId(Integer groupId){
		return busiGroupDetailDaoDaoImpl.findByGroupId(groupId);
	}
	
	/**
	 * 根据分组id,删除
	* @param id
	* @Author: wangxingfei
	* @Date: 2015年4月20日
	 */
	public void deleteByGroupId(Integer groupId) {
		busiGroupDetailDaoDaoImpl.deleteByGroupId(groupId);
	}
	
	/**
	 * 根据分组id,用户id,用户类型, 查找当前对象
	* @param busiGroupDetail
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	public BusiGroupDetail getByModel(BusiGroupDetail busiGroupDetail) {
		return busiGroupDetailDaoDaoImpl.getByModel(busiGroupDetail);
	}
	
	/**
	 * 创建
	* @param busiGroupDetail
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	public Integer create(BusiGroupDetail busiGroupDetail) {
		return (Integer) busiGroupDetailDaoDaoImpl.add(busiGroupDetail);
	}

	/**
	 * 删除
	* @param busiGroupDetail
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	public int delete(BusiGroupDetail busiGroupDetail) {
		return busiGroupDetailDaoDaoImpl.delete(busiGroupDetail);
	}
	

}