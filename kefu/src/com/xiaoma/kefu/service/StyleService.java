package com.xiaoma.kefu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.StyleDao;
import com.xiaoma.kefu.model.ClientStyle;
import com.xiaoma.kefu.model.InviteIcon;
import com.xiaoma.kefu.model.ServiceIcon;
import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.SysConst.DeviceType;
import com.xiaoma.kefu.util.SysConst.StyleIconType;

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
	
	@Autowired
	private ClientStyleService clientStyleService;//访客端界面
	@Autowired
	private ServiceIconService serviceIconService;//客服图标
	@Autowired
	private InviteIconService inviteIconService;//对话邀请框

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
	
	/**
	 * 初始化风格
	 * 1.创建风格
	 * 2.创建风格下的图标等表
	* @param style
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public Integer initStyle(Style style) {
		//创建风格
		Integer num = (Integer) styleDaoImpl.add(style);
		Integer styleId = style.getId();
		
		//访问端界面
		ClientStyle clientStyle = new ClientStyle();
		clientStyle.setStyleId(styleId);
		clientStyleService.create(clientStyle);
		
		//客服图标PC
		ServiceIcon serviceIcon = new ServiceIcon();
		serviceIcon.setStyleId(styleId);
		serviceIcon.setDeviceType(DeviceType.PC.getCode());
		serviceIcon.setIsDisplay(1);//默认显示
		serviceIcon.setDisplayMode(1);//默认浮动图标
		Integer buttonId = Integer.valueOf(styleId+""+StyleIconType.客服图标.getCode());
		serviceIcon.setButtonId(buttonId);
		serviceIconService.create(serviceIcon);
		
		//对话邀请框PC
		InviteIcon inviteIcon = new InviteIcon();
		inviteIcon.setStyleId(styleId);
		inviteIcon.setDeviceType(DeviceType.PC.getCode());
		inviteIcon.setLocationMode(2);//默认自动居中
		buttonId = Integer.valueOf(styleId+""+StyleIconType.对话邀请框.getCode());
		inviteIcon.setButtonId(buttonId);
		inviteIconService.create(inviteIcon);
		
		//客服图标-移动
		serviceIcon = new ServiceIcon();
		serviceIcon.setStyleId(styleId);
		serviceIcon.setDeviceType(DeviceType.移动.getCode());
		serviceIcon.setIsDisplay(1);//默认显示
		serviceIcon.setDisplayMode(1);//默认浮动图标
		buttonId = Integer.valueOf(styleId+""+StyleIconType.手机端客服图标.getCode());
		serviceIcon.setButtonId(buttonId);
		serviceIconService.create(serviceIcon);
		
		//对话邀请框-移动
		inviteIcon = new InviteIcon();
		inviteIcon.setStyleId(styleId);
		inviteIcon.setDeviceType(DeviceType.移动.getCode());
		inviteIcon.setLocationMode(2);//默认自动居中
		buttonId = Integer.valueOf(styleId+""+StyleIconType.手机端对话邀请框.getCode());
		inviteIcon.setButtonId(buttonId);
		inviteIconService.create(inviteIcon);
		
		return num;
	}

}