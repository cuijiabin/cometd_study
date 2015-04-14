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
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="loginName")
	private String loginName;
	@Column(name="password")
	private String password;
	@Column(name="userName")
	private String userName;
	@Column(name="cardName")
	private String cardName;
	@Column(name="email")
	private String email;
	@Column(name="phone")
	private String phone;
	@Column(name="birthday")
	private Date birthday;
	@Column(name="roleId")
	private Integer roleId;
	@Column(name="deptId")
	private Integer deptId;
	@Column(name="onLineStatus")
	private Integer onLineStatus;
	@Column(name="status")
	private Integer status;
	@Column(name="listenLevel")
	private Integer listenLevel;
	@Column(name="maxListen")
	private Integer maxListen;
	@Column(name="createDate")
	private String createDate;
	@Column(name="endDate")
	private String endDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getOnLineStatus() {
		return onLineStatus;
	}
	public void setOnLineStatus(Integer onLineStatus) {
		this.onLineStatus = onLineStatus;
	}


	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getListenLevel() {
		return listenLevel;
	}
	public void setListenLevel(Integer listenLevel) {
		this.listenLevel = listenLevel;
	}
	public Integer getMaxListen() {
		return maxListen;
	}
	public void setMaxListen(Integer maxListen) {
		this.maxListen = maxListen;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

    
	
}
