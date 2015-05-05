package com.xiaoma.kefu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.model.InviteIcon;
import com.xiaoma.kefu.service.InviteIconService;
import com.xiaoma.kefu.util.SysConst.DeviceType;

/**
 * 对话邀请框	controller
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:56
**********************************
 */
@Controller
@RequestMapping(value = "inviteIcon")
public class InviteIconController {

	private Logger logger = Logger.getLogger(InviteIconController.class);

	@Autowired
	private InviteIconService inviteIconService;
	
	
	/**
	 * PC 编辑
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "editPC.action", method = RequestMethod.GET)
	public String editPC(Model model,Integer styleId) {
		try {
			InviteIcon inviteIcon = inviteIconService.getByStyleId(styleId,DeviceType.PC);
			List<DictItem> dict = DictMan.getDictList("d_location_model");
			String strDiv = inviteIconService.getPvwDiv(inviteIcon,null,true,DeviceType.PC);
			model.addAttribute("inviteIcon", inviteIcon);
			model.addAttribute("strDiv", strDiv);
			model.addAttribute("dict", dict);
			return "/style/invite/editPC";
		} catch (Exception e) {
			logger.error("editPC"+styleId,e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	
	/**
	 * 移动 编辑
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "editYD.action", method = RequestMethod.GET)
	public String editYD(Model model,Integer styleId) {
		try {
			InviteIcon inviteIcon = inviteIconService.getByStyleId(styleId,DeviceType.移动);
			List<DictItem> dict = DictMan.getDictList("d_location_model");
			String strDiv = inviteIconService.getPvwDiv(inviteIcon,null,true,DeviceType.移动);
			
			model.addAttribute("inviteIcon", inviteIcon);
			model.addAttribute("strDiv", strDiv);
			model.addAttribute("dict", dict);
			return "/style/invite/editYD";
		} catch (Exception e) {
			logger.error("editYD"+styleId,e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * PC 保存
	* @Description: TODO
	* @param model
	* @param inviteIcon
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "savePC.action", method = RequestMethod.POST)
	public String savePC(Model model,
			@ModelAttribute("inviteIcon") InviteIcon inviteIcon) {
		try {
			InviteIcon oldModel = inviteIconService.get(inviteIcon.getId());
			inviteIcon.setCreateDate(oldModel.getCreateDate());
			inviteIcon.setTruePic(oldModel.getTruePic());
			inviteIcon.setButtonId(oldModel.getButtonId());
			inviteIconService.saveAndUpdateDiv4PC(inviteIcon);
		} catch (Exception e) {
			logger.error("savePC"+inviteIcon.getId(), e);
			return "error500";
		}
		return "redirect:/inviteIcon/editPC.action?styleId="+inviteIcon.getStyleId();
	}
	
	
	/**
	 * 移动 保存
	* @Description: TODO
	* @param model
	* @param inviteIcon
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "saveYD.action", method = RequestMethod.POST)
	public String saveYD(Model model,
			@ModelAttribute("inviteIcon") InviteIcon inviteIcon) {
		try {
			InviteIcon oldModel = inviteIconService.get(inviteIcon.getId());
			inviteIcon.setCreateDate(oldModel.getCreateDate());
			inviteIcon.setTruePic(oldModel.getTruePic());
			inviteIcon.setButtonId(oldModel.getButtonId());
			inviteIconService.saveAndUpdateDiv4YD(inviteIcon);
		} catch (Exception e) {
			logger.error("saveYD"+inviteIcon.getId(), e);
			return "error500";
		}
		return "redirect:/inviteIcon/editYD.action?styleId="+inviteIcon.getStyleId();
	}
	
	
}

