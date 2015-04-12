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
public interface ChatRecordFieldDao extends BaseDao<ChatRecordField>{

	/**
	* @Description: 根据用户id获取需要展示的字段
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public List<ChatRecordField> findByUserId(Integer userId);

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
	* @Description: 获取默认展示的字段
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public List<ChatRecordField> findCommonDefault();
	
	/**
	 * 
	* @Description: 获取基本的需要展示的字段
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	public List<ChatRecordField> findCommon();
	
	/**
	 * 
	* @Description: 根据用户id,删除配置
	* @param userId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	public int deleteByUserId(Integer userId);
	
	/**
	 * 更新是否显示
	* @Description: TODO
	* @param crf
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月12日
	 */
	public int updateDisplay(ChatRecordField crf);
	
}
