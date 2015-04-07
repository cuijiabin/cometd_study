package com.xiaoma.kefu.common;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 对话标识
 * 
 * @author cuijiabin
 *
 */
public class DialogueUniqueTag {

	public static Integer CUSTOMER_TYPE = 1;

	public static Integer USER_TYPE = 2;

	public static String DELIMITER = "##";
	
	public static Integer DEFAULT_USER_ID = -1;

	public DialogueUniqueTag() {
	}

	public DialogueUniqueTag(Integer type, Long customerId, Integer userId) {
		this.type = type;
		this.customerId = customerId;
		this.userId = userId;
	}

	/**
	 * 标识类型 1-customer 2-user
	 */
	private Integer type;

	/**
	 * 客户id
	 */
	private Long customerId;

	/**
	 * 客服id
	 */
	private Integer userId;

	/**
	 * 进入窗口时间
	 */
	private Date enterTime;

	/**
	 * 连接建立时间
	 */
	private Date connectTime;

	public String getUniqueTag() {

		StringBuffer sbf = new StringBuffer();
		sbf.append(type);

		// 如果是空的话使用-1代替
		customerId = (customerId == null) ? -1L : customerId;
		userId = (userId == null) ? -1 : userId;

		switch (type) {
		case 1:
			sbf.append(DELIMITER).append(customerId);
			sbf.append(DELIMITER).append(DEFAULT_USER_ID);
			break;

		case 2:
			sbf.append(DELIMITER).append(userId);
			sbf.append(DELIMITER).append(customerId);
			break;

		default:
			sbf.deleteCharAt(0);
			break;
		}

		return sbf.toString();

	}
	
	public String getSendUniqueTag() {

		StringBuffer sbf = new StringBuffer();
		
		// 如果是空的话使用-1代替
		customerId = (customerId == null) ? -1L : customerId;
		userId = (userId == null) ? -1 : userId;

		switch (type) {
		case 1:
			sbf.append(USER_TYPE);
			sbf.append(DELIMITER).append(userId);
			sbf.append(DELIMITER).append(customerId);
			break;

		case 2:
			sbf.append(CUSTOMER_TYPE);
			sbf.append(DELIMITER).append(customerId);
			sbf.append(DELIMITER).append(DEFAULT_USER_ID);
			break;

		default:
			sbf.deleteCharAt(0);
			break;
		}

		return sbf.toString();

	}

	public String getSendUniqueTag(String uniqueTag) {

		if (StringUtils.isBlank(uniqueTag)) {
			return "";
		}
		String[] uArr = uniqueTag.split(DELIMITER);
		if (uArr.length < 3) {
			return "";
		}

		Integer type = Integer.valueOf(uArr[0]);
		StringBuffer sbf = new StringBuffer(type);

		switch (type) {
		case 1:
			sbf.append(USER_TYPE);
			sbf.append(DELIMITER).append(uArr[2]);
			sbf.append(DELIMITER).append(uArr[1]);
			break;

		case 2:
			sbf.append(CUSTOMER_TYPE);
			sbf.append(DELIMITER).append(uArr[2]);
			sbf.append(DELIMITER).append(DEFAULT_USER_ID);
			break;
		}


		return sbf.toString();

	}

	public DialogueUniqueTag genDialogueUniqueTag(String uniqueTag) {
		DialogueUniqueTag dialogueUniqueTag = null;

		if (StringUtils.isBlank(uniqueTag)) {
			return null;
		}

		String[] uArr = uniqueTag.split(DELIMITER);
		if (uArr.length <= 3) {
			return null;
		}

		Integer type = Integer.valueOf(uArr[0]);

		switch (type) {
		case 1:
			dialogueUniqueTag = new DialogueUniqueTag(type,
					Long.valueOf(uArr[1]), Integer.valueOf(uArr[2]));
			break;

		case 2:
			dialogueUniqueTag = new DialogueUniqueTag(type,
					Long.valueOf(uArr[2]), Integer.valueOf(uArr[1]));
			break;
		}

		return dialogueUniqueTag;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public Date getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}

}
