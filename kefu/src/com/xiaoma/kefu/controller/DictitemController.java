package com.xiaoma.kefu.controller;


import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Dictitem;
import com.xiaoma.kefu.service.DictitemService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.PageBean;



/**
 * 
 * 二级字典表Controller
 * 
 * @author 冯榕基
 * @time 2015年1月19日上午10:49:06
 *
 */
@Controller
@RequestMapping(value = "dictitem")
public class DictitemController {

	@Autowired
	private DictitemService dictitemService;

	/**
	 * 查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(Model model, String code, Integer currentPage, Integer pageRecorders) {

		currentPage = (currentPage == null) ? 1 : currentPage;
		pageRecorders = (pageRecorders == null) ? 20 : pageRecorders;

		PageBean<Dictitem> pageBean = dictitemService.getResult(currentPage, pageRecorders, code);

		model.addAttribute("list", pageBean.getObjList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("code", code);
		
		return "/views/dictitemList";
	}

	/**
	 * 在弹出的对话框中显示详细信息
	 */
	@RequestMapping(value = "detail.action", method = RequestMethod.GET)
	public String dictitemDetail(Model model, Integer id) {

		Dictitem dictitem = dictitemService.getDictitemById(id);
		JSONObject jsonObject = JSONObject.fromObject(dictitem);
		model.addAttribute("result", jsonObject.toString());

		return "views/resultjson";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "add.action", method = RequestMethod.GET)
	public String addDictitem(Model model, Dictitem dictitem) {
		try {
			boolean isSuccess = dictitemService.createNewDictitem(dictitem);
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
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.GET)
	public String updateDict(Model model, Dictitem dictitem) {
		try {
			boolean isSuccess = dictitemService.updateDictitem(dictitem);
			
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
	public String deleteDict(Model model, Integer id) {
		try {
			boolean isSuccess = dictitemService.deleteDictitemById(id);
			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}
		
		return "/views/resultjson";
	}

}
