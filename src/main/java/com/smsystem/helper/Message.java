package com.smsystem.helper;

public class Message {
	private String type;
	private String heading;
	private String message;
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(String type, String heading, String message) {
		super();
		this.type = type;
		this.heading = heading;
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
