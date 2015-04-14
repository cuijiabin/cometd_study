package com.xiaoma.kefu.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.xiaoma.kefu.service.FunctionService;
import com.xiaoma.kefu.service.RoleService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

/**
 * 
 * @author yangixaofeng
 * 
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private DepartmentService deptService;

	@Autowired
	private FunctionService funcService;

	/**
	 * User login
	 * 
	 * @param name
	 * @param password
	 * @param session
	 */
	@RequestMapping(value = "login.action", method = RequestMethod.POST)
	public String login(HttpSession session, String loginName, String password,String yzm,Model model) {
	    System.out.println(loginName);
	    String yanzheng=session.getAttribute("randomCode").toString();
	    System.out.println(yanzheng);
	    Date oldTime= (Date) session.getAttribute("yzmtime");
	    Date newTime= new Date();
	     long count=newTime.getTime()-oldTime.getTime();
	    if(count<400000){
	    	 if(yzm.equals(yanzheng)){
	 	    	User user=userService.login(loginName,password);
	 	    	if(user!=null){
	 	    		session.setAttribute("user", user);
	 	    		model.addAttribute("result", Ajax.JSONResult(0, "登陆成功!"));
	 	    	}else{
	 	    		model.addAttribute("result", Ajax.JSONResult(3, "登录名或者密码错误!"));

	 	    	}
	 	    }else{
		    	model.addAttribute("result", Ajax.JSONResult(1, "验证码错误!"));
		    }	
	    }else{
	    	model.addAttribute("result", Ajax.JSONResult(2, "验证码过期,请刷新重登!"));
	    }
		return "resultjson";
	}
	/**
	 *进入主页的树列表展示
	 * 
	 * @param name
	 * @param password
	 * @param session
	 */
	@RequestMapping(value = "main.action", method = RequestMethod.GET)
	public String main(HttpSession session,Model model) {
		List list = funcService.findFuncOne();
		model.addAttribute("topList", list);
		return "index";
	}

	/**
	 * User login
	 * 
	 * @param name
	 * @param password
	 * @param session
	 */
	@RequestMapping(value = "demo.action", method = RequestMethod.GET)
	public String demo(HttpSession session) {
		// Assert.notNull(name, "userName can not be null!");
		// Assert.notNull(password, "password can not be null!");
		// model.addAttribute("msg", "登录名或者密码为空!");
		// User user = userService.login(name, password);
		// model.addAttribute("msg", "登录名或者密码不正确!");
		// if (user != null) {
		// session.setAttribute("currentUser", user);
		// return "/views/welcome";
		// } else {
		// logger.debug("login failed!username or password is incorrect.");
		// return "/views/login";
		// }
		return "demo";
	}

	/**
	 * 查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */

	// @RequestMapping(value = "find.action", method = RequestMethod.GET)
	// public String queryAll(Model model, String userName, String phone,
	// Integer currentPage, Integer pageRecorders) {
	// try{
	// currentPage = (currentPage == null) ? 1 : currentPage;
	// pageRecorders = (pageRecorders == null) ? 10 : pageRecorders;
	// PageBean<User> pageBean = userService.getResultByuserNameOrPhone(
	// currentPage, pageRecorders, userName, phone);
	//
	// model.addAttribute("list", pageBean.getObjList());
	// model.addAttribute("pageBean", pageBean);
	// model.addAttribute("phone", phone);
	// model.addAttribute("userName", userName);
	// return "/set/govern/userList";
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

	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(MapEntity conditions, Model model,
			@ModelAttribute("pageBean") PageBean<User> pageBean) {
		try {
			userService.getResult(conditions.getMap(), pageBean);
			System.out.println(pageBean.getObjList());
			System.out.println(conditions.getMap().get("status"));
			model.addAttribute("status",conditions.getMap().get("status"));
			if (conditions == null || conditions.getMap() == null || conditions.getMap().get("typeId") == null)
				return "/set/govern/user";
			else
				return "/set/govern/userList";
		} catch (Exception e) {
			model.addAttribute("message", "查询失败,请刷新重试!");
			logger.error(e.getMessage());
			return "/error";
		}
	}

	/**
	 * 跳转到添加页面
	 */

	@RequestMapping(value = "add.action", method = RequestMethod.GET)
	public String changadd(Model model, User user) {
		try {
			List<Role> rlist = (List<Role>) roleService.findRole();
			List<Department> dlist = deptService.findDept();
			model.addAttribute("deptList", dlist);
			model.addAttribute("roleList", rlist);
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
		}

		return "/set/govern/addUser";
	}

	/**
	 * 添加到数据库
	 */

	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String addUser(Model model, @ModelAttribute("user") User user) {
		try {
			// 对用户密码进行加密！
			String password = new String(DigestUtils.md5Hex(user.getPassword()
					.getBytes("UTF-8")));
			user.setPassword(password);

			Integer isSuccess = userService.createNewUser(user);
			if (isSuccess != 0) {
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
	 * 查询出角色列表
	 */
	@RequestMapping(value = "role.action", method = RequestMethod.GET)
	public String role(Model model) {
		try {
			List<Role> list = (List<Role>) roleService.findRole();
			System.out.println(list);
			JSONArray json = JSONArray.fromObject(list);
			model.addAttribute("result", json.toString());
			return "views/resultjson";
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
			return "views/resultjson";
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "/views/error500";
		}
	}

	/**
	 * 在弹出的对话框中显示详细信息
	 */
	@RequestMapping(value = "detail.action", method = RequestMethod.GET)
	public String userDetail(Model model, Integer id,Integer type) {
		try {
			User user = userService.getUserById(id);
			List<Role> rlist = (List<Role>) roleService.findRole();
			List<Department> dlist = deptService.findDept();
			model.addAttribute("deptList", dlist);
			model.addAttribute("roleList", rlist);
			model.addAttribute("user", user);
			if(type==5){
				return "/set/govern/checkUser";
			}else{
			    return "/set/govern/addUser";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "/views/error500";
		}
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.POST)
	public String updateUser(Model model, @ModelAttribute("user") User user) {

		try {
			Integer isSuccess = userService.updateUser(user);

			if (isSuccess == 1) {
				model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}

		return "resultjson";

	}

	/**
	 * 员工离职
	 * 
	 * @return
	 */
	@RequestMapping(value = "leave.action", method = RequestMethod.POST)
	public String updateLeave(Model model, String ids,Integer status) {
		if(ids==null ||status==null){
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "resultjson";
		}
		try {
			Integer isSuccess = userService.leaveUser(ids,status);

			if (isSuccess == 1) {
				model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}

		return "resultjson";
	}

	/**
	 * 员工转移部门
	 * 
	 * @return
	 */
	@RequestMapping(value = "tradept.action", method = RequestMethod.POST)
	public String updatedept(Model model, String ids, Integer deptId) {
		try {
			Integer isSuccess = userService.tradeUser(ids, deptId);

			if (isSuccess == 1) {
				model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}

		return "resultjson";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.POST)
	public String deletUser(Model model, String ids) {

		try {
			Integer isSuccess = userService.deleteUserById(ids);

				if (isSuccess == 1) {
					model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
				} else {
					model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
				}
			} catch (Exception e) {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}

			return "resultjson";

	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "repass.action", method = RequestMethod.GET)
	public String updateUser(Model model, Integer id, String password) {
		try {

			User toUpdateUser = userService.getUserById(id);
			toUpdateUser.setPassword(DigestUtils.md5Hex(password
					.getBytes("UTF-8")));
			Integer isSuccess = userService.updateUser(toUpdateUser);

			if (isSuccess == 1) {
				model.addAttribute("result", Ajax.JSONResult(0, "密码重置成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "密码重置失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "密码重置失败!"));
		}

		return "resultjson";

	}

	@RequestMapping(value = "logout.action", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("currentUser");
		return "/views/login";
	}

	/**
	 * 
	 * 精确查询
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping(value = "check.action", method = RequestMethod.GET)
	public String queryByCheck(Model model, User user)
			throws UnsupportedEncodingException {

		try {
			if (user == null || (StringHelper.isEmpty(user.getLoginName()))) {
				model.addAttribute("result", Ajax.toJson(1, "缺少参数，请重新提交！"));
				return "resultjson";
			}
			Integer count = userService.checkUser(user);
			if (count != null && count == 0) {
				model.addAttribute("result", Ajax.toJson(0, "该工号名可以使用！"));
			} else {
				model.addAttribute("result", Ajax.toJson(1, "该工号名已存在！"));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			model.addAttribute("result", Ajax.toJson(1, "查询出错啦，请刷新后重试！"));
		}
		return "resultjson";
	}

	@RequestMapping(value = "checkMsg.action", method = RequestMethod.GET)
	public String checkMsg(Model model, String ln, Integer uId, String phone,
			String msg) throws UnsupportedEncodingException {
		try {
			if (StringHelper.isEmpty(phone) || StringHelper.isEmpty(msg)) {
				model.addAttribute("result", Ajax.toJson(1, "缺少参数，请重新提交！"));
				return "resultjson";
			}
			model.addAttribute("result", Ajax.toJson(0, "手机号码校验成功！"));
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			model.addAttribute("result", Ajax.toJson(1, "查询出错啦，请刷新后重试！"));
		}
		return "resultjson";
	}

}
