package com.xiaoma.kefu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.Keyboard;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.DepartmentService;
import com.xiaoma.kefu.service.FunctionService;
import com.xiaoma.kefu.service.RoleService;
import com.xiaoma.kefu.util.Ajax;

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
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@RequestMapping(value = "tree.action", method = RequestMethod.GET)
	public String tree(Model model, Integer id,HttpSession session) {
		try {
			User user=(User) session.getAttribute("user");
			if(user==null)
				return"login";
			if (id != null) {
				List list = (List) CacheMan.getObject(CacheName.FUNCTIONTREEBYID, id);
				String codes = (String) CacheMan.getObject(CacheName.USERFUNCTION, user.getId());
				List newList =funcService.checkFuncOne(list, codes);
				JSONArray json = new JSONArray().fromObject(newList);
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
	@SuppressWarnings({ "rawtypes", "static-access" })
	@RequestMapping(value = "permit.action", method = RequestMethod.GET)
	public String findFunc(Model model, Integer roleId, Integer deptId,
			Integer status) {
		try {
			if (status == null)
				status = 1;
			if (roleId != null && deptId != null) {
				if (status == 3) {
					model.addAttribute("status", status);
				}
				Role role = roleService.getRoleById(roleId);
				List<Department> deptlist = deptService.findDept();
				List list = funcService.findFunction();
				String strs = funcService.checkedFunc(roleId, deptId);
				JSONArray json = new JSONArray().fromObject(list);
				System.out.println(json);
				model.addAttribute("deptId", deptId);
				model.addAttribute("role", role);
				model.addAttribute("list", deptlist);
				model.addAttribute("strs", strs);
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

	/**
	 * 保存权限的配置
	 */
	@RequestMapping(value = "saveFunc.action", method = RequestMethod.POST)
	public String saveFunc(Model model, Integer roleId, Integer deptId,
			String ids,HttpSession session) {
		try {
			if (roleId != null && deptId != null && ids != null) {
				Integer isSuccess = funcService.saveFunc(roleId, deptId, ids);
				if (isSuccess != 0) {
					model.addAttribute("result", Ajax.JSONResult(0, "保存成功!"));
				} else {
					model.addAttribute("result", Ajax.JSONResult(1, "保存失败!"));
				}
			}
			return "resultjson";
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "error";
		}
	}
	/**
	 * 跳转到快捷键的页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "key.action", method = RequestMethod.GET)
	public String key(Model model) {
		try {
	        return "/set/person/key";
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "error";
		}
	}
	/**
	 * 跳转到快捷键的页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveKeyboard.action", method = RequestMethod.GET)
	public String savekey(Model model,Keyboard keyboard,HttpSession session) {
		try {
			    User user = (User) session.getAttribute("user");
				Integer isSuccess = funcService.savekey(keyboard,user);
				if (isSuccess != 0) {
					model.addAttribute("result", Ajax.JSONResult(0, "保存成功!"));
				} else {
					model.addAttribute("result", Ajax.JSONResult(1, "保存失败!"));
				}
			return "resultjson";
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "error";
		}
	}
}