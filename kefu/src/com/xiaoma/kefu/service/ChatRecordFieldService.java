package com.xiaoma.kefu.service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.ChatRecordFieldDao;
import com.xiaoma.kefu.model.ChatRecordField;


/**
 * *********************************
* @Description: 聊天记录结果字段配置	业务处理类
* @author: wangxingfei
* @createdAt: 2015年4月3日上午10:01:00
**********************************
 */
@Service
public class ChatRecordFieldService {
	
	@Autowired
	private ChatRecordFieldDao chatRecordFieldDaoImpl;
	
	/**
	 * 只更新是否显示字段
	* @Description: TODO
	* @param list
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public void updateIsDisplay(List<ChatRecordField> list) {
		for(ChatRecordField crf : list){
			chatRecordFieldDaoImpl.updateIsDisplay(crf);
		}
	}
	
	
	/**
	 * 获取需要展示的字段
	* @Description: TODO
	* @return	key=字段code,value=字段名称
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public Map<String, String> getDisplayMap() {
		List<ChatRecordField> list = chatRecordFieldDaoImpl.findDisplay();
		Map<String,String> hm = new LinkedHashMap<String,String>(list.size(),1);//需要按顺序放
		for(ChatRecordField crf : list){
			hm.put(crf.getCode(), crf.getName());
		}
		return hm;
	}

	/**
	 * 根据ID查询
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public ChatRecordField findById(Integer id) {
		return chatRecordFieldDaoImpl.findById(id);
	}
	
	/**
	 * 获取所有
	* @Description: TODO
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public List<ChatRecordField> findAll(){
		return chatRecordFieldDaoImpl.findAll();
	}



}
