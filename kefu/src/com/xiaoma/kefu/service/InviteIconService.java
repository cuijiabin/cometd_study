package com.xiaoma.kefu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.InviteIconDao;
import com.xiaoma.kefu.model.InviteIcon;
import com.xiaoma.kefu.util.SysConst.DeviceType;

/**
 * 对话邀请框	业务实现类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:50:53
**********************************
 */
@Service
public class InviteIconService {
	
	@Autowired
	private InviteIconDao inviteIconDaoImpl;
	
	/**
	 * 根据风格id,设备类型 查找
	* @Description: TODO
	* @param styleId
	 * @param type 
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public InviteIcon getByStyleId(Integer styleId, DeviceType deviceType) {
		return inviteIconDaoImpl.findByStyleId(styleId,deviceType);
	}
	
	/**
	 * 根据主键id查找
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public InviteIcon get(Integer id) {
		return inviteIconDaoImpl.findById(InviteIcon.class, id);
	}
	
	/**
	 *更新
	* @Description: TODO
	* @param inviteIcon
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public Integer update(InviteIcon inviteIcon) {
		return inviteIconDaoImpl.update(inviteIcon);
	}

}