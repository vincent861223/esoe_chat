package container;

import java.io.Serializable;

public class AddFriendInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8272009928268780059L;
	public String friendUsername;
	public String userID;

	public String getFriendID() {
		return friendUsername;
	}

	public void setFriendID(String friendID) {
		this.friendUsername = friendID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public AddFriendInfo() {
		
	}
	
	public AddFriendInfo(String userID, String friendID) {
		this.userID = userID;
		this.friendUsername = friendID;
	}
	
}
