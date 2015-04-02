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
@Table(name="Role_dept_func")
public class Role_dept_func implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="roleDeptId")
	private Integer roleDeptId;
	
	@Column(name="funcId")
	private Integer funcId;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleDeptId() {
		return roleDeptId;
	}

	public void setRoleDeptId(Integer roleDeptId) {
		this.roleDeptId = roleDeptId;
	}

	public Integer getFuncId() {
		return funcId;
	}

	public void setFuncId(Integer funcId) {
		this.funcId = funcId;
	}

    
	
}
