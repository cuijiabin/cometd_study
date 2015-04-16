package com.xiaoma.kefu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.model.WaitList;
import com.xiaoma.kefu.service.WaitListService;

@Controller
@RequestMapping(value = "waitList")
public class WaitListController {

	private Logger logger = Logger.getLogger(WaitListController.class);
	
	@Autowired
	private WaitListService waitListService;
	
	/**
	 * 编辑
	* @Description: TODO
	* @param model
	* @param styleId	风格id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	@RequestMapping(value = "edit.action", method = RequestMethod.GET)
	public String edit(Model model,Integer styleId) {
		try {
			List<WaitList> pList = waitListService.getOneLev(styleId);
			if(pList!=null && pList.size()>0){
				List<WaitList> zList = waitListService.getByPid(pList.get(0).getId());
				model.addAttribute("zList", zList);
			}
			model.addAttribute("pList", pList);
			return "/style/wait/edit";
		} catch (Exception e) {
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
}
