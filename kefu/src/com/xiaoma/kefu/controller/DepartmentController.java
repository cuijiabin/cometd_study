package com.xiaoma.kefu.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.DepartmentService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;

/**
 * 部门管理 controller
 * 
 * @author yangxiaofeng
 * 
 */
@Controller
@RequestMapping(value = "dept")
public class DepartmentController {
	private Logger logger = Logger.getLogger(DepartmentController.class);
	@Autowired
	private DepartmentService deptService;

	@Autowired
	private UserService userService;

	// /**
	// * 部门查询
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
	// PageBean<Department> pageBean = deptService.getResult(currentPage,
	// pageRecorders);
	// System.out.println(pageBean.getObjList());
	// model.addAttribute("list", pageBean.getObjList());
	// model.addAttribute("pageBean", pageBean);
	// System.out.println(pageBean.getObjList());
	// System.out.println(pageBean);
	// return "/set/govern/deptList";
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
			@ModelAttribute("pageBean") PageBean<Department> pageBean) {
		try {
			deptService.getResult(conditions.getMap(), pageBean);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "/set/govern/dept/dept";
			else
				return "/set/govern/dept/deptList";
		} catch (Exception e) {
			model.addAttribute("message", "查询失败,请刷新重试!");
			logger.error(e.getMessage());
			return "/error";
		}
	}

	/**
	 * 查询部门里面的员工
	 * 
	 * @param model
	 * @param loginName
	 * @param phone
	 * @param currentPage
	 * @param pageRecorders
	 * @return
	 */
	@RequestMapping(value = "findDeptUser.action", method = RequestMethod.GET)
	public String querydeptUser(Model model, Integer deptId) {
		try {
			List list= userService.getResultDept(deptId);
            model.addAttribute("list",list);
			return "/set/govern/dept/deptUser";
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "/views/error500";
		}
	}

	/**
	 * 查询出所有的部门
	 */
	@RequestMapping(value = "dept.action", method = RequestMethod.GET)
	public String findDept(Model model) {
		try {
			List<Department> list = deptService.findDept();
			JSONArray json = JSONArray.fromObject(list);
			model.addAttribute("result", json.toString());
			System.out.println(json.toString());
			return "resultjson";
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "/views/error500";
		}
	}

	/**
	 * 跳转到页面
	 * 
	 * @param model
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "addDept.action", method = RequestMethod.GET)
	public String addRole(Model model, Department dept) {
		Integer num = deptService.getMaxNum();
		System.out.println(num);
		dept.setSortNum(num + 1);
		model.addAttribute("dept", dept);
		return "/set/govern/dept/addDept";
	}

	/**
	 * 添加部门
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.GET)
	public String addUser(Model model, Department dept) {
		try {
			Integer isSuccess = deptService.createNewDept(dept);
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
	 * 检查用部门是否存在
	 */
	@RequestMapping(value = "check.action", method = RequestMethod.GET)
	public String queryByCheck(Model model, Department dept)
			throws UnsupportedEncodingException {

		try {
			Integer count = deptService.checkDept(dept);
			if (count != null && count == 0) {
				model.addAttribute("result", Ajax.toJson(0, "该部门名可以使用！"));
			} else {
				model.addAttribute("result", Ajax.toJson(1, "该部门名已存在！"));
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
	public String deptDetail(Model model, Integer id) {

		Department dept = deptService.getDeptById(id);
		System.out.println(dept);
		model.addAttribute("dept", dept);

		return "/set/govern/dept/addDept";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.GET)
	public String updateUser(Model model, Department dept) {
		System.out.println(dept.getName());
		try {
			Integer isSuccess = deptService.updateDept(dept);

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
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String deleteProduct(Model model, Integer id) {

		try {
			Integer isSuccess = deptService.deleteDeptById(id);
			if (isSuccess == 1) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			}else if(isSuccess==3){
				model.addAttribute("result", Ajax.JSONResult(3, "该部门有员工在,不能删除!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}

		return "resultjson";
	}

}
