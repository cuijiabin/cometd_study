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
 * @time 2015年4月8日下午3:32:41 
 * 常用语分类 Controller
 */
@Controller
@RequestMapping(value = "messageType")
public class MessageTypeController {

	private Logger logger = Logger.getLogger(MessageTypeController.class);

	@Autowired
	private MessageTypeService messageTypeService;

	/**
	 * 查询各个级别的树
	 * 
	 * @param model
	 * @param id(类型ID)
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "main.action", method = RequestMethod.GET)
	public String main(HttpSession session,Model model, Integer id) {
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
			return "messagetype/messageType";
		} else {
			return "/error500";
		}
	}

	/**
	 * 保存前页面跳转
	 * 
	 * @return 返回值
	 */
	@RequestMapping(value = "new.action", method = RequestMethod.GET)
	public String toSave(Model model, Integer treeId, Integer typeId) {
	   
		Integer sortId =  messageTypeService.getChildCount(treeId,typeId);
		if (sortId==null) {
			sortId =0;
		}
		model.addAttribute("treeId", treeId);
		model.addAttribute("typeId", typeId);
		model.addAttribute("sortId", sortId+1);
		return "messagetype/addMessageType";
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
	public String save(HttpSession session, Model model, MessageType messageType)
			throws ParseException {
		try {
			User user = (User) session.getAttribute(CacheName.USER);
			if (user == null) {
				return "login";
			}
			Integer userId = user.getId();
			messageType.setUserId(userId);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String nowString = sdf.format(now); // Date型 时间 转换为 String型
			Date nowDate = sdf.parse(nowString);
			messageType.setCreateDate(nowDate);

			boolean isSuccess = messageTypeService
					.createNewMessageType(messageType);
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
    public String toUpdate(Model model,Integer treeId) {
		if(treeId == null)
			treeId = 1;
    	MessageType messageType = messageTypeService.getMessageTypeById(treeId); 
    	 model.addAttribute("messageType", messageType);
	   
        return "messagetype/editMessageType";
     }

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.POST)
	public String update(HttpSession session, Model model,
			MessageType messageType) {
		try {
			MessageType toUpdateMessageType = messageTypeService
					.getMessageTypeById(messageType.getId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			User user = (User) session.getAttribute(CacheName.USER);
			if (user == null)
				return "login";
			Integer userId = user.getId();
			toUpdateMessageType.setUserId(userId);

			Date now = new Date();
			String nowString = sdf.format(now); // Date型 时间 转换为 String型
			Date nowDate = sdf.parse(nowString);
			toUpdateMessageType.setCreateDate(nowDate);

			toUpdateMessageType.setTypeId(messageType.getTypeId());
			toUpdateMessageType.setTitle(messageType.getTitle());
			toUpdateMessageType.setSortId(messageType.getSortId());
			toUpdateMessageType.setpId(messageType.getpId());
			toUpdateMessageType.setStatus(messageType.getStatus());
			boolean isSuccess = messageTypeService
					.updateMessageType(toUpdateMessageType);

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
	 * 删除前检查
	 * 
	 * @param model
	 * @param messageType
	 * @return
	 */
	@RequestMapping(value = "check.action", method = RequestMethod.GET)
	public String checkChild(Model model, Integer treeId) {

		try {
			if (treeId == null) {
				model.addAttribute("result", Ajax.toJson(1, "缺少参数，请重新提交！"));
				return "resultjson";
			}
			Integer count = messageTypeService.checkChild(treeId); // 查看是否有子节点
			System.out.println(count);
			if (count != null && count == 0) {
				model.addAttribute("result", Ajax.toJson(0, "删除成功"));
			} else {
				model.addAttribute("result", Ajax.toJson(1, "请先删除子节点信息"));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			model.addAttribute("result", Ajax.toJson(1, "查询出错啦，请刷新后重试！"));
		}

		return "resultjson";

	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String delet(Model model, Integer treeId) {

		boolean isSuccess = messageTypeService.deleteMessageTypeById(treeId);

		if (isSuccess) {
			model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));

		} else {
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
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
	public String messageTypeDetail(Model model, Integer id) {

		MessageType messageType = messageTypeService.getMessageTypeById(id);
		model.addAttribute("messageType", messageType);
		return "messagetype/messageTypeDetail";
	}
	/**
	 * 条件查询
	 */
	
	@RequestMapping(value = "search.action",method = RequestMethod.GET)
	public String search(Model model,String typeId,String title){
		MessageType messageType = messageTypeService.getResultBySearch(typeId,title);
		model.addAttribute("messageType", messageType);
		return "messagetype/messageTypeDetail";
	}
	 

	/***
	 * 刷新树
	 */
	@RequestMapping(value = "treeList.action", method = RequestMethod.GET)
	public String treeList(HttpSession session, Model model, Integer id, Integer typeId) {
		try {
			User user = (User) session.getAttribute(CacheName.USER);
			if (user == null)
				return "login";
			 Integer userId = user.getId();
			MessageType mType = null;
			if (id != null)
				mType = messageTypeService.getMessageTypeById(id);
			List list = messageTypeService.findTree(typeId,userId);
			JSONArray json = new JSONArray();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					MessageType mt = (MessageType) list.get(i);
					if(i == 1 && mType == null )
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
			model.addAttribute("messageType",mType);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "messagetype/messageTree";
	}

}
