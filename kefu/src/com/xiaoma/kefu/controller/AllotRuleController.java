package com.xiaoma.kefu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.AllotRule;
import com.xiaoma.kefu.service.AllotRuleService;
import com.xiaoma.kefu.util.Ajax;

/**
 * 分配机制	controller
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月20日下午2:12:14
**********************************
 */
@Controller
@RequestMapping(value = "allotRule")
public class AllotRuleController {

	private Logger logger = Logger.getLogger(AllotRuleController.class);

	@Autowired
	private AllotRuleService allotRuleService;
	
	
	/**
	 * 编辑
	* @Description: TODO
	* @param model
	* @param styleId 风格id
	* @param deviceTypeId 1=PC 2=移动
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "edit.action", method = RequestMethod.GET)
	public String edit(Model model,Integer styleId) {
		try {
			AllotRule rule = allotRuleService.getByStyleId(styleId); 
			model.addAttribute("rule", rule);
			model.addAttribute("rule1", DictMan.getDictList("d_rule_first"));
			model.addAttribute("rule2", DictMan.getDictList("d_rule_second"));
			model.addAttribute("rule3", DictMan.getDictList("d_rulethird"));
			return "/style/rule/allot";
		} catch (Exception e) {
			logger.error("edit"+styleId,e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 保存
	* @Description: TODO
	* @param model
	* @param allotRule
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(Model model,
			@ModelAttribute("allotRule") AllotRule allotRule) {
		try {
			Integer id = allotRule.getId();
			int isSuccess = 0;
			if(id==null){
				isSuccess = allotRuleService.create(allotRule);
			}else{
				AllotRule oldModel = allotRuleService.get(id);
				allotRule.setCreateDate(oldModel.getCreateDate());;
				isSuccess = allotRuleService.update(allotRule);
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
	
	
}
