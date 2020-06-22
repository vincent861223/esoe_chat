package client;

import container.AddFriendInfo;
import container.Friend;
import container.Request;
import container.Response;
import container.UserInfo;

public class FriendAgent extends Agent {
	// The agent that is responsible for doing Friend related operation
	String userID;
	
	public FriendAgent(String ip, int port, String userID) {
		super(ip, port);
		this.userID = userID;
	}
	
	public Response addFriend(String friendUsername) {
		// add friend by the friend's name
		AddFriendInfo addFriendInfo = new AddFriendInfo(userID, friendUsername);
		Request request = new Request("AddFriend", addFriendInfo);
		return sendRequest(request);
	}
	
	public Response getFriend() {
		// get the list of all the friend of the user
		UserInfo userInfo = new UserInfo(this.userID);
		Request request = new Request("GetFriend", userInfo);
		return sendRequest(request);
	}
	
	public Response confirmFriend(String friendUsername) {
		// confirm an invitation
		Friend friend = new Friend(friendUsername, false, false, false);
		Request request = new Request("modifyFriend", userID, friend);
		return sendRequest(request);
	}
}
