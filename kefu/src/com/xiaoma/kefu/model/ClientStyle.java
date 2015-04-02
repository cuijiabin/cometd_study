package com.xiaoma.kefu.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 访问端界面	实体类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月1日下午5:39:18
**********************************
 */
@Entity
@Table(name="client_style")
public class ClientStyle implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="styleId")
	private Integer styleId;
	@Column(name="headAd",length=50)
	private String headAd;
	@Column(name="isHeadAd")
	private Integer isHeadAd;
	@Column(name="welcomeAd",length=50)
	private String welcomeAd;
	@Column(name="isWelcomeAd")
	private Integer isWelcomeAd;
	@Column(name="isPhizBtn")
	private Integer isPhizBtn;
	@Column(name="isUserGrade")
	private Integer isUserGrade;
	@Column(name="ysAd")
	private String ysAd;
	@Column(name="isYsAd")
	private Integer isYsAd;
	@Column(name="yxAd")
	private String yxAd;
	@Column(name="isYxAd")
	private Integer isYxAd;
	@Column(name="buttomAd",length=50)
	private String buttomAd;
	@Column(name="isButtomAd")
	private Integer isButtomAd;
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
	public String getHeadAd() {
		return headAd;
	}
	public void setHeadAd(String headAd) {
		this.headAd = headAd;
	}
	public Integer getIsHeadAd() {
		return isHeadAd;
	}
	public void setIsHeadAd(Integer isHeadAd) {
		this.isHeadAd = isHeadAd;
	}
	public String getWelcomeAd() {
		return welcomeAd;
	}
	public void setWelcomeAd(String welcomeAd) {
		this.welcomeAd = welcomeAd;
	}
	public Integer getIsWelcomeAd() {
		return isWelcomeAd;
	}
	public void setIsWelcomeAd(Integer isWelcomeAd) {
		this.isWelcomeAd = isWelcomeAd;
	}
	public Integer getIsPhizBtn() {
		return isPhizBtn;
	}
	public void setIsPhizBtn(Integer isPhizBtn) {
		this.isPhizBtn = isPhizBtn;
	}
	public Integer getIsUserGrade() {
		return isUserGrade;
	}
	public void setIsUserGrade(Integer isUserGrade) {
		this.isUserGrade = isUserGrade;
	}
	public String getYsAd() {
		return ysAd;
	}
	public void setYsAd(String ysAd) {
		this.ysAd = ysAd;
	}
	public Integer getIsYsAd() {
		return isYsAd;
	}
	public void setIsYsAd(Integer isYsAd) {
		this.isYsAd = isYsAd;
	}
	public String getYxAd() {
		return yxAd;
	}
	public void setYxAd(String yxAd) {
		this.yxAd = yxAd;
	}
	public Integer getIsYxAd() {
		return isYxAd;
	}
	public void setIsYxAd(Integer isYxAd) {
		this.isYxAd = isYxAd;
	}
	public String getButtomAd() {
		return buttomAd;
	}
	public void setButtomAd(String buttomAd) {
		this.buttomAd = buttomAd;
	}
	public Integer getIsButtomAd() {
		return isButtomAd;
	}
	public void setIsButtomAd(Integer isButtomAd) {
		this.isButtomAd = isButtomAd;
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
