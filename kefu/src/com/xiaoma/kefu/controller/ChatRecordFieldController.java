package com.xiaoma.kefu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.ChatRecordField;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.ChatRecordFieldService;
import com.xiaoma.kefu.util.Ajax;

/**
 * *********************************
* @Description: 聊天记录结果字段配置
* @author: wangxingfei
* @createdAt: 2015年4月3日上午9:57:51
**********************************
 */
@Controller
@RequestMapping(value = "chatRecordField")
public class ChatRecordFieldController {

	private Logger logger = Logger.getLogger(ChatRecordFieldController.class);

	@Autowired
	private ChatRecordFieldService chatRecordFieldService;
	
	/**
	* @Description: 保存自定义配置
	* @param session
	* @param model
	* @param date	格式 customerName:1,cardName:0
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	@RequestMapping(value = "saveRecord.action", method = RequestMethod.POST)
	public String saveRecord(HttpSession session,Model model,@RequestParam("data") String data){
		try {
			//获取当前用户id
			User user = (User) session.getAttribute(CacheName.USER);
			chatRecordFieldService.saveRecord(user.getId(),data);
			
			model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
		} catch (Exception e) {
			logger.error("saveRecord失败!"+data);
			e.printStackTrace();
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 配置显示字段 编辑页面
	* @Description: TODO
	* @param session
	* @param model
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月12日
	 */
	@RequestMapping(value = "edit.action", method = RequestMethod.GET)
	public String edit(HttpSession session,Model model){
		//获取当前用户id
		User user = (User) session.getAttribute(CacheName.USER);
		
		List<ChatRecordField> list = chatRecordFieldService.findByUserId(user.getId());
		if(list==null || list.size()==0){
			//如果没有配置过,则取默认的
			list = chatRecordFieldService.findByUserId(1);
			for(ChatRecordField crf : list){
				crf.setIsDisplay(0);
				if(crf.getIsDefault()==1){
					crf.setIsDisplay(1);
				}
			}
		}
		model.addAttribute("list", list);
		return "/record/talk/recordField";
	}
	
}
