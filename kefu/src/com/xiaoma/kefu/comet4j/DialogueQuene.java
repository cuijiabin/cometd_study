package com.xiaoma.kefu.comet4j;

import java.io.Serializable;
import java.util.Date;

import com.xiaoma.kefu.model.Customer;

/**
 * 客服的对话列表
 * 
 * @author cuijiabin
 *
 */
public class DialogueQuene implements Serializable{

	private static final long serialVersionUID = 6781135371791181446L;

	private String ccnId;
	
	private Customer customer;
	
	private Date enterTime;

	public String getCcnId() {
		return ccnId;
	}

	public void setCcnId(String ccnId) {
		this.ccnId = ccnId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	
}
