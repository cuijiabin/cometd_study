package com.xiaoma.kefu.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.MessageDao;
import com.xiaoma.kefu.dao.MessageTypeDao;
import com.xiaoma.kefu.model.Message;
import com.xiaoma.kefu.model.MessageType;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

/**
 * @author frongji
 * @time 2015年4月7日下午5:24:07 常用语 service class
 */
@Service("messageService")
public class MessageService {
	private static Logger logger = Logger.getLogger(MessageService.class);
	@Autowired
	private MessageDao messageDaoImpl;

	@Autowired
	private MessageTypeDao messageTypeDaoImpl;

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public void getResult(Map<String, String> conditions,
			PageBean<Message> pageBean, Integer id) {
		messageDaoImpl.findByCondition(conditions, pageBean, id);
	}

	/**
	 * 查询最大的id 值
	 */
	public Integer maxId() {
		Integer num = messageDaoImpl.getMaxId(); // num 最大的排序号
		return num;
	}

	/**
	 * 查询一条
	 */
	public Message getMessageById(Integer id) {
		return messageDaoImpl.getMessageById(id);
	}

	/**
	 * 查询是否有常用
	 */
	public Integer checkDaily(Integer messageTypeId) {
		Integer count = messageDaoImpl.checkDaily(messageTypeId);
		return count;
	}

	/**
	 * 添加
	 */
	public boolean createNewMessage(Message message) {
		return messageDaoImpl.createNewMessage(message);
	}

	/**
	 * 修改
	 */
	public boolean updateMessage(Message message) {
		return messageDaoImpl.updateMessage(message);
	}

	/**
	 * 删除
	 */
	public Integer deleteMessageById(Integer id) {
		return messageDaoImpl.deleteMessageById(id);
	}

	/**
	 * 查询所有的常用语 typeId 1为公用，2为个人
	 */
	public List<Message> findAllByParam(Integer typeId, Integer userId) {
		List<Message> list = messageDaoImpl.findAllByParam(typeId, userId);
		return list;
	}

	/**
	 * 查询所有的常用语 typeId 1为公用，2为个人
	 */
	public String findAllUserMessage(Integer userId) {
		try {
			//获取所有公用的常用语分类
			List<MessageType> messageTypelist = messageTypeDaoImpl.findAllByParam(1, userId);
			//获取所有个人的常用语分类
			List<MessageType> userTypelist = messageTypeDaoImpl.findAllByParam(2, userId);
			//获取所有公用的常用语
			List<Message> list = messageDaoImpl.findAllByParam(1, userId);
			//获取所有个人的常用语
			List<Message> messageList = messageDaoImpl.findAllByParam(2, userId);
			
			JSONArray json = new JSONArray();
			
			//公共常用语分类
			if (messageTypelist != null && messageTypelist.size() > 0) {
				for (MessageType mt : messageTypelist) {
					JSONObject jObject = new JSONObject();
					jObject.element("id", mt.getId())
							.element("pId", mt.getpId())
							.element("name", mt.getTitle())
							.element("typeId", mt.getTypeId())
							.element("status", mt.getStatus())
							.element("sId", 1)
							.element("title", mt.getTitle());
					json.add(jObject);
				}
			}
			//个人常用语分类
			if (userTypelist != null && userTypelist.size() > 0) {
				for (MessageType mt : userTypelist) {
					JSONObject jObject = new JSONObject();
					jObject.element("id", mt.getId())
					.element("pId", mt.getpId())
					.element("name", mt.getTitle())
					.element("typeId", mt.getTypeId())
					.element("status", mt.getStatus())
					.element("sId", 1)
					.element("title", mt.getTitle());
					json.add(jObject);
				}
			}
			//公共常用语
			if (list != null && list.size() > 0) {
				for (Message mt : list) {
					JSONObject jObject = new JSONObject();
					jObject.element("id", mt.getId())
							.element("pId", mt.getMessageTypeId())
							.element("name", mt.getTitle())
							.element("typeId", mt.getTypeId())
							.element("sId", 2)
							.element("title", mt.getContent());
					json.add(jObject);
				}
			}
			//个人常用语
			if (messageList != null && messageList.size() > 0) {
				for (Message mt : messageList) {
					JSONObject jObject = new JSONObject();
					jObject.element("id", mt.getId())
					.element("pId", mt.getMessageTypeId())
					.element("name", StringHelper.subTextString(mt.getTitle(),12))
					.element("typeId", mt.getTypeId())
					.element("sId", 2)
					.element("title", mt.getContent());
					json.add(jObject);
				}
			}
			return json == null ? "":json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return "";
	}
}
