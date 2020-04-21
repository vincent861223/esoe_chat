package container;

import java.io.Serializable;

public class UserInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3455185393392627798L;
	public String userID;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public UserInfo() {
		
	}
	
	public UserInfo(String userID) {
		this.userID = userID;
	}
}
