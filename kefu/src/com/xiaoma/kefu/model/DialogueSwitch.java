package com.xiaoma.kefu.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对话转接记录
 * 
 * @author cuijiabin
 *
 */
@Entity
@Table(name = "dialogue_switch")
public class DialogueSwitch implements Serializable{

	private static final long serialVersionUID = -3782023768137715881L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "fromUserId")
	private Integer fromUserId;
	
	@Column(name = "toUserId")
	private Integer toUserId;
	
	@Column(name = "customerId")
	private Long customerId;
	
	@Column(name = "dialogueId")
	private Long dialogueId;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "createDate")
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getDialogueId() {
		return dialogueId;
	}

	public void setDialogueId(Long dialogueId) {
		this.dialogueId = dialogueId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
