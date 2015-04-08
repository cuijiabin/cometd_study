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
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	public void delete4Logic(String ids){
		if(StringUtils.isBlank(ids)) return;
		String[] temp = ids.split(",");
		for(String str : temp){
			MessageRecords record = new MessageRecords();
			record.setId(Integer.valueOf(str));
			messageRecordsDaoImpl.update2Del(record);
		}
	}
	
	/**
	 * 物理删除
	* @Description: TODO
	* @param ids	1,2,3
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	public void delete(String ids){
		if(StringUtils.isBlank(ids)) return;
		String[] temp = ids.split(",");
		for(String str : temp){
			MessageRecords record = new MessageRecords();
			record.setId(Integer.valueOf(str));
			messageRecordsDaoImpl.delete(record);
		}
	}
	
	/**
	 * 回收站信息还原
	* @Description: TODO
	* @param ids	1,2,3
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	public void restore(String ids){
		if(StringUtils.isBlank(ids)) return;
		String[] temp = ids.split(",");
		for(String str : temp){
			MessageRecords record = new MessageRecords();
			record.setId(Integer.valueOf(str));
			messageRecordsDaoImpl.update2Restore(record);
		}
	}

}
