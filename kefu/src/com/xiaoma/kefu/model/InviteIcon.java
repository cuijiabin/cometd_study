package com.xiaoma.kefu.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *对话邀请框图标	实体类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月1日下午5:39:18
**********************************
 */
@Entity
@Table(name="invite_icon")
public class InviteIcon implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="styleId")
	private Integer styleId;
	@Column(name="deviceType")
	private Integer deviceType;
	@Column(name="truePic")
	private String truePic;
	@Column(name="locationMode")
	private Integer locationMode;
	@Column(name="siteLevel")
	private String siteLevel;
	@Column(name="siteVertical")
	private String siteVertical;
	@Column(name="buttonId")
	private Integer buttonId;
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
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getTruePic() {
		return truePic;
	}
	public void setTruePic(String truePic) {
		this.truePic = truePic;
	}
	public Integer getLocationMode() {
		return locationMode;
	}
	public void setLocationMode(Integer locationMode) {
		this.locationMode = locationMode;
	}
	public String getSiteLevel() {
		return siteLevel;
	}
	public void setSiteLevel(String siteLevel) {
		this.siteLevel = siteLevel;
	}
	public String getSiteVertical() {
		return siteVertical;
	}
	public void setSiteVertical(String siteVertical) {
		this.siteVertical = siteVertical;
	}
	public Integer getButtonId() {
		return buttonId;
	}
	public void setButtonId(Integer buttonId) {
		this.buttonId = buttonId;
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
