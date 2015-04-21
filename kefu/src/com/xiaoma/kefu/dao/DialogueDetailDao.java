package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.DialogueDetail;
/**
 * @Description 详细对话内容 dao
 * @author cuijiabin
 * @Date 2015-04-15
 */
public interface DialogueDetailDao extends BaseDao<DialogueDetail>{
	
	public List<DialogueDetail> getLastRecordsByCustomerId(Long customerId);
	
}
