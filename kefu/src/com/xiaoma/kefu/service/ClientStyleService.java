package com.xiaoma.kefu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.ClientStyleDao;
import com.xiaoma.kefu.model.ClientStyle;

/**
 * 访客端界面	业务实现类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:50:53
**********************************
 */
@Service
public class ClientStyleService {
	
	@Autowired
	private ClientStyleDao clientStyleDaoImpl;
	
	/**
	 * 根据风格id查找
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ClientStyle getByStyleId(Integer styleId) {
		return clientStyleDaoImpl.findByStyleId(styleId);
	}
	
	/**
	 * 根据主键id查找
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ClientStyle get(Integer id) {
		return clientStyleDaoImpl.findById(ClientStyle.class, id);
	}
	
	/**
	 *更新
	* @Description: TODO
	* @param clientStyle
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public Integer update(ClientStyle clientStyle) {
		return clientStyleDaoImpl.update(clientStyle);
	}


}