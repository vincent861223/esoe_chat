package container;

import java.io.Serializable;

public class GetHistoryInfo extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8076241048620745549L;
	public String chatroomID;
	public String getChatroomID() {
		return chatroomID;
	}
	public void setChatroomID(String chatroomID) {
		this.chatroomID = chatroomID;
	}
	
	public GetHistoryInfo(String chatroomID) {
		this.chatroomID = chatroomID;
	}
}
