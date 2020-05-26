package container;

import java.io.Serializable;

public class UpdateHistoryInfo extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String chatroomID;
	public String getChatroomID() {
		return chatroomID;
	}
	public void setChatroomID(String chatroomID) {
		this.chatroomID = chatroomID;
	}
	public UpdateHistoryInfo() {
		
	}
	public UpdateHistoryInfo(String chatroomID) {
		this.chatroomID = chatroomID;
	}
}
