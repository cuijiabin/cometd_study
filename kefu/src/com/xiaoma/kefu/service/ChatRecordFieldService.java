package com.xiaoma.kefu.service;


import java.util.Date;
import java.util.HashMap;
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
	 * 
	* @Description: 根据用户  获取需要展示的字段
	* @return	key=字段code,value=字段名称
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	public Map<String, String> findDisplayMapByUserId(Integer userId) {
		List<ChatRecordField> list = chatRecordFieldDaoImpl.findByUserId(userId);
		Map<String,String> hm = new LinkedHashMap<String,String>(list.size(),1);//需要按顺序放
		for(ChatRecordField crf : list){
			if(crf.getIsDisplay()!=1){
				continue;
			}
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
		return chatRecordFieldDaoImpl.findById(ChatRecordField.class,id);
	}
	
	/**
	 * 获取默认需要展示的字段
	* @Description: TODO
	* @return	key=字段code,value=字段名称
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public Map<String, String> findDefaultMap() {
		List<ChatRecordField> list = chatRecordFieldDaoImpl.findCommonDefault();
		Map<String,String> hm = new LinkedHashMap<String,String>(list.size(),1);//需要按顺序放
		for(ChatRecordField crf : list){
			hm.put(crf.getCode(), crf.getName());
		}
		return hm;
	}

	
	/**
	 * 
	* @Description: 根据用户保存配置
	* @param userId
	* @param date	格式  customerName:1,cardName:0
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	public void saveRecord(Integer userId, String date) {
		if(userId!=null && userId==1){
			Map<String,ChatRecordField> hm = findCommonMap();
			String[] strs = date.split(",");
			for(int i=0;i<strs.length;i++){
				String[] temp = strs[i].split(":");
				ChatRecordField crf = hm.get(temp[0]);
				crf.setIsDisplay(Integer.valueOf(temp[1]));
				crf.setUpdateDate(new Date());
				chatRecordFieldDaoImpl.updateDisplay(crf);
			}
		}else{
			//先删除
			chatRecordFieldDaoImpl.deleteByUserId(userId);
			//再保存
			Map<String,ChatRecordField> hm = findCommonMap();
			String[] strs = date.split(",");
			for(int i=0;i<strs.length;i++){
				String[] temp = strs[i].split(":");
				ChatRecordField template = hm.get(temp[0]);
				ChatRecordField crf = new ChatRecordField();
				
				//使用模板信息
				crf.setCode(template.getCode());
				crf.setIsDefault(template.getIsDefault());
				crf.setName(template.getName());
				crf.setSortId(template.getSortId());
				
				//设置信息
				crf.setUserId(userId);
				crf.setIsDisplay(Integer.valueOf(temp[1]));
				crf.setCreateDate(new Date());
				crf.setUpdateDate(null);
				chatRecordFieldDaoImpl.add(crf);
			}
		}
		
	}
	
	/**
	 * 
	* @Description: 获取基本的配置字段信息
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	public List<ChatRecordField> findCommon(){
		return chatRecordFieldDaoImpl.findCommon();
	}
	
	/**
	 * 
	* @Description: 获取基本的配置字段信息
	* @return	key=code,value=ChatRecordField
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	public Map<String,ChatRecordField> findCommonMap(){
		List<ChatRecordField> list = findCommon();
		Map<String,ChatRecordField> hm = new HashMap<String,ChatRecordField>(list.size(),1);
		for(ChatRecordField crf : list){
			hm.put(crf.getCode(), crf);
		}
		return hm;
	}
	
	/**
	 * 根据用户id,获取配置信息
	* @Description: TODO
	* @param userId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月12日
	 */
	public List<ChatRecordField> findByUserId(Integer userId) {
		return chatRecordFieldDaoImpl.findByUserId(userId);
	}




}
