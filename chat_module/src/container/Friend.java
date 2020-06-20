package container;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Friend extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5957861412642192873L;
	@SerializedName("friendUsername")
	public String friendUsername; 
	@SerializedName("pending")
	public Boolean pending;
	@SerializedName("blocked")
	public Boolean blocked;
	@SerializedName("inviteSender")
	public Boolean inviteSender;

	public Friend() {
		
	}
	
	public Friend(String friendUsername, Boolean pending, Boolean blocked, Boolean inviteSender) {
		this.friendUsername = friendUsername;
		this.pending = pending;
		this.blocked = blocked;
		this.inviteSender = inviteSender;
	}
	
	public Friend(String friendUsername, String pending, String blocked, String inviteSender) {
		this.friendUsername = friendUsername;
		this.pending = pending.equals("1");
		this.blocked = blocked.equals("1");
		this.inviteSender = inviteSender.equals("1");
	}
}
