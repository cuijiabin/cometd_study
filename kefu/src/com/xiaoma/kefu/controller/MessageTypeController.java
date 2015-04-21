package com.xiaoma.kefu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.MessageType;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.MessageTypeService;
import com.xiaoma.kefu.util.Ajax;

/**
 * @author frongji
 * @time 2015年4月8日下午3:32:41 常用语分类 Controller class
 */
@Controller
@RequestMapping(value = "messageType")
public class MessageTypeController {

	@Autowired
	private MessageTypeService messageTypeService;

	// 查询各个级别的树
	@SuppressWarnings("static-access")
	@RequestMapping(value = "main.action", method = RequestMethod.GET)
	public String main(Model model, Integer id) {
		if (id != null) {
			List list = messageTypeService.findTree(id);

			JSONArray json = new JSONArray();
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					MessageType mt = (MessageType)list.get(i);
					JSONObject jObject = new JSONObject();
					jObject.element("id", mt.getId()).element("pId", mt.getpId())
						.element("name", mt.getTitle());
					json.add(jObject);
				}
			}
			
			System.out.println(json);
			model.addAttribute("json", json.toString());
			return "message/messageType";
		} else {
			return "null";
		}
	}
	
	  /**
     * 保存前页面跳转
     * 
     * @return 返回值
     */
    @RequestMapping(value = "new.action",method=RequestMethod.GET)
    public String toSave(Model model) {
        return "message/addMessageType";
     }
    
    
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(HttpSession session, Model model,MessageType messageType) throws ParseException {
		try {
           User user = (User) session.getAttribute(CacheName.USER);
             if (user == null ) {
        	   return "login";
		      }
              Integer userId = user.getId();
              messageType.setUserId(userId);
              
             SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             Date now =  new Date();
 			 String nowString =  sdf.format(now); //Date型 时间 转换为 String型
 			 Date nowDate = sdf.parse(nowString);
 			 messageType.setCreateDate(nowDate);

			boolean isSuccess = messageTypeService.createNewMessageType(messageType);
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
	 * 显示详细信息
	 * 
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "detail.action", method = RequestMethod.GET)
	public String messageTypeDetail(Model mode, Integer id) {

		MessageType messageType = messageTypeService.getMessageTypeById(id);

		JSONObject jsonObject = JSONObject.fromObject(messageType);
		mode.addAttribute("result", jsonObject.toString());

		return "message/messageType";
	}

}
