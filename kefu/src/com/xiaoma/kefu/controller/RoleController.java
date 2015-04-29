package com.xiaoma.kefu.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.log4j.Logger;

import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.LoginLog;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.service.DepartmentService;
import com.xiaoma.kefu.service.RoleService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;

/**
 * 角色管理 controller
 * 
 * @author yangxiaofeng
 * 
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "role")
public class RoleController {

	private Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	private DepartmentService deptService;

	// /**
	// * 角色查询
	// * @param model
	// * @param condition
	// * @param currentPage
	// * @param pageRecorders
	// * @return
	// */
	// @RequestMapping(value = "list.action", method = RequestMethod.GET)
	// public String queryAll(Model model, String loginName, String phone,
	// Integer currentPage, Integer pageRecorders) {
	// try{
	// currentPage = (currentPage == null) ? 1 : currentPage;
	// pageRecorders = (pageRecorders == null) ? 10 : pageRecorders;
	// PageBean<Role> pageBean = roleService.getResult(currentPage,
	// pageRecorders);
	// System.out.println(pageBean.getObjList());
	// model.addAttribute("list", pageBean.getObjList());
	// model.addAttribute("pageBean", pageBean);
	// System.out.println(pageBean.getObjList());
	// System.out.println(pageBean);
	// return "/set/govern/roleList";
	// }catch(Exception e){
	// logger.error(e.getMessage());
	// model.addAttribute("error","出错了,请刷新页面重试！");
	// return "/views/error500";
	// }
	// }

	/**
	 * 查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */

	@RequestMapping(value = "list.action", method = RequestMethod.GET)
	public String queryAll(MapEntity conditions, Model model,
			@ModelAttribute("pageBean") PageBean<Role> pageBean) {
		try {
			List<Department> list = deptService.findDept();
			Integer deptId=0;
			if(!list.isEmpty()){
				deptId=list.get(0).getId();
			}
			roleService.getResult(conditions.getMap(), pageBean);
			model.addAttribute("deptId",deptId);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "/set/govern/role/role";
			else
				return "/set/govern/role/roleList";
		} catch (Exception e) {
			model.addAttribute("message", "查询失败,请刷新重试!");
			logger.error(e.getMessage());
			return "/error";
		}
	}

	/**
	 * 跳转到页面
	 * 
	 * @param model
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "addRole.action", method = RequestMethod.GET)
	public String addRole(Model model, Role role) {

		return "/set/govern/role/addRole";
	}

	/**
	 * 添加
	 */

	@RequestMapping(value = "save.action", method = RequestMethod.GET)
	public String addUser(Model model, Role role) {
		try {
			Integer isSuccess = roleService.createNewUser(role);
			if (isSuccess != 0) {
				model.addAttribute("result", Ajax.JSONResult(0, "添加成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "添加失败!"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("result", Ajax.JSONResult(1, "添加失败!"));
		}

		return "resultjson";
	}

	/**
	 * 检查用户名是否存在
	 */
	@RequestMapping(value = "check.action", method = RequestMethod.GET)
	public String queryByCheck(Model model, Role role)
			throws UnsupportedEncodingException {

		try {
			Integer count = roleService.checkRole(role);
			if (count != null && count == 0) {
				model.addAttribute("result", Ajax.toJson(0, "该用户名可以使用！"));
			} else {
				model.addAttribute("result", Ajax.toJson(1, "该用户名已存在！"));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			model.addAttribute("result", Ajax.toJson(1, "查询出错啦，请刷新后重试！"));
		}
		return "resultjson";
	}

	/**
	 * 在弹出的对话框中显示详细信息
	 */
	@RequestMapping(value = "detail.action", method = RequestMethod.GET)
	public String roleDetail(Model model, Integer id) {

		Role role = roleService.getRoleById(id);
		System.out.println(role);
		model.addAttribute("role", role);

		return "/set/govern/role/addRole";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.GET)
	public String updateUser(Model model, Role role) {
		try {
			Integer isSuccess = roleService.updateRole(role);

			if (isSuccess == 1) {
				model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}

		return "resultjson";

	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.POST)
	public String deleteProduct(Model model, Integer id) {

		try {
			Integer isSuccess = roleService.deleteRoleById(id);
			if (isSuccess == 1) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else if(isSuccess==3){
				model.addAttribute("result", Ajax.JSONResult(3, "该角色有员工在使用,不能删除!"));
			}else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}

		return "resultjson";
	}

}
