package container;

import java.io.Serializable;

public class Message extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4445432527977303589L;
	public String senderID;
	public String msg;
	public String timestamp;
	
	public String getSenderID() {
		return senderID;
	}
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public Message(String senderID, String msg) {
		this(senderID,  msg, null);
	}
	
	public Message(String senderID, String msg, String timestamp) {
		this.senderID = senderID;
		this.msg = msg;
		this.timestamp = timestamp;
	}
	
}
