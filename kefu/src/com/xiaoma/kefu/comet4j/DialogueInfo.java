package com.xiaoma.kefu.comet4j;

import java.io.Serializable;

/**
 * 临时对话信息
 * 
 * @author cuijiabin
 *
 */
public class DialogueInfo implements Serializable{
	
	private static final long serialVersionUID = 8811884851363056252L;
	
	private Long customerId;
	private Integer userId;
	private String cardName;
	private Integer deptId;
	private Integer isWait;
	private Integer waitTime;
	private Integer scoreType;
	private String  scoreRemark;
	private String ip;
	private String ipInfo;
	private String keywords;
	private Integer deviceType;
	private String consultPage;
	private Integer openType;
	private String btnCode;
	private Integer waitListId;
	private Integer closeType;
	private Integer isDel;
	private Integer totalNum;
	private String landingPage;
	private Integer styleId;
	
	private String userCcnId;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	public Integer getScoreType() {
		return scoreType;
	}
	public void setScoreType(Integer scoreType) {
		this.scoreType = scoreType;
	}
	public String getScoreRemark() {
		return scoreRemark;
	}
	public void setScoreRemark(String scoreRemark) {
		this.scoreRemark = scoreRemark;
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
	public Integer getStyleId() {
		return styleId;
	}
	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}
	public String getUserCcnId() {
		return userCcnId;
	}
	public void setUserCcnId(String userCcnId) {
		this.userCcnId = userCcnId;
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
	
}
