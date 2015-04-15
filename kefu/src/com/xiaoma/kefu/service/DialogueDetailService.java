package com.xiaoma.kefu.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DialogueDetailDao;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.util.JsonUtil;

@Service("dialogueDetailService")
public class DialogueDetailService {

	@Resource()
	private DialogueDetailDao dialogueDetailDao;
	
	public List<Long> batchAdd(List<DialogueDetail> list){
		
		List<Serializable> returnList = dialogueDetailDao.batchAdd(list);
		
		return JsonUtil.convertSerializable2Long(returnList);
	}
	
	public Boolean batchDelete(List<Long> ids){
		
		List<Serializable> idList = JsonUtil.convertLong2Serializable(ids);
		
		return dialogueDetailDao.batchDelete(DialogueDetail.class, idList);
	}
}
