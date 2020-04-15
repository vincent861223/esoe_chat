package container;

import java.io.Serializable;

public class MessageInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 72005063343133555L;
	public String sender;
	public String receiver;
	public String message;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageInfo() {
		
	}
	public MessageInfo(String sender, String receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}
}
