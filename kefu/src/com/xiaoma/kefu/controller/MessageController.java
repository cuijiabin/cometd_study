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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.Message;
import com.xiaoma.kefu.model.MessageType;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.MessageService;
import com.xiaoma.kefu.service.MessageTypeService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;

/**
 * 
 * @author frongji
 *  常用语控制层
 */

@Controller
@RequestMapping(value = "messageDaily")
public class MessageController {
	@Autowired
	private MessageTypeService messageTypeService;
	@Autowired
	private MessageService messageService;
	 
	@RequestMapping(value = "main.action", method = RequestMethod.GET)
	public String messageDaily(HttpSession session, Model model, Integer id) {
		User user = (User) session.getAttribute(CacheName.USER);
		if (user == null)
			return "login";
		 Integer userId = user.getId();
		if (id != null) {
			List list = messageTypeService.findTree(id,userId);
			MessageType mType = null;
			JSONArray json = new JSONArray();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					MessageType mt = (MessageType) list.get(i);
					if (i == 0)
						mType = mt;
					JSONObject jObject = new JSONObject();
					jObject.element("id", mt.getId())
							.element("pId", mt.getpId())
							.element("name", mt.getTitle())
							.element("typeId", mt.getTypeId())
							.element("sortId", mt.getSortId())
							.element("status", mt.getStatus());
					json.add(jObject);
				}
			}
			model.addAttribute("json", json.toString());
			model.addAttribute("typeId", id);  //参数id为 类型(1,公用；2，个人)
			model.addAttribute("messageType", mType);
			return "messagedaily/messageDaily";
		} else {
			return "/error500";
		}
	}
	
	/**
	 * 查询所有、条件查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(MapEntity conditions, @ModelAttribute("pageBean") PageBean<Message> pageBean ,Integer id) {
		try {
			messageService.getResult(conditions.getMap(), pageBean,id);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "messagedaily/messageDailyList";
			else
				return "messagedaily/messageDailyList";
		} catch (Exception e) {
			return "/error500";
		}
		
	}
	
	/**
	 * 保存前页面跳转
	 * 
	 * @return 返回值
	 */
	@RequestMapping(value = "new.action", method = RequestMethod.GET)
	public String toSave(Model model, Integer treeId) {
		Integer numId =  messageService.maxId();
		if (numId==null) {
			numId =0;
		}
		model.addAttribute("numId", numId+1);
		model.addAttribute("treeId", treeId);
		return "messagedaily/addMessageDaily";
	}
	
	/**
	 * 保存
	 * 
	 * @param session
	 * @param model
	 * @param messageType
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(HttpSession session, Model model, Message message)
			throws ParseException {
		try {
			User user = (User) session.getAttribute(CacheName.USER);
			if (user == null) {
				return "login";
			}
			Integer userId = user.getId();
			message.setUserId(userId);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String nowString = sdf.format(now); // Date型 时间 转换为 String型
			Date nowDate = sdf.parse(nowString);
			message.setCreateDate(nowDate);

			boolean isSuccess = messageService.createNewMessage(message);
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

}
