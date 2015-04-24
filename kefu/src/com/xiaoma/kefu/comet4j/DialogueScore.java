package com.xiaoma.kefu.comet4j;

import java.io.Serializable;

/**
 * 对话评分
 * 
 * @author cuijiabin
 *
 */
public class DialogueScore implements Serializable{

	private static final long serialVersionUID = 46790121846591261L;

	private Integer scoreType;
	
	private String remark;

	public Integer getScoreType() {
		return scoreType;
	}

	public void setScoreType(Integer scoreType) {
		this.scoreType = scoreType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
