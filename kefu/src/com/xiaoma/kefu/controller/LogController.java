package com.xiaoma.kefu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.service.LoginLogService;
import com.xiaoma.kefu.service.RoleService;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;

/**
 * 
 * @author yangixaofeng
 * 
 */
@Controller
@RequestMapping(value = "log")
public class LogController {

	private Logger logger = Logger.getLogger(LogController.class);

	@Autowired
	private LoginLogService loginLogService;

	@Autowired
	private RoleService roleService;

	/**
	 * 查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */

	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(MapEntity conditions,Model model,
			@ModelAttribute("pageBean") PageBean<LoginLog> pageBean) {
		try {
			loginLogService.getResult(conditions.getMap(), pageBean);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "/set/log/loginLog";
			else
				return "/set/log/loginLogList";
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("message", "登录日志查询失败，请刷新后重试!");
			return "/error";
		}
	}

}
