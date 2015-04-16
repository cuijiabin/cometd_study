package com.xiaoma.kefu.controller;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.service.DepartmentService;
import com.xiaoma.kefu.service.FunctionService;
import com.xiaoma.kefu.service.RoleService;

/**
 * 
 * @author yangxiaofeng
 * @time 2015年4月7日 10:18:00
 */
@Controller
@RequestMapping("function")
public class FunctionController {

	private Logger logger = Logger.getLogger(FunctionController.class);

	@Autowired
	private FunctionService funcService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DepartmentService deptService;

	// 查询各个级别的树
	@SuppressWarnings("static-access")
	@RequestMapping(value = "tree.action", method = RequestMethod.GET)
	public String tree(Model model, Integer id) {
		try {
			if (id != null) {
				List list = funcService.findTree(id);
				JSONArray json = new JSONArray().fromObject(list);
				System.out.println(json);
				model.addAttribute("json", json.toString());
				return "left";
			} else {
				return "null";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "error";
		}
	}

	/**
	 * 配置权限的查询
	 */
	@SuppressWarnings({ "rawtypes", "unused", "static-access" })
	@RequestMapping(value = "permit.action", method = RequestMethod.GET)
	public String permit(Model model, Integer id) {
		try {
			if (id != null) {
				Role role = roleService.getRoleById(id);
				List<Department> deptlist = deptService.findDept();
				List list = funcService.findFunction();
				//List list = funcService.findTree(5);
				JSONArray json = new JSONArray().fromObject(list);
				System.out.println(json);
				model.addAttribute("role",role);
				model.addAttribute("list",deptlist);
				model.addAttribute("json", json.toString());
				return "/set/govern/func/func";
			} else {
				return "null";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "error";
		}
	}
}
