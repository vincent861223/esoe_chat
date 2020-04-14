package chat;

import java.io.Serializable;

import org.json.JSONObject;

public class Request implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3467064688812671749L;
	public String command;
	public String message;
	public String sender;
	public String receiver;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
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
	
	public Request(String command, String message, String sender, String receiver) {
		this.command = command;
		this.message = message;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}
}
