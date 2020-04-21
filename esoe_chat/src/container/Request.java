package container;

import java.io.Serializable;


public class Request extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3467064688812671749L;
	public String command;
	public String userID;
	public Container info;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	
	
	public Container getInfo() {
		return info;
	}

	public void setInfo(Container info) {
		this.info = info;
	}
	
	public Request(String command, String userID, Container info) {
		this.command = command;
		this.userID = userID;
		this.info = info;
	}
	
	public Request(String command, Container info) {
		this(command, null, info);
	}
	
	public Request(String command, String userID) {
		this(command, userID, null);
	}
	
}
