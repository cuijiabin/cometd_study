package com.xiaoma.kefu.comet4j;


public class NoticeData {
	
	private String type ;
	
	private String cookieValue;
	
	private Object obj ;

	public NoticeData(String type, Object obj){
		
		this.type = type;
		this.obj = obj;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getCookieValue() {
		return cookieValue;
	}
	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}
    
	
}
