package com.xiaoma.kefu.comet4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description JoinDTO
 * @author cuijiabin
 */
public class JoinDTO {
	private final String transtime;
	private String type;
	private String id;
	private String name;
	private String realId;
	private String cookieVal;
	 

	public String getCookieVal() {
		return cookieVal;
	}

	public void setCookieVal(String cookieVal) {
		this.cookieVal = cookieVal;
	}

	public String getRealId() {
		return realId;
	}

	public void setRealId(String realId) {
		this.realId = realId;
	}

	public JoinDTO(String id, String name) {
		this.type = "up";
		this.id = id;
		this.name = name;
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
		this.transtime = f.format(d);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTranstime() {
		return this.transtime;
	}
}