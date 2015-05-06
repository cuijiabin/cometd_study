package com.xiaoma.kefu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.BusiGroup;
import com.xiaoma.kefu.model.BusiGroupDetail;
import com.xiaoma.kefu.service.BusiGroupDetailService;
import com.xiaoma.kefu.service.BusiGroupService;
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
	
	

	/**
	 * 保存(分组)
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
	
	
	/**
	 * 分流编辑页面
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	@RequestMapping(value = "editDetail.action", method = RequestMethod.GET)
	public String editDetail(Model model,Integer styleId) {
		try {
			List<BusiGroup> groupList = busiGroupService.findByStyleId(styleId);
			Map<Integer,List<BusiGroupDetail>> hm = new HashMap<Integer,List<BusiGroupDetail>>();
			for(BusiGroup group : groupList){
				List<BusiGroupDetail> detailList = busiGroupDetailService.findByGroupId(group.getId());
				hm.put(group.getId(), detailList);
			}
			
			model.addAttribute("groupList", groupList);
			model.addAttribute("detailMap", hm);
			return "style/rule/editDetail";
		} catch (Exception e) {
			logger.error("editDetail ERROR"+styleId,e);
			return "/error500";
		}
	}
	
	
	/**
	 * 保存分流明细
	* @param model
	* @param data	15:0,16:1	id:是否勾选 0否,1是
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月21日
	 */
	@RequestMapping(value = "saveDetail.action", method = RequestMethod.POST)
	public String saveDetail(Model model,String data){
		try {
			busiGroupDetailService.saveDetail(data);
			model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
		} catch (Exception e) {
			logger.error("saveRecord失败!"+data,e);
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}
		return "resultjson";
	}
	
	
}
