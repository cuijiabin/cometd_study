package com.xiaoma.kefu.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.service.DialogueService;

/**
 * 分配机制	业务实现类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:50:53
**********************************
 */
@Service
public class QuartzJobService {
	
	@Autowired
	private DialogueService dialogueService;//对话信息
	
	
	/**
	 * 定时导出对话信息到 excel文件
	* @throws Exception
	* @Author: wangxingfei
	* @Date: 2015年4月23日
	 */
	public void expTalkRecord() throws Exception{
		dialogueService.writeExcelByTime();
	}
	
	
	
}