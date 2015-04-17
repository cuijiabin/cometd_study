package com.xiaoma.kefu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.model.ServiceIcon;
import com.xiaoma.kefu.service.ServiceIconService;
import com.xiaoma.kefu.util.SysConst;
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
	
	
	/**
	 * 编辑
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "editPC.action", method = RequestMethod.GET)
	public String editPC(Model model,@RequestParam Integer styleId) {
		try {
			ServiceIcon serviceIcon = serviceIconService.getByStyleId(styleId,DeviceType.PC);
			String onUrl = getViewPath(serviceIcon, StylePicName.客服图标PC在线);
			String offUrl = getViewPath(serviceIcon, StylePicName.客服图标PC离线);
			List<DictItem> dict = DictMan.getDictList("d_display_model");
			model.addAttribute("serviceIcon", serviceIcon);
			model.addAttribute("onUrl", onUrl);
			model.addAttribute("offUrl", offUrl);
			model.addAttribute("dict", dict);
			return "/style/service/editPC";
		} catch (Exception e) {
			logger.error("editPC"+styleId,e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 保存
	* @Description: TODO
	* @param model
	* @param customer
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "savePC.action", method = RequestMethod.POST)
	public String savePC(HttpServletRequest request,Model model,MultipartFile fileOn,
			MultipartFile fileOff,
			@ModelAttribute("serviceIcon") ServiceIcon serviceIcon) {
		try {
			//保存文件 ys
			serviceIconService.saveUplaodFile(fileOn,serviceIcon,StylePicName.客服图标PC在线);
			serviceIconService.saveUplaodFile(fileOff,serviceIcon,StylePicName.客服图标PC离线);
//			
//			//拿出旧的创建时间,类型, 别的全用新的
			ServiceIcon oldModel = serviceIconService.get(serviceIcon.getId());
			serviceIcon.setCreateDate(oldModel.getCreateDate());
			serviceIcon.setDeviceType(oldModel.getDeviceType());
			serviceIcon.setUpdateDate(new Date());
			if(serviceIcon.getOnlinePic()==null){//如果这次没上传图片,则取上次的地址
				serviceIcon.setOnlinePic(oldModel.getOnlinePic());;
			}
			if(serviceIcon.getOfflinePic()==null){//如果这次没上传图片,则取上次的地址
				serviceIcon.setOfflinePic(oldModel.getOfflinePic());
			}
			serviceIconService.update(serviceIcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/serviceIcon/editPC.action?styleId="+serviceIcon.getStyleId(); 
	}
	
	/**
	 * 缩略图展示的路径
	* @Description: TODO
	* @param serviceIcon
	* @param type
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	private String getViewPath(ServiceIcon serviceIcon,StylePicName type) {
		String extensionName = "";
		if(type.equals(StylePicName.客服图标PC在线)){
			String fileName = serviceIcon.getOnlinePic();
			if(StringUtils.isBlank(fileName)) return extensionName;
			extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
		}else if(type.equals(StylePicName.客服图标PC离线)){
			String fileName = serviceIcon.getOfflinePic();
			if(StringUtils.isBlank(fileName)) return extensionName;
			extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
		}
		return 
//				SystemConfiguration.getInstance().getPicPrefix() //前缀
				DictMan.getDictItem("d_sys_param", 2).getItemName()
				+ "/" + SysConst.STYLE_PATH //风格主目录
				+ "/"+serviceIcon.getStyleId()	//风格id
				+ "/"+type.getCode()	//类别
				+ SysConst.MIN_PIC_SUFFIX //缩略图后缀
				+ extensionName	//后缀
				;
	}  
	
	
}
