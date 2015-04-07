package com.xiaoma.kefu.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对话信息	实体类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月3日下午3:20:40
**********************************
 */
@Entity
@Table(name="dialogue")
public class Dialogue implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="userId")
	private Integer userId;
	@Column(name="cardName",length=50)
	private String cardName;
	@Column(name="deptId")
	private Integer deptId;
	@Column(name="customerId")
	private Integer customerId;
	@Column(name="beginDate")
	private Date beginDate;
	@Column(name="endDate")
	private Date endDate;
	@Column(name="durationTime")
	private Integer durationTime;
	@Column(name="maxSpace")
	private Integer maxSpace;
	@Column(name="isWait")
	private Integer isWait;
	@Column(name="waitTime")
	private Integer waitTime;
	@Column(name="firstTime")
	private Integer firstTime;
	@Column(name="isTalk")
	private Integer isTalk;
	@Column(name="scoreType")
	private Integer scoreType;
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
	@Column(name="btnCode")
	private String btnCode;
	@Column(name="waitListId")
	private Integer waitListId;
	@Column(name="closeType")
	private Integer closeType;
	@Column(name="isDel")
	private Integer isDel;
	@Column(name="totalNum")
	private Integer totalNum;
	@Column(name="landingPage")
	private String landingPage;
	@Column(name="styleId")
	private Integer styleId;
	
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
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(Integer durationTime) {
		this.durationTime = durationTime;
	}
	public Integer getMaxSpace() {
		return maxSpace;
	}
	public void setMaxSpace(Integer maxSpace) {
		this.maxSpace = maxSpace;
	}
	public Integer getIsWait() {
		return isWait;
	}
	public void setIsWait(Integer isWait) {
		this.isWait = isWait;
	}
	public Integer getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}
	public Integer getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(Integer firstTime) {
		this.firstTime = firstTime;
	}
	public Integer getIsTalk() {
		return isTalk;
	}
	public void setIsTalk(Integer isTalk) {
		this.isTalk = isTalk;
	}
	public Integer getScoreType() {
		return scoreType;
	}
	public void setScoreType(Integer scoreType) {
		this.scoreType = scoreType;
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
	public String getBtnCode() {
		return btnCode;
	}
	public void setBtnCode(String btnCode) {
		this.btnCode = btnCode;
	}
	public Integer getWaitListId() {
		return waitListId;
	}
	public void setWaitListId(Integer waitListId) {
		this.waitListId = waitListId;
	}
	public Integer getCloseType() {
		return closeType;
	}
	public void setCloseType(Integer closeType) {
		this.closeType = closeType;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public String getLandingPage() {
		return landingPage;
	}
	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}
	
}
