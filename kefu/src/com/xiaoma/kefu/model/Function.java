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
@Table(name="Function")
public class Function implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="pId")
	private Integer pId;

	@Column(name="name")
	private String name;
	
	@Column(name="target")
	private String target;
	
	@Column(name="url")
	private String url;
	
	@Column(name="funcLevel")
	private Integer funcLevel;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getFuncLevel() {
		return funcLevel;
	}

	public void setFuncLevel(Integer funcLevel) {
		this.funcLevel = funcLevel;
	}

	

	
	

    
	
}
