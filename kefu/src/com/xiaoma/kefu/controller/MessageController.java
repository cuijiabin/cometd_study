package com.xiaoma.kefu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
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
 * @time 2015-04-27
 * @author frongji
 *  常用语控制层
 */
@Controller
@RequestMapping(value = "messageDaily")
public class MessageController {
	
	private Logger logger = Logger.getLogger(MessageController.class);
	
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
			PageBean<Message> pageBean = new PageBean<Message>();
		    Integer  treeId=  mType.getId();
			model.addAttribute("json", json.toString());
			model.addAttribute("typeId", id);  //参数id为 类型(1,公用；2，个人)
			model.addAttribute("messageType", mType);   //树的默认选中项
		   
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
	public String queryAll(MapEntity conditions, @ModelAttribute("pageBean") PageBean<Message> pageBean ,Model model,Integer id) {
		try {
			messageService.getResult(conditions.getMap(), pageBean,id);//这个id是节点ID
			model.addAttribute("messageDailyId",id);   //messageDailyId 是常用语的类别
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "messagedaily/messageDailyList";
			else
				return "messagedaily/messageDailyList";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error500";
		}
	}
	/**
	 * 添加分类 前检查 该分类下是否有 常用语
	 */
	@RequestMapping(value = "check.action", method = RequestMethod.GET)
	public String checkChild(Model model, Integer messageTypeId) {
        
		try {
			if (messageTypeId == null) {
				model.addAttribute("result", Ajax.toJson(1, "缺少参数，请重新提交！"));
				return "resultjson";
			}
			Integer count = messageService.checkDaily(messageTypeId); // 查看是否有子节点
			if (count != null && count == 0) {
				model.addAttribute("result", Ajax.toJson(0, "可以添加！"));
			} else {
				model.addAttribute("result", Ajax.toJson(1, "请先删除该节点下的常用语！"));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			model.addAttribute("result", Ajax.toJson(1, "查询出错啦，请刷新后重试！"));
		}
		return "resultjson";
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
	
	/**
	 * 编辑前页面跳转
	 * 
	 * @return 返回值
	 */
	@RequestMapping(value = "toUpdate.action",method=RequestMethod.GET)
    public String toUpdate(Model model,Integer messageDailyId) {
	
    	Message message = messageService.getMessageById(messageDailyId); 
    	 model.addAttribute("message", message);
	   
        return "messagedaily/editMessageDaily";
     }

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.POST)
	public String update(HttpSession session, Model model,Message message) {
		try {
			Message toUpdateMessage = messageService.getMessageById(message.getId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			User user = (User) session.getAttribute(CacheName.USER);
			if (user == null)
				return "login";
			Integer userId = user.getId();
			toUpdateMessage.setUserId(userId);

			Date now = new Date();
			String nowString = sdf.format(now); // Date型 时间 转换为 String型
			Date nowDate = sdf.parse(nowString);
			toUpdateMessage.setCreateDate(nowDate);

	        toUpdateMessage.setId(message.getId());
			toUpdateMessage.setTitle(message.getTitle());
		    toUpdateMessage.setContent(message.getContent());
			toUpdateMessage.setStatus(message.getStatus());
			boolean isSuccess = messageService.updateMessage(toUpdateMessage);

			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}
		return "resultjson";

	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete.action" , method = RequestMethod.GET)
    public String deleteMessageDaily(Model model,Integer id){
    	
		try {
			Integer isSuccess = messageService.deleteMessageById(id);
			
			if (isSuccess!=null) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} 
			else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("result",Ajax.JSONResult(1, "删除失败！"));
		}
		return "resultjson";
    }
}
