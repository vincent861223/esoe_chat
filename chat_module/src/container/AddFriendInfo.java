package container;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class AddFriendInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8272009928268780059L;

	@SerializedName("friendUsername")
	public String friendUsername;
	@SerializedName("userID")
	public String userID;

	
	public AddFriendInfo(String userID, String friendID) {
		this.userID = userID;
		this.friendUsername = friendID;
	}
	
}
