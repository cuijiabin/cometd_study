package com.xiaoma.kefu.controller;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Dict;
import com.xiaoma.kefu.service.DictService;
import com.xiaoma.kefu.service.DictitemService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.PageBean;



/**
 * 一级字典表controller
 * 
 * @time 2015-01-13
 * @author fengrongji
 *
 */
@Controller
@RequestMapping(value = "dict")
public class DictController {

	@Autowired
	private DictService dictService;
	
	@Autowired
	private DictitemService dictitemService;

	/**
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(Model model, String code, String name,Integer currentPage, Integer pageRecorders) {

		currentPage = (currentPage == null) ? 1 : currentPage;
		pageRecorders = (pageRecorders == null) ? 10 : pageRecorders;
		PageBean<Dict> pageBean = dictService.getResultByInfo(currentPage,pageRecorders, code, name);

		model.addAttribute("list", pageBean.getObjList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("code", code);
		model.addAttribute("name", name);

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
	
	/**
	 * 删除一级字典前检查
	 * @param model
	 * @param dictCode
	 * @return
	 */
	@RequestMapping(value="deleteCheck.action",method=RequestMethod.GET)
	public String deleteCheck(Model model, String dictCode) {
		
		String message ="failure";
		Integer code = -1;
		
        Integer childrenCount = dictitemService.getCountByCode(dictCode);
		
		if(childrenCount != null && childrenCount > 0){
			 message ="success";
			 code = 200;
		}
		
		model.addAttribute("message", message);
		model.addAttribute("code", code);
		
		return "/views/message";
	}
	
	/**
	 * 删除一级字典
	 * @param model
	 * @param dictCode
	 * @return
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String deleteDict(Model model, String dictCode) {
		
		try {
			boolean isSuccess = dictService.deleteDictById(dictCode);
			
			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}

		return "resultjson";
	}

}
