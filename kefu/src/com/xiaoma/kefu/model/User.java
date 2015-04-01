package com.xiaoma.kefu.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity
 * @author hanyu
 *
 */
@Entity
@Table(name="User")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static public final int STATUS_DELETED = 0;
	static public final int STATUS_ENABLED = 1;
	static public final int STATUS_LOCKED = 2;
	static public final int STATUS_DISABLED = 3;

	static public final String ATTRIBUTE_CREDENTIAL_CHANGE = "passwordLastChange";
	static public final String ATTRIBUTE_LAST_LOGIN = "lastLogin";
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="loginName",length=64)
	private String loginName;
	@Column(name="userName",length=64)
	private String userName;
	@Column(name="status")
	private Integer status;
	@Column(name="password",length=64,nullable=false)
	private String password;
	@Column(name="userRole")
	private Integer userRole;
	@Column(name="userType")
	private Integer userType;
//	private Map<String, String> attributes;
	@Column(name="email")
	private String email;
	@Column(name="phone")
	private String phone;
	@Column(name="leaderId")
	private Integer leaderId;
	@Column(name="managerId")
	private Integer managerId;
//	private Set<String> roleIdSet;
//	private String[] roles;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserRole() {
		return userRole;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}
	
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User)obj;
		return getId().equals(other.getId());
	}
	
}
