package com.xiaoma.kefu.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
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
		busiGroupDetail.setCreateDate(new Date());
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
	
	/**
	 * 保存分流明细
	* @param data	15:0,16:1	id:是否勾选 0否,1是
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	public void saveDetail(String data) {
		if(StringUtils.isNotBlank(data)){
			String[] strs = data.split(",");
			for(String temp : strs){
				String[] ss = temp.split(":");
				BusiGroupDetail detail = new BusiGroupDetail();
				detail.setId(Integer.valueOf(ss[0]));
				detail.setIsReception(Integer.valueOf(ss[1]));
				busiGroupDetailDaoDaoImpl.updateIsRece(detail);
			}
			if(strs.length>0){
				String[] st = strs[0].split(":");
				BusiGroupDetail bgdTemp = busiGroupDetailDaoDaoImpl.findById(BusiGroupDetail.class, Integer.valueOf(st[0]));
				CacheMan.remove(CacheName.ONLINE_USER_STYLEID, bgdTemp.getStyleId());
			}
		}
	}
	
	/**
	 * 员工离职,删除业务分组中此员工信息
	* @param userId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月22日
	 */
	public int deleteByUserId(Integer userId){
		return busiGroupDetailDaoDaoImpl.deleteByUserOrDept(userId, 1);
	}
	
	/**
	 * 删除部门, 删除业务分组中部门信息
	* @param deptId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月22日
	 */
	public int deleteByDeptId(Integer deptId){
		return busiGroupDetailDaoDaoImpl.deleteByUserOrDept(deptId,2);
	}
	

	/**
	 * 根据风格id获取userId列表
	 * @param styleId
	 * @return
	 */
	public List<Integer> findUserIdsByStyleId(Integer styleId){
		return busiGroupDetailDaoDaoImpl.findUserIdsByStyleId(styleId);
	}
}