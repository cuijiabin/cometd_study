package com.xiaoma.kefu.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DialogueDao;
import com.xiaoma.kefu.model.Dialogue;


/**
 * 对话信息	业务处理类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月3日下午3:27:45
**********************************
 */
@Service
public class DialogueService {
	
	@Autowired
	private DialogueDao dialogueDaoImpl;
	
	/**
	 * 逻辑删除 对话信息
	* @Description: TODO
	* @param list
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public void delete4Logic(List<Dialogue> list){
		for(Dialogue dialogue : list){
			dialogueDaoImpl.update2Del(dialogue);
		}
	}
	
	/**
	 * 物理删除	对话信息
	* @Description: TODO
	* @param list
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public void delete(List<Dialogue> list){
		for(Dialogue dialogue : list){
			dialogueDaoImpl.delete(dialogue);
		}
	}
	
	/**
	 * 回收站信息还原
	* @Description: TODO
	* @param list
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	public void restore(List<Dialogue> list){
		for(Dialogue dialogue : list){
			dialogueDaoImpl.update2Restore(dialogue);
		}
	}
	
	/**
	 * 
	* @Description: 根据id,获取对话信息
	* @param dialogueId
	 * @return 
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	public Dialogue findById(Long dialogueId) {
		return dialogueDaoImpl.findById(Dialogue.class, dialogueId);
	}


}
