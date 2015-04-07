package com.xiaoma.kefu.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对话聊天内容	实体类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月7日下午2:09:14
**********************************
 */
@Entity
@Table(name="dialogue_detail")
public class DialogueDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	@Column(name="dialogueId")
	private Long dialogueId;
	@Column(name="dialogueType")
	private Integer dialogueType;
	@Column(name="customerId")
	private Long customerId;
	@Column(name="userId")
	private Integer userId;
	@Column(name="cardName",length=20)
	private String cardName;
	@Column(name="content")
	private String content;
	@Column(name = "createDate")
	private Date createDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDialogueId() {
		return dialogueId;
	}
	public void setDialogueId(Long dialogueId) {
		this.dialogueId = dialogueId;
	}
	public Integer getDialogueType() {
		return dialogueType;
	}
	public void setDialogueType(Integer dialogueType) {
		this.dialogueType = dialogueType;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
