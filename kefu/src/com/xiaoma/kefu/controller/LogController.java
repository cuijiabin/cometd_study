package com.xiaoma.kefu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.model.OperateLog;
import com.xiaoma.kefu.service.LoginLogService;
import com.xiaoma.kefu.service.OperateLogService;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;

/**
 * 
 * @author hanyu
 * 
 */
@Controller
@RequestMapping(value = "log")
public class LogController {

	private Logger logger = Logger.getLogger(LogController.class);

	@Autowired
	private LoginLogService loginLogService;
	@Autowired
	private OperateLogService operateLogService;

	/**
	 * 查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */

	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String find(MapEntity conditions,Model model,
			@ModelAttribute("pageBean") PageBean<LoginLog> pageBean) {
		try {
			loginLogService.getResult(conditions.getMap(), pageBean);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "/set/log/loginLog";
			else
				return "/set/log/loginLogList";
		} catch (Exception e) {
			model.addAttribute("message", "查询失败,请刷新重试!");
			logger.error(e.getMessage());
			return "/error";
		}
	}
	@RequestMapping(value = "findLog.action", method = RequestMethod.GET)
	public String findLog(MapEntity conditions,Model model,
			@ModelAttribute("pageBean") PageBean<OperateLog> pageBean) {
		try {
			operateLogService.getResult(conditions.getMap(), pageBean);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "/set/log/operateLog";
			else
				return "/set/log/operateLogList";
		} catch (Exception e) {
			model.addAttribute("message", "查询失败,请刷新重试!");
			logger.error(e.getMessage());
			e.printStackTrace();
			return "/error";
		}
	}

}
