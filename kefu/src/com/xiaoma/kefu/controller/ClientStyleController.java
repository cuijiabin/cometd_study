package com.xiaoma.kefu.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.model.ClientStyle;
import com.xiaoma.kefu.service.ClientStyleService;
import com.xiaoma.kefu.service.StyleService;
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
	@Autowired
	private StyleService styleService;//风格
	
	
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
			String ysUrl = styleService.getMinPicPath(styleId, StylePicName.访问端右上)+"?"+new Random().nextInt();
			String yxUrl = styleService.getMinPicPath(styleId, StylePicName.访问端右下)+"?"+new Random().nextInt();
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
			logger.error("save",e);
		}
		return "redirect:/clientStyle/edit.action?styleId="+clientStyle.getStyleId(); 
	}
	
}
