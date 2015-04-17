package com.xiaoma.kefu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.service.DictitemService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.StringHelper;



/**
 * 系统配置controller
 * 
 * @time 2015-01-13
 * @author fengrongji
 *
 */
@Controller
@RequestMapping(value = "sys")
public class SystemConfController {

	@Autowired
	private DictitemService dictItemService;

	/**
	 * 访问注册设置查询
	 * @return 
	 */
	@RequestMapping(value = "visitRegister.action", method = RequestMethod.GET)
	public String visitRegister(Model model) {
		model.addAttribute("message", DictMan.getDictItem("d_sys_param",5));
		model.addAttribute("dialog", DictMan.getDictItem("d_sys_param",6));
		model.addAttribute("info", DictMan.getDictItem("d_sys_param",7));
		model.addAttribute("check", DictMan.getDictItem("d_sys_param",8));
		model.addAttribute("dict", DictMan.getDictList("d_cus_info"));
		return "sys/visitRegister";
	}
	/**
	 * 修改注册设置查询
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "updateVisitRegister.action", method = RequestMethod.POST)
	public String upVisitRegister(Model model,String message,String dialog,String info,String check) {
		if(StringHelper.isNotEmpty(message)){
			DictItem mItem = DictMan.getDictItem("d_sys_param",5);
			mItem.setItemName(message);
			dictItemService.update(mItem);
		}
		if(StringHelper.isNotEmpty(dialog)){
			DictItem dItem = DictMan.getDictItem("d_sys_param",6);
			dItem.setItemName(dialog);
			dictItemService.update(dItem);
		}
		if(StringHelper.isNotEmpty(info)){
			DictItem iItem = DictMan.getDictItem("d_sys_param",7);
			iItem.setItemName(info);
			dictItemService.update(iItem);
		}
		if(StringHelper.isNotEmpty(check)){
			DictItem cItem = DictMan.getDictItem("d_sys_param",8);
			cItem.setItemName(check);
			dictItemService.update(cItem);
		}
		model.addAttribute("result",Ajax.JSONResult(0,"保存成功!"));
		return "resultjson";
	}

}
