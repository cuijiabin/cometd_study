package com.xiaoma.kefu.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity
 * @author yangxiaofeng
 *
 */
@Entity
@Table(name="keyboard")
public class Keyboard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="userId")
	private Integer userId;
	
	@Column(name="picKey")
	private String picKey;

	@Column(name="showKey")
	private String showKey;
	
	@Column(name="lastKet")
	private String lastKey;
	
	@Column(name="nextKey")
	private String nextKey;
	
	@Column(name="sendKey")
	private Integer sendKey;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPicKey() {
		return picKey;
	}

	public void setPicKey(String picKey) {
		this.picKey = picKey;
	}

	public String getShowKey() {
		return showKey;
	}

	public void setShowKey(String showKey) {
		this.showKey = showKey;
	}

	public String getLastKey() {
		return lastKey;
	}

	public void setLastKey(String lastKey) {
		this.lastKey = lastKey;
	}

	public String getNextKey() {
		return nextKey;
	}

	public void setNextKey(String nextKey) {
		this.nextKey = nextKey;
	}

	public Integer getSendKey() {
		return sendKey;
	}

	public void setSendKey(Integer sendKey) {
		this.sendKey = sendKey;
	}

 
	
}
