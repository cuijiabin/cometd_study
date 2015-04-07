package com.xiaoma.kefu.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.DepartmentService;
import com.xiaoma.kefu.service.RoleService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.Ajax;
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

	/**
	 * User login
	 * 
	 * @param name
	 * @param password
	 * @param session
	 */
	@RequestMapping(value = "login.action", method = RequestMethod.GET)
	public String login(HttpSession session, String name, String password,
			Model model) {
//		Assert.notNull(name, "userName can not be null!");
//		Assert.notNull(password, "password can not be null!");
//		model.addAttribute("msg", "登录名或者密码为空!");
//		User user = userService.login(name, password);
//		model.addAttribute("msg", "登录名或者密码不正确!");
//		if (user != null) {
//			session.setAttribute("currentUser", user);
//			return "/views/welcome";
//		} else {
//			logger.debug("login failed!username or password is incorrect.");
//			return "/views/login";
//		}
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
//		Assert.notNull(name, "userName can not be null!");
//		Assert.notNull(password, "password can not be null!");
//		model.addAttribute("msg", "登录名或者密码为空!");
//		User user = userService.login(name, password);
//		model.addAttribute("msg", "登录名或者密码不正确!");
//		if (user != null) {
//			session.setAttribute("currentUser", user);
//			return "/views/welcome";
//		} else {
//			logger.debug("login failed!username or password is incorrect.");
//			return "/views/login";
//		}
		return "demo";
	}

	/**
	 * 查询
	 * @param conditions
	 * @param pageBean
	 * @return
	 */

	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(Model model, String userName, String phone,
			Integer currentPage, Integer pageRecorders) {
      try{
		currentPage = (currentPage == null) ? 1 : currentPage;
		pageRecorders = (pageRecorders == null) ? 10 : pageRecorders;
		PageBean<User> pageBean = userService.getResultByuserNameOrPhone(
				currentPage, pageRecorders, userName, phone);

		model.addAttribute("list", pageBean.getObjList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("phone", phone);
		model.addAttribute("userName", userName);
        System.out.println(pageBean.getObjList());
		return "/views/admin/userList";
      }catch(Exception e){
			model.addAttribute("error","对不起出错了");
			return "/views/error500";
		}
	}

	/**
	 * 添加
	 */

	@RequestMapping(value = "add.action", method = RequestMethod.GET)
	public String addUser(Model model, User user) {
            User use =new User();
            use.setLoginName("xn102345");
            use.setPassword("666666");
            use.setUserName("蒋钦");
            use.setCardName("蒋老师");
            use.setDeptId(1);
            use.setRoleId(1);
            use.setCreateDate(new Date());
		try {
			// 对用户密码进行加密！
			String password = new String(DigestUtils.md5Hex(user.getPassword().getBytes("UTF-8")));
			use.setPassword(password);
            
			boolean isSuccess = userService.createNewUser(user
					);
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
	 * 查询出角色列表
	 */
	@RequestMapping(value = "role.action", method = RequestMethod.GET)
	public String role(Model model) {
      try{
		List<Role> list= (List<Role>) roleService.findRole();
		System.out.println(list);
		JSONArray json = JSONArray.fromObject(list);
		model.addAttribute("result", json.toString());
		return "views/resultjson";
      }catch(Exception e){
			model.addAttribute("error","对不起出错了");
			return "/views/error500";
		}
	}

	/**
	 * 查询出所有的部门
	 */
	@RequestMapping(value = "dept.action", method = RequestMethod.GET)
	public String findDept(Model model) {
	 try{	
		List<Department> list= deptService.findDept();
		JSONArray json = JSONArray.fromObject(list);
		model.addAttribute("result", json.toString());
        System.out.println(json.toString());
		return "views/resultjson";
	 }catch(Exception e){
			model.addAttribute("error","对不起出错了");
			return "/views/error500";
		}
	}
	/**
	 * 在弹出的对话框中显示详细信息
	 */
	@RequestMapping(value = "detail.action", method = RequestMethod.GET)
	public String userDetail(Model model, Integer id) {
       try{
		User user = userService.getUserById(id);
		JSONObject jsonObject = JSONObject.fromObject(user);
		model.addAttribute("result", jsonObject.toString());
		return "views/resultjson";
       }catch(Exception e){
			model.addAttribute("error","对不起出错了");
			return "/views/error500";
		}
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.GET)
	public String updateUser(Model model, User user) {

		try {
			boolean isSuccess = userService.updateUser(user);

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
	 * 员工离职
	 * 
	 * @return
	 */
	@RequestMapping(value = "leave.action", method = RequestMethod.GET)
	public String updateLeave(Model model, String ids) {
            if(ids==null){
            	ids="3";
            }
		try {
			boolean isSuccess = userService.leaveUser(ids);

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
	 * 员工转移部门
	 * 
	 * @return
	 */
	@RequestMapping(value = "tradept.action", method = RequestMethod.GET)
	public String updatedept(Model model, String ids,Integer deptId) {
            if(ids==null){
            	ids="3";
            }
            if(deptId==null){
            	deptId=2;
            }
		try {
			boolean isSuccess = userService.tradeUser(ids,deptId);

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
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String deletUser(Model model, Integer id) {
      try{
		boolean isSuccess = userService.deleteUserById(id);
		String message = "failure";
		Integer code = -1;

		if (isSuccess) {
			message = "success";
			code = 200;
		}
		model.addAttribute("message", message);
		model.addAttribute("code", code);

		return "/views/message";
      }catch(Exception e){
			model.addAttribute("error","对不起出错了");
			return "/views/error500";
		}

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
			toUpdateUser.setPassword(DigestUtils.md5Hex(password.getBytes("UTF-8")));
			boolean isSuccess = userService.updateUser(toUpdateUser);

			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "密码重置成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "密码重置失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "密码重置失败!"));
		}

		return "/views/resultjson";

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
	public String queryByCheck(Model model, User user) throws UnsupportedEncodingException {
		
		try {
			if(user == null || (StringHelper.isEmpty(user.getLoginName()) && StringHelper.isEmpty(user.getPhone()))){
				model.addAttribute("result", Ajax.toJson(1, "缺少参数，请重新提交！"));
				return "/views/resultjson";
			}
			Integer count = userService.checkUser(user);
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
	
	@RequestMapping(value = "checkMsg.action", method = RequestMethod.GET)
	public String checkMsg(Model model,String ln,Integer uId, String phone,String msg) throws UnsupportedEncodingException {
		try {
			if(StringHelper.isEmpty(phone) || StringHelper.isEmpty(msg)){
				model.addAttribute("result", Ajax.toJson(1, "缺少参数，请重新提交！"));
				return "/views/resultjson";
			}
			model.addAttribute("result", Ajax.toJson(0, "手机号码校验成功！"));
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("result", Ajax.toJson(1, "查询出错啦，请刷新后重试！"));
		}
		return "/views/resultjson";
	}
	
}
