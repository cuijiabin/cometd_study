package com.xiaoma.kefu.controller;

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

import com.xiaoma.kefu.model.BusiGroup;
import com.xiaoma.kefu.model.BusiGroupDetail;
import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.BusiGroupDetailService;
import com.xiaoma.kefu.service.BusiGroupService;
import com.xiaoma.kefu.service.DepartmentService;
import com.xiaoma.kefu.service.FunctionService;
import com.xiaoma.kefu.service.StyleService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.SysConst.RoleNameId;

/**
 * 业务分组	controller
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月13日下午7:53:59
**********************************
 */
@Controller
@RequestMapping(value = "busiGroup")
public class BusiGroupController {

	private Logger logger = Logger.getLogger(BusiGroupController.class);

	@Autowired
	private StyleService styleService;
	@Autowired
	private BusiGroupService busiGroupService;//业务分组
	@Autowired
	private BusiGroupDetailService busiGroupDetailService;//业务分组明细
	@Autowired
	private DepartmentService deptService;//部门
	@Autowired
	private UserService userService;//用户
	
	@Autowired
	private FunctionService funcService;
	
	
	/**
	 * 分组设置
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "viewGroup.action", method = RequestMethod.GET)
	public String viewGroup(Model model,Integer styleId) {
		try {
			List<BusiGroup> groupList = busiGroupService.findByStyleId(styleId);
			List<BusiGroupDetail> detailList = null;
			if(groupList!=null && groupList.size()>0){
				model.addAttribute("currentGroupId", groupList.get(0).getId());
				detailList = busiGroupDetailService.findByGroupId(groupList.get(0).getId());
			}
			//树
			JSONArray jsonTree = getDeptUserJsonTree();
			
			model.addAttribute("jsonTree", jsonTree.toString());
			model.addAttribute("groupList", groupList);
			model.addAttribute("detailList", detailList);
			model.addAttribute("styleId", styleId);
			return "style/rule/group";
		} catch (Exception e) {
			logger.error("StyleController.viewGroup ERROR",e);
			return "/error500";
		}
	}
	
	/**
	 * 封装部门和部门下员工 为json对象
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	private JSONArray getDeptUserJsonTree() {
		JSONArray json = new JSONArray();
		List<Department> deptList = deptService.findDept();
		for(Department dept : deptList){
			//封装部门进去
			JSONObject deptJson = new JSONObject();
			deptJson.element("id", dept.getId()).element("pId", "0").element("name", dept.getName()).element("type", "2");
			json.add(deptJson);
			
			//查询部门下员工
			List<User> userList = userService.getResultDept(dept.getId());
			for(User user : userList){
				if(user.getRoleId().equals(RoleNameId.员工.getCode())){//如果是员工,才放进树
					JSONObject userJson = new JSONObject();
					userJson.element("id", user.getId()).element("pId", dept.getId()).element("name", user.getCardName()).element("type", "1");
					json.add(userJson);
				}
			}
		}
		return json;
	}
	
	/**
	 * 编辑分组
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "addGroup.action", method = RequestMethod.GET)
	public String addGroup(Model model,Integer styleId) {
		try {
			BusiGroup group = new BusiGroup();
			group.setStyleId(styleId);
			model.addAttribute("group", group);
			return "/style/rule/addGroup";
		} catch (Exception e) {
			logger.error("addGroup ERROR",e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 校验分组名称是否存在
	 * (每个风格下名称唯一)
	* @Description: TODO
	* @param model
	* @param group
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "validate.action", method = RequestMethod.GET)
	public String validate(Model model, BusiGroup group) {
		try {
			Integer isSuccess = busiGroupService.validateName(group);
			if (isSuccess == 0) {
				model.addAttribute("result", Ajax.JSONResult(0, "成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "名称已存在!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 保存
	* @Description: TODO
	* @param model
	* @param group
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("group") BusiGroup group) {
		try {
			Integer id = group.getId();
			Integer isSuccess = 0;
			if(id==null){
				isSuccess = busiGroupService.create(group);
			}else{
				BusiGroup toUpdate = busiGroupService.get(id);
				toUpdate.setName(group.getName());
				isSuccess = busiGroupService.update(toUpdate);
			}
			if (isSuccess > 0) {
				model.addAttribute("result", Ajax.JSONResult(0, "操作成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 删除
	* @Description: TODO
	* @param model
	* @param group
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String delete(Model model, @ModelAttribute("group") BusiGroup group) {
		try {
			int num = 0;
			if(group==null || group.getId()==null){
			}else{
				num = busiGroupService.delete(group);
			}
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			logger.error("delete失败!");
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}
		return "resultjson";
	}
	
}
