package container;

import java.io.Serializable;

public class MessageInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 72005063343133555L;
	public String chatroomID;
	public String message;
	
	
	public String getSender() {
		return chatroomID;
	}
	public void setSender(String sender) {
		this.chatroomID = sender;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageInfo() {
		
	}
	public MessageInfo(String chatroomID,String message) {
		this.chatroomID = chatroomID;
		this.message = message;
	}
}
