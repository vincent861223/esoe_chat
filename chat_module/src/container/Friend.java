package container;

import java.io.Serializable;

public class Friend extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5957861412642192873L;
	public String friendUsername; 
	public Boolean pending;
	public Boolean blocked;
	public String getFriendUsername() {
		return friendUsername;
	}
	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
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
	
	public Friend(String friendUsername, Boolean pending, Boolean blocked) {
		this.friendUsername = friendUsername;
		this.pending = pending;
		this.blocked = blocked;
	}
	
	public Friend(String friendUsername, String pending, String blocked) {
		this.friendUsername = friendUsername;
		this.pending = pending.equals("1");
		this.blocked = blocked.equals("1");
	}
}
