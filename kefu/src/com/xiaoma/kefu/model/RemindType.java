package com.xiaoma.kefu.model;


import java.io.Serializable;

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

@Table(name="remind_type")
    public class RemindType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="userId")
	private Integer userId;
	
	@Column(name="lsoundEffect")
	private Integer lsoundEffect;
	
	@Column(name="lineEffect")
	private Integer lineEffect;
	
	@Column(name="lineEffectUrl")
	private String lineEffectUrl;
	
	@Column(name="jsoundEffect")
	private Integer jsoundEffect;
	
	@Column(name="createEffect")
	private Integer createEffect;
	
	@Column(name="createUrl")
	private String createUrl;
	
	@Column(name="reSoundEffect")
	private Integer reSoundEffect;
	
	@Column(name="receiveEffect")
	private Integer receiveEffect;
	
	@Column(name="receiveUrl")
	private String receiveUrl;

	@Column(name="upHint")
	private Integer upHint;
	
	@Column(name="upWdInform")
	private Integer upWdInform;
	
	@Column(name="createDate")
	private Integer createDate;

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

	public Integer getLsoundEffect() {
		return lsoundEffect;
	}

	public void setLsoundEffect(Integer lsoundEffect) {
		this.lsoundEffect = lsoundEffect;
	}

	public Integer getLineEffect() {
		return lineEffect;
	}

	public void setLineEffect(Integer lineEffect) {
		this.lineEffect = lineEffect;
	}

	public String getLineEffectUrl() {
		return lineEffectUrl;
	}

	public void setLineEffectUrl(String lineEffectUrl) {
		this.lineEffectUrl = lineEffectUrl;
	}

	public Integer getJsoundEffect() {
		return jsoundEffect;
	}

	public void setJsoundEffect(Integer jsoundEffect) {
		this.jsoundEffect = jsoundEffect;
	}

	public Integer getCreateEffect() {
		return createEffect;
	}

	public void setCreateEffect(Integer createEffect) {
		this.createEffect = createEffect;
	}

	public String getCreateUrl() {
		return createUrl;
	}

	public void setCreateUrl(String createUrl) {
		this.createUrl = createUrl;
	}

	public Integer getReSoundEffect() {
		return reSoundEffect;
	}

	public void setReSoundEffect(Integer reSoundEffect) {
		this.reSoundEffect = reSoundEffect;
	}

	public Integer getReceiveEffect() {
		return receiveEffect;
	}

	public void setReceiveEffect(Integer receiveEffect) {
		this.receiveEffect = receiveEffect;
	}

	public String getReceiveUrl() {
		return receiveUrl;
	}

	public void setReceiveUrl(String receiveUrl) {
		this.receiveUrl = receiveUrl;
	}

	public Integer getUpHint() {
		return upHint;
	}

	public void setUpHint(Integer upHint) {
		this.upHint = upHint;
	}

	public Integer getUpWdInform() {
		return upWdInform;
	}

	public void setUpWdInform(Integer upWdInform) {
		this.upWdInform = upWdInform;
	}

	public Integer getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Integer createDate) {
		this.createDate = createDate;
	}
	


    
	
}
