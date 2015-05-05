package com.xiaoma.kefu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.model.ServiceIcon;
import com.xiaoma.kefu.service.ServiceIconService;
import com.xiaoma.kefu.service.StyleService;
import com.xiaoma.kefu.util.SysConst.DeviceType;
import com.xiaoma.kefu.util.SysConst.StylePicName;

/**
 * 客服图标	controller
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:56
**********************************
 */
@Controller
@RequestMapping(value = "serviceIcon")
public class ServiceIconController {

	private Logger logger = Logger.getLogger(ServiceIconController.class);

	@Autowired
	private ServiceIconService serviceIconService;
	@Autowired
	private StyleService styleService;//风格
	
	
	/**
	 * 编辑
	* @Description: TODO
	* @param model
	* @param styleId 风格id
	* @param deviceTypeId 1=PC 2=移动
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "edit.action", method = RequestMethod.GET)
	public String edit(Model model,Integer styleId,Integer deviceTypeId) {
		try {
			String result = "/style/service/editPC";
			ServiceIcon serviceIcon;
			String onUrl,offUrl;
			if(deviceTypeId==1){
				serviceIcon = serviceIconService.getByStyleId(styleId,DeviceType.PC);
				onUrl = styleService.getMinPicPath(styleId, StylePicName.客服图标PC在线);
				offUrl = styleService.getMinPicPath(styleId, StylePicName.客服图标PC离线);
			}else{
				serviceIcon = serviceIconService.getByStyleId(styleId,DeviceType.移动);
				onUrl = styleService.getMinPicPath(styleId, StylePicName.客服图标移动在线);
				offUrl = styleService.getMinPicPath(styleId, StylePicName.客服图标移动离线);
				result = "/style/service/editYD";
			}
			List<DictItem> dict = DictMan.getDictList("d_display_model");//显示方式
			model.addAttribute("serviceIcon", serviceIcon);
			model.addAttribute("onUrl", onUrl);
			model.addAttribute("offUrl", offUrl);
			model.addAttribute("dict", dict);
			return result;
		} catch (Exception e) {
			logger.error("edit"+styleId+",deviceTypeId="+deviceTypeId,e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * PC 保存
	* @param model
	* @param fileOn
	* @param fileOff
	* @param serviceIcon
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	@RequestMapping(value = "savePC.action", method = RequestMethod.POST)
	public String savePC(Model model,MultipartFile fileOn,
			MultipartFile fileOff,
			@ModelAttribute("serviceIcon") ServiceIcon serviceIcon) {
		try {
			ServiceIcon oldModel = serviceIconService.get(serviceIcon.getId());
			serviceIconService.saveAndUpdateDiv4PC(fileOn,fileOff,serviceIcon,oldModel);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "redirect:/serviceIcon/edit.action?styleId="+serviceIcon.getStyleId()
				+"&deviceTypeId="+serviceIcon.getDeviceType(); 
	}
	
	/**
	 * 移动 保存
	* @param model
	* @param fileOn
	* @param fileOff
	* @param serviceIcon
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	@RequestMapping(value = "saveYD.action", method = RequestMethod.POST)
	public String saveYD(Model model,MultipartFile fileOn,
			MultipartFile fileOff,
			@ModelAttribute("serviceIcon") ServiceIcon serviceIcon) {
		try {
			ServiceIcon oldModel = serviceIconService.get(serviceIcon.getId());
			serviceIconService.saveAndUpdateDiv4YD(fileOn,fileOff,serviceIcon,oldModel);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "redirect:/serviceIcon/edit.action?styleId="+serviceIcon.getStyleId()
				+"&deviceTypeId="+serviceIcon.getDeviceType(); 
	}
	
	
}
