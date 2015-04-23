package com.xiaoma.kefu.comet4j;

public class Message {

	private String id ;
	private String name;
	private String text;
	private String transtime;
	private String who;
	
	public Message(String id,String name,String text,String transtime,String who){
		this.id = id;
		this.name = name;
		this.text = text;
		this.transtime = transtime;
		this.who = who;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTranstime() {
		return transtime;
	}
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
}
