package com.xiaoma.kefu.controller;

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
import com.xiaoma.kefu.model.ClientStyle;
import com.xiaoma.kefu.service.ClientStyleService;
import com.xiaoma.kefu.util.SysConst;
import com.xiaoma.kefu.util.SysConst.StylePicName;

/**
 * 访客端界面	controller
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:56
**********************************
 */
@Controller
@RequestMapping(value = "clientStyle")
public class ClientStyleController {

	private Logger logger = Logger.getLogger(ClientStyleController.class);

	@Autowired
	private ClientStyleService clientStyleService;
	
	
	/**
	 * 编辑
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "edit.action", method = RequestMethod.GET)
	public String edit(Model model,@RequestParam Integer styleId) {
		try {
			ClientStyle clientStyle = clientStyleService.getByStyleId(styleId);
			String ysUrl = getViewPath(clientStyle, StylePicName.访问端右上);
			String yxUrl = getViewPath(clientStyle, StylePicName.访问端右下);
			model.addAttribute("clientStyle", clientStyle);
			model.addAttribute("ysUrl", ysUrl);
			model.addAttribute("yxUrl", yxUrl);
			return "/style/client/edit";
		} catch (Exception e) {
			logger.error("edit"+styleId,e);
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
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(HttpServletRequest request,Model model,MultipartFile fileYs,
			MultipartFile fileYx,
			@ModelAttribute("clientStyle") ClientStyle clientStyle) {
		try {
			ClientStyle oldModel = clientStyleService.get(clientStyle.getId());
			clientStyleService.updateAndSaveFile(fileYs,fileYx,clientStyle,oldModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/clientStyle/edit.action?styleId="+clientStyle.getStyleId(); 
	}
	
	/**
	 * 缩略图展示的路径
	* @Description: TODO
	* @param clientStyle
	* @param type
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	private String getViewPath(ClientStyle clientStyle,StylePicName type) {
		String extensionName = "";
		if(type.equals(StylePicName.访问端右上)){
			String fileName = clientStyle.getYsAd();
			if(StringUtils.isBlank(fileName)) return extensionName;
			extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
		}else if(type.equals(StylePicName.访问端右下)){
			String fileName = clientStyle.getYxAd();
			if(StringUtils.isBlank(fileName)) return extensionName;
			extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
		}
		return 
//				SystemConfiguration.getInstance().getPicPrefix() //前缀
				DictMan.getDictItem("d_sys_param", 2).getItemName()
				+ "/" + SysConst.STYLE_PATH //风格主目录
				+ "/"+clientStyle.getStyleId()	//风格id
				+ "/"+type.getCode()	//类别
				+ SysConst.MIN_PIC_SUFFIX //缩略图后缀
				+ extensionName	//后缀
				;
	}  
	
	
}
