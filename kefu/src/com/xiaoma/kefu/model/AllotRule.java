package com.xiaoma.kefu.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分配机制	实体类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月2日上午9:06:10
**********************************
 */
@Entity
@Table(name="allot_rule")
public class AllotRule implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="styleId")
	private Integer styleId;
	@Column(name="firstRule")
	private Integer firstRule;
	@Column(name="secondRule")
	private Integer secondRule;
	@Column(name="thirdRule")
	private Integer thirdRule;
	@Column(name="updateDate")
	private Date updateDate;
	@Column(name="createDate")
	private Date createDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStyleId() {
		return styleId;
	}
	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}
	public Integer getFirstRule() {
		return firstRule;
	}
	public void setFirstRule(Integer firstRule) {
		this.firstRule = firstRule;
	}
	public Integer getSecondRule() {
		return secondRule;
	}
	public void setSecondRule(Integer secondRule) {
		this.secondRule = secondRule;
	}
	public Integer getThirdRule() {
		return thirdRule;
	}
	public void setThirdRule(Integer thirdRule) {
		this.thirdRule = thirdRule;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
