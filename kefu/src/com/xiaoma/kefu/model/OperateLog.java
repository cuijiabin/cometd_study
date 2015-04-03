package com.xiaoma.kefu.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OperateLog entity
 * @author hanyu
 *
 */
@Entity
@Table(name="operate_log")
public class OperateLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="userId")
	private Integer userId;
	@Column(name="loginName")
	private String loginName;
	@Column(name="cardName")
	private String cardName;
	@Column(name="deptId")
	private Integer deptId;
	@Column(name="funcId")
	private Integer funcId;
	@Column(name="title")
	private String title;
	@Column(name="content")
	private String content;
	@Column(name="createDate")
	private Date createDate;
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
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getFuncId() {
		return funcId;
	}
	public void setFuncId(Integer funcId) {
		this.funcId = funcId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
