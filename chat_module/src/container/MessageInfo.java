package container;

import java.io.Serializable;

public class MessageInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 72005063343133555L;
	public String chatroomID;
	public String senderID;
	public String message;
	
	
	
	public String getChatroomID() {
		return chatroomID;
	}
	public void setChatroomID(String chatroomID) {
		this.chatroomID = chatroomID;
	}
	public String getSenderID() {
		return senderID;
	}
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageInfo() {
		
	}
	public MessageInfo(String chatroomID, String senderID, String message) {
		this.chatroomID = chatroomID;
		this.senderID = senderID;
		this.message = message;
	}
}
