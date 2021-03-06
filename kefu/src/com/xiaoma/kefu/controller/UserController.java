package com.xiaoma.kefu.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.model.Function;
import com.xiaoma.kefu.model.Role;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.DepartmentService;
import com.xiaoma.kefu.service.FunctionService;
import com.xiaoma.kefu.service.RoleService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.thread.AddLoginLogThread;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.CheckCodeUtil;
import com.xiaoma.kefu.util.CookieUtil;
import com.xiaoma.kefu.util.JsonUtil;
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
	 * 用户登录，密码验证，错误四次锁定
	 * @param name
	 * @param password
	 * @param session
	 */
	@RequestMapping(value = "login.action", method = RequestMethod.POST)
	public String login(HttpSession session, HttpServletRequest request,
			String loginName, String password, String yzm, Model model) {
		try {
			String yanzheng = (String) session.getAttribute("randomCode");
			Date oldTime = (Date) session.getAttribute("yzmtime");
			if (yanzheng == null || oldTime == null) {
				model.addAttribute("result", Ajax.JSONResult(3, "请刷新验证码重新输入!"));
				return "resultjson";
			}
			Date newTime = new Date();
			long count = newTime.getTime() - oldTime.getTime();
			if (count < 400000) {
				if (yzm.equals(yanzheng)) {
					User user = userService.login(loginName);
					if (user != null) {
						if (user.getIsLock() == 0) {
							String password1 = new String(DigestUtils.md5Hex(password.getBytes("UTF-8")));
							if (password1.equals(user.getPassword())) {
								session.setAttribute(CacheName.USER, user);
								user.setOnLineStatus(1);
								CacheMan.update(CacheName.SUSER, user.getId(),user);
								User user2 = (User) CacheMan.getObject(CacheName.SUSER,user.getId());
								System.out.println(user2);
								model.addAttribute("result",Ajax.JSONResult(0, "登录成功!"));
								Thread thread = new AddLoginLogThread(user.getId(),CookieUtil.getIpAddr(request));
								if (thread != null)
									thread.start();
							} else {
								Object obj = CacheMan.getObject(CacheName.LOGINCOUNT, "");
								if (obj == null) {
									CacheMan.addObjectTimer(CacheName.LOGINCOUNT, "", 1, 6);
								} else {
									Integer num = (Integer) CacheMan.getObject(CacheName.LOGINCOUNT, "");
									if (num == 4)
										userService.updateUser("1", user);
									CacheMan.addObjectTimer(CacheName.LOGINCOUNT, "", num + 1,6);
								}
								model.addAttribute("result",
										Ajax.JSONResult(6, "密码错误请重新输入!"));
							}
						} else {
							model.addAttribute("result",
									Ajax.JSONResult(5, "账户已锁,请联系管理员!"));
						}
					} else {
						model.addAttribute("result",
								Ajax.JSONResult(4, "用户不存在请重新输入!"));
					}
				} else {
					model.addAttribute("result", Ajax.JSONResult(1, "验证码错误!"));
				}
			} else {
				model.addAttribute("result",
						Ajax.JSONResult(2, "验证码过期,请刷新重登录!"));
			}
			return "resultjson";
		} catch (Exception e) {
			model.addAttribute("message", "查询失败,请刷新重试!");
			logger.error(e.getMessage());
			return "/error";
		}
	}


	/**
	 * 进入主页的树列表展示
	 * 
	 * @param name
	 * @param password
	 * @param session
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "main.action", method = RequestMethod.GET)
	public String main(HttpSession session, Model model, Integer typeId) {
	try{
		User user = (User) session.getAttribute(CacheName.USER);		
		if (user == null)
			return "login";
		String codes = (String) CacheMan.getObject(CacheName.USERFUNCTION,
				user.getId());
		List<Function> list = (List<Function>) CacheMan.getList(CacheName.SYSFUNCTIONONE, "",Function.class);
		List<Function> newList = funcService.checkFuncOne(list, codes);
		model.addAttribute("topList", newList);
			// 根据typeId判断初始加载哪个页面。哪个顶部标签选中。
			if (typeId == null){
				boolean b = CheckCodeUtil.isCheckFunc(user.getId(), "f_dialog_pt");
				if(b){
					return "redirect:/dialogue/user.action";
				}
			}
			Function function = (Function) CacheMan.getObject(CacheName.FUNCTION,
					typeId==null?2:typeId);
			model.addAttribute("func", function);
			return "index";


	}catch(Exception e){
		model.addAttribute("message", "查询失败,请刷新重试!");
		logger.error(e.getMessage());
		return "/error";
	}
}

	/**
	 * 进入demo处理页面
	 * 
	 * @param name
	 * @param password
	 * @param session
	 */
	@RequestMapping(value = "demo.action", method = RequestMethod.GET)
	public String demo(HttpSession session) {
		return "demo";
	}

	/**
	 * 查询员工的列表
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */

	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String find(MapEntity conditions, Model model,
			@ModelAttribute("pageBean") PageBean<User> pageBean) {
		try {
			
			List<String> strList = JedisTalkDao.getSwitchList();
			List<Integer> onLineUserIds = JsonUtil.convertString2Integer(strList);
			List<Department> list = deptService.findDept();
			userService.getResult(conditions.getMap(), pageBean);
			model.addAttribute("deptList", list);
			model.addAttribute("status", conditions.getMap().get("status"));
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "/set/govern/user/user";
			else
				return "/set/govern/user/userList";
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
			return "error";
		}

		return "/set/govern/user/addUser";
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
			logger.error(e.getMessage());
			model.addAttribute("error", "添加失败");
			return "error";
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
			return "resultjson";
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "error";
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
			return "error500";
		}
	}

	/**
	 * 在弹出的对话框中显示详细信息
	 */
	@RequestMapping(value = "detail.action", method = RequestMethod.GET)
	public String userDetail(Model model, Integer id, Integer type) {
		System.out.println(type);
		try {
			User user = userService.getUserById(id);
			List<Role> rlist = (List<Role>) roleService.findRole();
			List<Department> dlist = deptService.findDept();
			model.addAttribute("user", user);
			model.addAttribute("deptList", dlist);
			model.addAttribute("roleList", rlist);
			if (type == null) {
				return "/set/govern/user/updateUser";
			} else {
				return "/set/govern/user/checkUser";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "error500";
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
			Integer isSuccess = userService.updateUser(null, user);

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
	public String updateLeave(Model model, String ids, Integer status) {
		if (ids == null || status == null) {
			model.addAttribute("error", "出错了,请刷新页面重试！");
			return "resultjson";
		}
		try {
			Integer isSuccess = userService.leaveUser(ids, status);
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
	public String updateUser(Model model, HttpSession session, String password,
			String oldpass) {
		try {
			User user = (User) session.getAttribute(CacheName.USER);
			if (user == null)
				return "login";
			if (password != null) {
				Integer isSuccess = userService.updateUser(password, user);
				if (isSuccess == 1) {
					model.addAttribute("result", Ajax.JSONResult(0, "密码重置成功!"));
				} else {
					model.addAttribute("result", Ajax.JSONResult(1, "密码重置失败!"));
				}
			} else {
				model.addAttribute("result", Ajax.JSONResult(2, "密码为空请重新输入"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "密码重置失败!"));
		}

		return "resultjson";

	}

	/**
	 * 工号验证
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

	/**
	 * 用户的旧密码验证
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping(value = "checkPass.action", method = RequestMethod.GET)
	public String checkPass(Model model, HttpSession session, String oldpass)
			throws UnsupportedEncodingException {

		try {
			User user = (User) session.getAttribute("user");
			if (user == null)
				return "login";
			if (oldpass != null) {
				User usern = userService.getUserById(user.getId());
				String password1 = usern.getPassword();
				String mdpass = DigestUtils.md5Hex(oldpass.getBytes("UTF-8"));
				if (password1.equals(mdpass)) {
					model.addAttribute("result", Ajax.toJson(0, "验证正确！"));
				} else {
					model.addAttribute("result", Ajax.toJson(1, "旧密码输入不正确！"));
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			model.addAttribute("result", Ajax.toJson(1, "查询出错啦，请刷新后重试！"));
		}
		return "resultjson";
	}
	
	/**
	 * 退出系统
	 * 
	 * @param name
	 * @param password
	 * @param session
	 */
	@RequestMapping(value = "exit.action")
	public String exit(HttpSession session, Model model) {
		User user = (User) session.getAttribute(CacheName.USER);
		user.setOnLineStatus(2);
		CacheMan.update(CacheName.SUSER, user.getId(),user);
		session.removeAttribute(CacheName.USER);
		return "login";
	}

	/**
	 * 缓存清除
	 * 
	 * @param name
	 *            表名
	 */
	@RequestMapping(value = "clear.action")
	public String clearTable(Model model, String name) {
		List<DictItem> list = DictMan.getDictList(name);
		if (list != null && list.size() > 0) {
			for (DictItem item : list)
				DictMan.clearItemCache(item.getCode(), item.getItemCode());
		}
		DictMan.clearTableCache(name);
		model.addAttribute("result", Ajax.toJson(0, "清除成功!"));
		return "resultjson";
	}
	/**
	 * 缓存清除
	 * 
	 * @param name
	 *            表名
	 */
	@RequestMapping(value = "error.action")
	public String error(Model model, String name) {
		model.addAttribute("message","错误信息展示一下！");
		return "error";
	}

	/**
	 * 个人信息展示
	 * 
	 * @param name
	 * @param password
	 * @param session
	 */
	@RequestMapping(value = "person.action")
	public String person(HttpSession session, Model model) {
		User user = (User) session.getAttribute(CacheName.USER);
		if(user==null)
	    return "login";
		model.addAttribute("user", user);
		return "set/person/person";
	}

	/**
	 * 个人信息展示
	 * 
	 */
	@RequestMapping(value = "password.action")
	public String password(HttpSession session, Model model) {
		return "set/person/pass";
	}
}
