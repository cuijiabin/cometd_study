package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.ChatRecordField;



/**
 * *********************************
* @Description: 聊天记录结果字段配置	dao
* @author: wangxingfei
* @createdAt: 2015年4月3日上午10:01:54
**********************************
 */
public interface ChatRecordFieldDao {

	/**
	* @Description: 获取需要展示的字段
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public List<ChatRecordField> findDisplay();

	public ChatRecordField findById(Integer id);
	
	/**
	 * 只更新是否显示字段
	* @Description: TODO
	* @param list
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public int updateIsDisplay(ChatRecordField crf);
	
	/**
	 * 获取所有
	* @Description: TODO
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public List<ChatRecordField> findAll();
	
}
