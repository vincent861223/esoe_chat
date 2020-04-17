package container;

import java.io.Serializable;

public class Friend extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5957861412642192873L;
	public String friendID; 
	public Boolean pending;
	public Boolean blocked;
	public String getFriendID() {
		return friendID;
	}
	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}
	public Boolean getPending() {
		return pending;
	}
	public void setPending(Boolean pending) {
		this.pending = pending;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	
	public Friend() {
		
	}
	
	public Friend(String friendID, Boolean pending, Boolean blocked) {
		this.friendID = friendID;
		this.pending = pending;
		this.blocked = blocked;
	}
	
	public Friend(String friendID, String pending, String blocked) {
		this.friendID = friendID;
		this.pending = pending.equals("1");
		this.blocked = blocked.equals("1");
	}
}
