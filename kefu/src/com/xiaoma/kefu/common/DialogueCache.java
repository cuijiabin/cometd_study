package com.xiaoma.kefu.common;

import java.util.Date;

import net.sf.json.JSONObject;

public class DialogueCache {

	private String content;
	
	//1-客户，2-客服 ,3-机器人
	private Integer type;
	
	private Date createDate;
	
	public DialogueCache(){}
	
	public DialogueCache(Integer type,Date createDate,String content){
		this.type = type;
		this.createDate = createDate;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	//与json互相转化
	public String toString(){
		
		JSONObject jsonObj = JSONObject.fromObject(this);
		
		return jsonObj.toString();
		
	}
	
	public static DialogueCache getObjFromJson(String json){
		JSONObject obj = JSONObject.fromObject(json);
		
		DialogueCache dialogueCache = (DialogueCache) JSONObject.toBean(obj,DialogueCache.class);
		
		return dialogueCache;
	}
	
	public static String buildKey(Long customerId, Integer userId){
		
		return "dialogue::cId:"+customerId+"uId:"+userId;
	}
	
	public static String buildKey(DialogueUniqueTag uniqueTag){
		
		return buildKey(uniqueTag.getCustomerId(),uniqueTag.getUserId());
	}
	
	public static String getDialogueCache(DialogueUniqueTag uniqueTag, String content){
		
		Integer type = (uniqueTag.getUserId() == DialogueUniqueTag.DEFAULT_USER_ID) ? 3 : uniqueTag.getType();
		
		DialogueCache dialogueCache = new DialogueCache(type, new Date(),content);
		
		return dialogueCache.toString();
		
	}
}
