package com.xiaoma.kefu.comet4j;

public class NoticeData {

	public static final String APP_CHANNEL = "talker";
	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String RENAME = "rename";
	public static final String TALK = "talk";
	public static final String HEALTH = "health";
	
	private String type ;
	
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
    
	
	
	
}
