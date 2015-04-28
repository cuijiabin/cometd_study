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
 *对话邀请框元素	实体类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月1日下午5:39:18
**********************************
 */
@Entity
@Table(name="invite_element")
public class InviteElement implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="inviteId")
	private Integer inviteId;
	@Column(name="name")
	private String name;
	@Column(name="width")
	private Integer width;
	@Column(name="height")
	private Integer height;
	@Column(name="siteLeft")
	private Integer siteLeft;
	@Column(name="siteTop")
	private Integer siteTop;
	@Column(name="picUrl")
	private String picUrl;
	@Column(name="level")
	private Integer level;
	@Column(name="operationType")
	private Integer operationType;
	@Column(name="openType")
	private String openType;
	@Column(name="openUrl")
	private String openUrl;
	@Column(name="updateDate")
	private Date updateDate;
	@Column(name="createDate")
	private Date createDate;
	
	@Transient
	private Integer sortId;//虚拟列,排序id
	@Transient
	private Boolean isUpPic;//虚拟列, 是否上传了图片 用于预览图时候
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInviteId() {
		return inviteId;
	}
	public void setInviteId(Integer inviteId) {
		this.inviteId = inviteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getSiteLeft() {
		return siteLeft;
	}
	public void setSiteLeft(Integer siteLeft) {
		this.siteLeft = siteLeft;
	}
	public Integer getSiteTop() {
		return siteTop;
	}
	public void setSiteTop(Integer siteTop) {
		this.siteTop = siteTop;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getOperationType() {
		return operationType;
	}
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	public String getOpenUrl() {
		return openUrl;
	}
	public void setOpenUrl(String openUrl) {
		this.openUrl = openUrl;
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
	public Integer getSortId() {
		return sortId;
	}
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	public Boolean getIsUpPic() {
		return isUpPic;
	}
	public void setIsUpPic(Boolean isUpPic) {
		this.isUpPic = isUpPic;
	}
	
	
	
	
}
