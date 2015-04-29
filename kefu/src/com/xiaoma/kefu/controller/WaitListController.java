package com.xiaoma.kefu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.WaitList;
import com.xiaoma.kefu.service.WaitListService;
import com.xiaoma.kefu.util.Ajax;

@Controller
@RequestMapping(value = "waitList")
public class WaitListController {

	private Logger logger = Logger.getLogger(WaitListController.class);
	
	@Autowired
	private WaitListService waitListService;
	
	/**
	 * 编辑
	* @Description: TODO
	* @param model
	* @param styleId	风格id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	@RequestMapping(value = "edit.action", method = RequestMethod.GET)
	public String edit(Model model,Integer styleId) {
		try {
			List<WaitList> pList = waitListService.getOneLev(styleId);
			if(pList!=null && pList.size()>0){
				List<WaitList> zList = waitListService.getByPid(pList.get(0).getId());
				model.addAttribute("zList", zList);
				model.addAttribute("pId", pList.get(0).getId());
			}
			model.addAttribute("pList", pList);
			model.addAttribute("styleId", styleId);
			return "/style/wait/edit";
		} catch (Exception e) {
			logger.error("edit", e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 编辑二级菜单页面
	 * @param model
	 * @param pId
	 * @param styleId
	 * @return
	 */
	@RequestMapping(value = "editTwo.action", method = RequestMethod.GET)
	public String editTwo(Model model,Integer pId,Integer styleId) {
		try {
			List<WaitList> zList = waitListService.getByPid(pId);
			model.addAttribute("zList", zList);
			model.addAttribute("pId", pId);
			model.addAttribute("styleId", styleId);
			return "/style/wait/editTwo";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	
	/**
	 *  新增一级菜单界面
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	@RequestMapping(value = "add.action", method = RequestMethod.GET)
	public String add(Model model,Integer styleId) {
		try {
			model.addAttribute("styleId", styleId);
			model.addAttribute("pId", 0);
			return "/style/wait/add";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 校验 名称是否存在 及 数量是否超出限制
	 * @param model
	 * @param WaitList
	 * @return
	 */
	@RequestMapping(value = "validate.action", method = RequestMethod.POST)
	public String validate(Model model, @ModelAttribute("waitList")WaitList waitList) {
		try {
			Integer nameSuccess = waitListService.validateName(waitList);
			if (nameSuccess == 0) {
				boolean numSuccess = waitListService.validateNum(waitList);
				if(numSuccess){
					model.addAttribute("result", Ajax.JSONResult(0, "成功!"));
				}else{
					model.addAttribute("result", Ajax.JSONResult(1, "数量超出限制!"));
				}
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "名称已存在!"));
			}
		} catch (Exception e) {
			logger.error("validate", e);
			model.addAttribute("result", Ajax.JSONResult(1, "失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 删除一级菜单
	* @Description: TODO
	* @param model
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月16日
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.POST)
	public String delete(Model model,@RequestParam("id")Integer id){
		try {
			int num = waitListService.delete(id);
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			logger.error("delete失败!"+id,e);
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 新增二级菜单界面
	* @Description: TODO
	* @param model
	* @param pId
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月16日
	 */
	@RequestMapping(value = "addTwo.action", method = RequestMethod.GET)
	public String addTwo(Model model,Integer pId,Integer styleId) {
		try {
			model.addAttribute("pId", pId);
			model.addAttribute("styleId", styleId);
			return "/style/wait/addTwo";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 保存
	* @Description: TODO
	* @param model
	* @param customer
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("waitList") WaitList waitList) {
		try {
			Integer id = waitList.getId();
			int isSuccess = 0;
			if(id==null){
				isSuccess = waitListService.create(waitList);
			}else{
				WaitList toUpdate = waitListService.get(id);
				toUpdate.setName(waitList.getName());
				toUpdate.setLinkUrl(waitList.getLinkUrl());
				isSuccess = waitListService.update(toUpdate);
			}
			if (isSuccess > 0) {
				model.addAttribute("result", Ajax.JSONResult(0, "操作成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
		}
		return "resultjson";
	}
	/***
	 * 根据Id查询所有
	 * @param model
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "findById.action", method = RequestMethod.GET)
	public String findById(Model model,Integer styleId,Integer id) {
		try {
			if(styleId == null)
				styleId = 1;
			if(id == null)
				id = 0;
			List<WaitList> list = (List<WaitList>) CacheMan.getObject(CacheName.STYLEWAITLIST, styleId+"_"+id,List.class);
			model.addAttribute("waitList",list);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/dialogue/waitList";
	}
	
}
