package container;

import java.io.Serializable;

public class ChatroomInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2988104891215489697L;
	public String chatroomID;
	public String[] members;
	public String getChatroomID() {
		return chatroomID;
	}
	public void setChatroomID(String chatroomID) {
		this.chatroomID = chatroomID;
	}
	public String[] getMembers() {
		return members;
	}
	public void setMembers(String[] members) {
		this.members = members;
	}
	
	public ChatroomInfo() {
		
	}
	public ChatroomInfo(String[] members) {
		this.members = members;
	}
}
