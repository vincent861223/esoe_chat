package container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FriendList extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1351577605324651324L;
	public List<Friend> friends;
	
	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}
	
	public FriendList() {
		this.friends = new ArrayList<Friend>();
	}

	public FriendList(List<Friend> friends) {
		this.friends = friends;
	}
	
}
