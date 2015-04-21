package com.xiaoma.kefu.service;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.MessageRecordsDao;
import com.xiaoma.kefu.model.MessageRecords;


/**
 * 留言信息	业务处理类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月8日上午11:00:28
**********************************
 */
@Service
public class MessageRecordsService {
	
	@Autowired
	private MessageRecordsDao messageRecordsDaoImpl;
	
	/**
	 * 逻辑删除
	* @Description: TODO
	* @param ids	1,2,3
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	public int delete4Logic(String ids){
		int num = 0;
		if(StringUtils.isBlank(ids)) return num;
		String[] temp = ids.split(",");
		for(String str : temp){
			MessageRecords record = new MessageRecords();
			record.setId(Integer.valueOf(str));
			num += messageRecordsDaoImpl.update2Del(record);
		}
		return num;
	}
	
	/**
	 * 物理删除
	* @Description: TODO
	* @param ids	1,2,3
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	public int delete(String ids){
		int num = 0;
		if(StringUtils.isBlank(ids)) return num;
		String[] temp = ids.split(",");
		for(String str : temp){
			MessageRecords record = new MessageRecords();
			record.setId(Integer.valueOf(str));
			num += messageRecordsDaoImpl.delete(record);
		}
		return num;
	}
	
	/**
	 * 回收站信息还原
	* @Description: TODO
	* @param ids	1,2,3
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	public int restore(String ids){
		int num = 0;
		if(StringUtils.isBlank(ids)) return num;
		String[] temp = ids.split(",");
		for(String str : temp){
			MessageRecords record = new MessageRecords();
			record.setId(Integer.valueOf(str));
			num += messageRecordsDaoImpl.update2Restore(record);
		}
		return num;
	}
	
	/**
	 * 根据ID,获得留言记录
	* @Description: TODO
	* @param valueOf
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月12日
	 */
	public MessageRecords findById(Integer id) {
		return messageRecordsDaoImpl.findById(MessageRecords.class, id);
	}
	/***
	 * 添加新留言
	 * @param mr
	 * @return
	 */
	public Integer add(MessageRecords mr) {
		return (Integer)messageRecordsDaoImpl.add(mr);
	}

}
