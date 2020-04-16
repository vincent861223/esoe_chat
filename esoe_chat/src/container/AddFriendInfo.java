package container;

import java.io.Serializable;

public class AddFriendInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8272009928268780059L;
	public String[] friends;

	public String[] getFriends() {
		return friends;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}
	
	public AddFriendInfo() {
		
	}
	
	public AddFriendInfo(String[] friends) {
		this.friends = friends;
	}
	
}
