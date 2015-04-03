package com.xiaoma.kefu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Blacklist;
import com.xiaoma.kefu.service.BlacklistService;
import com.xiaoma.kefu.util.Ajax;


/**
 * @author frongji
 * @time 2015年4月2日上午11:21:05
 *
 */
@Controller
@RequestMapping(value = "blacklist")
public class BlacklistController {
   
	@Autowired
	private BlacklistService blacklistService;
	
	/**
	 * 查询所有
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(Model model, Long customerId, Integer userId,String description,
			Integer currentPage, Integer pageRecorders) {

		currentPage = (currentPage == null) ? 1 : currentPage;
		pageRecorders = (pageRecorders == null) ? 10 : pageRecorders;
//		PageBean<Customer> pageBean = blacklistService.getResultByConditions(currentPage, pageRecorders,
	//			customerId, userId,description);

//		model.addAttribute("list", pageBean.getObjList());
//		model.addAttribute("pageBean", pageBean);
		model.addAttribute("customerId", customerId);
		model.addAttribute("userId", userId);
		model.addAttribute("userId",userId);

		return "xx/customerList";
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

		return "iews/resultjson";
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
