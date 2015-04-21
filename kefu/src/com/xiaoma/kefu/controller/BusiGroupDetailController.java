package com.xiaoma.kefu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.BusiGroupDetail;
import com.xiaoma.kefu.service.BusiGroupDetailService;
import com.xiaoma.kefu.service.BusiGroupService;
import com.xiaoma.kefu.service.DepartmentService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.Ajax;

/**
 * 业务分组明细	controller
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月13日下午7:53:59
**********************************
 */
@Controller
@RequestMapping(value = "busiGroupDetail")
public class BusiGroupDetailController {

	private Logger logger = Logger.getLogger(BusiGroupDetailController.class);

	@Autowired
	private BusiGroupService busiGroupService;//业务分组
	@Autowired
	private BusiGroupDetailService busiGroupDetailService;//业务分组明细
	@Autowired
	private DepartmentService deptService;//部门
	@Autowired
	private UserService userService;//用户
	
	

	/**
	 * 保存
	* @Description: TODO
	* @param model
	* @param busiGroupDetail
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("busiGroupDetail") BusiGroupDetail busiGroupDetail) {
		try {
			BusiGroupDetail oldModel = busiGroupDetailService.getByModel(busiGroupDetail);
			if(oldModel!=null){//已经存在,不处理
			}else{
				busiGroupDetailService.create(busiGroupDetail);
			}
		} catch (Exception e) {
			logger.error("save"+busiGroupDetail.getUserId(), e);
			return "error500";
		}
		return "redirect:/busiGroupDetail/viewDetail.action?groupId="+busiGroupDetail.getGroupId(); 
	}
	
	/**
	 * 分组明细展示页面
	* @param model
	* @param groupId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	@RequestMapping(value = "viewDetail.action", method = RequestMethod.GET)
	public String viewDetail(Model model,Integer groupId) {
		try {
			List<BusiGroupDetail> detailList = busiGroupDetailService.findByGroupId(groupId);
			
			model.addAttribute("detailList", detailList);
			model.addAttribute("groupId", groupId);
			return "style/rule/groupDetail";
		} catch (Exception e) {
			logger.error("viewDetail ERROR"+groupId,e);
			return "/error500";
		}
	}
	
	/**
	 * 删除
	* @Description: TODO
	* @param model
	* @param busiGroupDetail
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.POST)
	public String delete(Model model, @ModelAttribute("busiGroupDetail") BusiGroupDetail busiGroupDetail) {
		try {
			int num = 0;
			if(busiGroupDetail==null || busiGroupDetail.getId()==null){
			}else{
				num = busiGroupDetailService.delete(busiGroupDetail);
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
