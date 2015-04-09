package com.xiaoma.kefu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Blacklist;
import com.xiaoma.kefu.service.BlacklistService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;


/**
 * @author frongji
 * @time 2015年4月2日上午11:21:05
 *    黑名单 Controller
 */
@Controller
@RequestMapping(value = "blacklist")
public class BlacklistController {
   
	@Autowired
	private BlacklistService blacklistService;
	
	/**
	 * 查询所有、条件查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(MapEntity conditions, @ModelAttribute("pageBean") PageBean<Blacklist> pageBean ){

		try {
			blacklistService.getResult(conditions.getMap(), pageBean);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "customer/black";
			else
				return "customer/blackList";
		} catch (Exception e) {
			return "/error500";
		}
	}
	
	/**
	 * 添加
	 */

	@RequestMapping(value = "add.action", method = RequestMethod.GET)
	public String addBlacklist(Model model, Blacklist blacklist) {

		try {
		
			boolean isSuccess = blacklistService.createNewBlacklist(blacklist);
			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "添加成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "添加失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "添加失败!"));
		}

		return "resultjson";
	}
	
	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.GET)
	public String updateBlacklist(Model model, Blacklist blacklist) {

		try {

			Blacklist toUpdateBlacklist = blacklistService.getBlacklistById(blacklist.getId());

			toUpdateBlacklist.setCustomerId(blacklist.getCustomerId());
			toUpdateBlacklist.setIp(blacklist.getIp());
			toUpdateBlacklist.setEndDate(blacklist.getEndDate());;
            toUpdateBlacklist.setDescription(blacklist.getDescription());
			boolean isSuccess = blacklistService.updateBlacklist(toUpdateBlacklist);

			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}

		return "iews/resultjson";

	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String deletBlacklist(Model model, Integer id) {

		boolean isSuccess = blacklistService.deleteBlacklistById(id);
		String message = "failure";
		Integer code = -1;

		if (isSuccess) {
			message = "success";
			code = 200;
		}
		model.addAttribute("message", message);
		model.addAttribute("code", code);

		return "iews/message";

	}


}
