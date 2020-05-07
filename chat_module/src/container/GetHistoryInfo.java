package container;

import java.io.Serializable;

public class GetHistoryInfo extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8076241048620745549L;
	public String chatroomID;
	public String startTime;
	public String getChatroomID() {
		return chatroomID;
	}
	public void setChatroomID(String chatroomID) {
		this.chatroomID = chatroomID;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public GetHistoryInfo(String chatroomID, String startTime) {
		this.chatroomID = chatroomID;
		this.startTime = startTime;
	}
	
	public GetHistoryInfo(String chatroomID) {
		this(chatroomID, "1970-01-01 00:00:00.000");
	}
}
