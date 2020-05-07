package container;

import java.io.Serializable;

public class AddFriendInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8272009928268780059L;
	public String friendID;
	public String userID;

	public String getFriendID() {
		return friendID;
	}

	public void setFriendID(String friendID) {
		this.friendID = friendID;
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
		this.friendID = friendID;
	}
	
}
