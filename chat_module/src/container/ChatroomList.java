package container;

import java.io.Serializable;

public class ChatroomList extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String[] chatroomIDs;
	public String[] getChatroomIDs() {
		return chatroomIDs;
	}
	public void setChatroomIDs(String[] chatroomIDs) {
		this.chatroomIDs = chatroomIDs;
	}
	
	public ChatroomList(String[] chatroomIDs) {
		this.chatroomIDs = chatroomIDs;
	}
}
