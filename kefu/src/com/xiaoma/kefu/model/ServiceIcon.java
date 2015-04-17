package com.xiaoma.kefu.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *客服图标	实体类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月1日下午5:39:18
**********************************
 */
@Entity
@Table(name="service_icon")
public class ServiceIcon implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="styleId")
	private Integer styleId;
	@Column(name="deviceType")
	private Integer deviceType;
	@Column(name="isDisplay")
	private Integer isDisplay;
	@Column(name="displayMode")
	private Integer displayMode;
	@Column(name="siteZy")
	private String siteZy;
	@Column(name="siteZyPx")
	private Integer siteZyPx;
	@Column(name="siteDd")
	private String siteDd;
	@Column(name="siteDdPx")
	private Integer siteDdPx;
	@Column(name="onlinePic")
	private String onlinePic;
	@Column(name="offlinePic")
	private String offlinePic;
	@Column(name="width")
	private Integer width;
	@Column(name="height")
	private Integer height;
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
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public Integer getDisplayMode() {
		return displayMode;
	}
	public void setDisplayMode(Integer displayMode) {
		this.displayMode = displayMode;
	}
	public String getSiteZy() {
		return siteZy;
	}
	public void setSiteZy(String siteZy) {
		this.siteZy = siteZy;
	}
	public Integer getSiteZyPx() {
		return siteZyPx;
	}
	public void setSiteZyPx(Integer siteZyPx) {
		this.siteZyPx = siteZyPx;
	}
	public String getSiteDd() {
		return siteDd;
	}
	public void setSiteDd(String siteDd) {
		this.siteDd = siteDd;
	}
	public Integer getSiteDdPx() {
		return siteDdPx;
	}
	public void setSiteDdPx(Integer siteDdPx) {
		this.siteDdPx = siteDdPx;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getOnlinePic() {
		return onlinePic;
	}
	public void setOnlinePic(String onlinePic) {
		this.onlinePic = onlinePic;
	}
	public String getOfflinePic() {
		return offlinePic;
	}
	public void setOfflinePic(String offlinePic) {
		this.offlinePic = offlinePic;
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
