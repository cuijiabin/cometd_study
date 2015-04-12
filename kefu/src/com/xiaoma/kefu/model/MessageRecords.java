package com.xiaoma.kefu.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 留言记录	实体类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月8日上午10:29:30
**********************************
 */
@Entity
@Table(name="message_records")
public class MessageRecords implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="replyWay")
	private Integer replyWay;
	@Column(name="replyType")
	private Integer replyType;
	@Column(name="deptId")
	private Integer deptId;
	@Column(name="userId")
	private Integer userId;
	@Column(name="customerId")
	private Long customerId;
	@Column(name="name",length=50)
	private String name;
	@Column(name="email",length=50)
	private String email;
	@Column(name="phone",length=50)
	private String phone;
	@Column(name="qq",length=50)
	private String qq;
	@Column(name="msn",length=50)
	private String msn;
	@Column(name="company",length=100)
	private String company;
	@Column(name="messageContent",length=255)
	private String messageContent;
	@Column(name="createDate")
	private Date createDate;
	@Column(name="ip",length=20)
	private String ip;
	@Column(name="ipInfo",length=50)
	private String ipInfo;
	@Column(name="keywords")
	private String keywords;
	@Column(name="deviceType")
	private Integer deviceType;
	@Column(name="consultPage")
	private String consultPage;
	@Column(name="openType")
	private Integer openType;
	@Column(name="buttonCode")
	private String buttonCode;
	@Column(name="isDel")
	private Integer isDel;
	@Column(name="firstLandingPage")
	private String firstLandingPage;
	@Column(name="landingPage")
	private String landingPage;
	@Column(name="status")
	private Integer status;
	@Column(name="styleId")
	private Integer styleId;
	
	@Transient
	private String customerName;//客户名称 虚拟列
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReplyWay() {
		return replyWay;
	}
	public void setReplyWay(Integer replyWay) {
		this.replyWay = replyWay;
	}
	public Integer getReplyType() {
		return replyType;
	}
	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIpInfo() {
		return ipInfo;
	}
	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getConsultPage() {
		return consultPage;
	}
	public void setConsultPage(String consultPage) {
		this.consultPage = consultPage;
	}
	public Integer getOpenType() {
		return openType;
	}
	public void setOpenType(Integer openType) {
		this.openType = openType;
	}
	public String getButtonCode() {
		return buttonCode;
	}
	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getFirstLandingPage() {
		return firstLandingPage;
	}
	public void setFirstLandingPage(String firstLandingPage) {
		this.firstLandingPage = firstLandingPage;
	}
	public String getLandingPage() {
		return landingPage;
	}
	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getStyleId() {
		return styleId;
	}
	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}
	
}
