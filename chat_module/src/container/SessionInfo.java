package container;

import java.io.Serializable;

public class SessionInfo extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String userID; 
	public String sessionID;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	public SessionInfo(String userID, String sessionID) {
		this.userID = userID;
		this.sessionID = sessionID;
	}
}
