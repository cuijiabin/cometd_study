package com.xiaoma.kefu.controller;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.service.RoleService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.PageBean;

/**
 * 角色管理 controller
 * @author yangxiaofeng
 *
 */
@Controller
@RequestMapping(value="role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	/**
	 * 角色查询
	 * @param model
	 * @param condition
	 * @param currentPage
	 * @param pageRecorders
	 * @return
	 */
	@RequestMapping(value = "list.action", method = RequestMethod.GET)
	public String queryAll(Model model, String loginName, String phone,
			Integer currentPage, Integer pageRecorders) {
     try{
		currentPage = (currentPage == null) ? 1 : currentPage;
		pageRecorders = (pageRecorders == null) ? 10 : pageRecorders;
		PageBean<Role> pageBean = roleService.getResult(currentPage, pageRecorders);
        System.out.println(pageBean.getObjList());
		model.addAttribute("list", pageBean.getObjList());
		model.addAttribute("pageBean", pageBean);
		System.out.println(pageBean.getObjList());
        System.out.println(pageBean);
		return "/views/admin/roleList";
     }catch(Exception e){
			model.addAttribute("error","对不起出错了");
			return "/views/error500";
		}
	}
	
	/**
	 * 添加
	 */

	@RequestMapping(value = "add.action", method = RequestMethod.GET)
	public String addUser(Model model, Role role) {
		try {
			boolean isSuccess = roleService.createNewUser(role);
			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "添加成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "添加失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "添加失败!"));
		}

		return "/views/resultjson";
	}
	/**
	 * 检查用户名是否存在
	 */
	@RequestMapping(value = "check.action", method = RequestMethod.GET)
	public String queryByCheck(Model model, Role role) throws UnsupportedEncodingException {
		
		try {
			Integer count = roleService.checkRole(role);
			if(count!=null && count==0){
				model.addAttribute("result", Ajax.toJson(0, "该用户名可以使用！"));
			}else{
				model.addAttribute("result", Ajax.toJson(1, "该用户名已存在！"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("result", Ajax.toJson(1, "查询出错啦，请刷新后重试！"));
		}
		return "/views/resultjson";
	}

	/**
	 * 在弹出的对话框中显示详细信息
	 */
	@RequestMapping(value = "detail.action", method = RequestMethod.GET)
	public String roleDetail(Model model, Integer id) {

		Role role = roleService.getRoleById(id);
		System.out.println(role);
		JSONObject jsonObject = JSONObject.fromObject(role);
		model.addAttribute("result", jsonObject.toString());

		return "views/resultjson";
	}
	
	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.GET)
	public String updateUser(Model model, Role role) {
           System.out.println(role.getName());
		try {

			Role toUpdateRole = roleService.getRoleById(role.getId());

			toUpdateRole.setName(role.getName());

			boolean isSuccess = roleService.updateRole(toUpdateRole);

			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}

		return "/views/resultjson";

	}
	
	/**
	 * 删除
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String deleteProduct(Model model, Integer id) {

		try {
			boolean isSuccess = roleService.deleteRoleById(id);
			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除产品成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除产品失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "删除产品失败!"));
		}

		return "/views/resultjson";
	}
	
}
