package client;

import container.AddFriendInfo;
import container.Friend;
import container.Request;
import container.Response;
import container.UserInfo;

public class FriendAgent extends Agent {
	String userID;
	
	public FriendAgent(String ip, int port, String userID) {
		super(ip, port);
		this.userID = userID;
	}
	
	public Response addFriend(String friendID) {
		AddFriendInfo addFriendInfo = new AddFriendInfo(userID, friendID);
		Request request = new Request("AddFriend", addFriendInfo);
		return sendRequest(request);
	}
	
	public Response getFriend() {
		UserInfo userInfo = new UserInfo(this.userID);
		Request request = new Request("GetFriend", userInfo);
		return sendRequest(request);
	}
	
	public Response confirmFriend(String friendID) {
		Friend friend = new Friend(friendID, false, false);
		Request request = new Request("modifyFriend", userID, friend);
		return sendRequest(request);
	}
}
