package com.xiaoma.kefu.controller;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.Dict;
import com.xiaoma.kefu.service.DictService;
import com.xiaoma.kefu.util.Ajax;



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
	private DictService dictService;

	/**
	 * 访问注册设置查询
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "visitRegister.action", method = RequestMethod.GET)
	public String queryAll(Model model) {
		DictMan.getDictList(CacheName.DICTLIST, "d_");
//		model.addAttribute("list", pageBean.getObjList());
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("code", code);
//		model.addAttribute("name", name);

		return "/views/dictList";
	}

	/**
	 * 在弹出的对话框中显示详细信息
	 */
	@RequestMapping(value = "detail.action", method = RequestMethod.GET)
	public String dictDetail(Model model, String id) {

		Dict dict = dictService.getDictById(id);
		JSONObject jsonObject = JSONObject.fromObject(dict);
		model.addAttribute("result", jsonObject.toString());

		return "views/resultjson";
	}

	/**
	 * 添加
	 */

	@RequestMapping(value = "add.action", method = RequestMethod.GET)
	public String addDict(Model model, Dict dict) {
		
		try {
			boolean isSuccess = dictService.createNewDict(dict);
			if (isSuccess) {
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
	 * 修改一级字典
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.GET)
	public String updateDict(Model model, Dict dict) {

		String message = "failure";
		Integer code = -1;

		boolean isSuccess = dictService.updateDict(dict);
		if (isSuccess) {
			message = "success";
			code = 200;
		}
		model.addAttribute("message", message);
		model.addAttribute("code", code);

		return "/views/message";
	}
	

}
