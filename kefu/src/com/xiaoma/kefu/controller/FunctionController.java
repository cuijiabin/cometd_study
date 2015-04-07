package com.xiaoma.kefu.controller;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.service.FunctionService;
/**
 * 
 * @author yangxiaofeng
 * @time   2015年4月7日 10:18:00
 */
@Controller
@RequestMapping("function")
public class FunctionController {
	
	@Autowired
	private FunctionService funcService;
	
	//查询各个级别的树
	@RequestMapping(value = "tree.action", method = RequestMethod.GET)
	public String tree(Model model,Integer tid) {
		if(tid!=null){
		List list = funcService.findTree(tid);
		JSONArray json = new JSONArray().fromObject(list);
		System.out.println(json);
		model.addAttribute("json",json.toString());
		return "left";
		}else{
			return "null";
		}
	}
}
